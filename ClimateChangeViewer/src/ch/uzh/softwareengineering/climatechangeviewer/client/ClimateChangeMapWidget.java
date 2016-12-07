package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.ajaxloader.client.ArrayHelper;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.visualizationlib.HeatMapLayer;
import com.google.gwt.maps.client.visualizationlib.HeatMapLayerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ClimateChangeMapWidget extends Composite {

	public static final double TEMPERATURE_CHANGE_THRESHOLD_NEUTRAL = 0.25f;
	public static final double TEMPERATURE_CHANGE_THRESHOLD_ASCENDING_MODERATE = 1.0f;
	public static final double TEMPERATURE_CHANGE_THRESHOLD_ASCENDING_MEDIUM = 2.0f;
	public static final double TEMPERATURE_CHANGE_THRESHOLD_DECENDING_MODERATE = -1.0f;
	public static final double TEMPERATURE_CHANGE_THRESHOLD_DECENDING_MEDIUM = -2.0f;

	private FlowPanel mapPanel = new FlowPanel();
	private MapWidget mapWidget;

	private HeatMapLayer heatMapLayerBlack;
	private HeatMapLayer heatMapLayerGreen;
	private HeatMapLayer heatMapLayerLightBlue;
	private HeatMapLayer heatMapLayerMediumBlue;
	private HeatMapLayer heatMapLayerDarkBlue;
	private HeatMapLayer heatMapLayerLightRed;
	private HeatMapLayer heatMapLayerMediumRed;
	private HeatMapLayer heatMapLayerDarkRed;
	
	private List<Marker> setMarkers = new ArrayList<Marker>();
	
	private List<LatLng> coordinatesInvalid = new ArrayList<LatLng>();
	private List<LatLng> coordinatesNeutral = new ArrayList<LatLng>();
	private List<LatLng> coordinatesDecendingModerate = new ArrayList<LatLng>();
	private List<LatLng> coordinatesDecendingMedium = new ArrayList<LatLng>();
	private List<LatLng> coordinatesDecendingHigh = new ArrayList<LatLng>();
	private List<LatLng> coordinatesAscendingModerate = new ArrayList<LatLng>();
	private List<LatLng> coordinatesAscendingMedium = new ArrayList<LatLng>();
	private List<LatLng> coordinatesAscendingHigh = new ArrayList<LatLng>();

	private InfoWindow currentInfoWindow;
	
	public ClimateChangeMapWidget() {
		initWidget(mapPanel);
		draw();
	}
	
	private void draw() {
		// Set map options and draw it.
		LatLng center = LatLng.newInstance(25, 0);
		MapOptions options = MapOptions.newInstance();
		options.setMapTypeId(MapTypeId.HYBRID);
		options.setCenter(center);
		options.setStreetViewControl(false);
		options.setMapTypeControl(false);
		options.setKeyboardShortcuts(false);
		options.setDraggable(true);
		options.setScrollWheel(true);
		options.setZoom(2);
		options.setMaxZoom(4);
		options.setMinZoom(2);
		mapWidget = new MapWidget(options);

		// Add map to panel.
		mapPanel.add(mapWidget);
		mapWidget.setSize("1000px", "555px");
		initializeLayers();
	}
	
	public void drawClimateChangeModel(final List<MapDataElement> mapData) {
		resetData();
		
		injectDataIntoHeatMapLayers();
		
		Size imageSize = Size.newInstance(32,32);
		Point imageOrigin = Point.newInstance(10,10);
		Point imageAnchor = Point.newInstance(16,16);
		MarkerImage markerImage = MarkerImage.newInstance("images/invisible32x32.ico", imageSize, imageOrigin, imageAnchor);
		
		for(int i = 0; i <= mapData.size(); i++) {
			final MapDataElement dataElement = mapData.get(i);
			double latitude = dataElement.getLatitude();
			double longitude = dataElement.getLongitude();
			LatLng location = LatLng.newInstance(latitude, longitude);
			double temperaturePeriod1 = dataElement.getTemperaturePeriod1();
			double temperaturePeriod2 = dataElement.getTemperaturePeriod2();
			
			getHeatMapLayerIDForClimateChange(temperaturePeriod1, temperaturePeriod2).add(location);
			
			MarkerOptions markerOptions = MarkerOptions.newInstance();
			markerOptions.setPosition(location);
			markerOptions.setIcon(markerImage);
			markerOptions.setVisible(true);
			markerOptions.setMap(mapWidget);
			
			final Marker marker = Marker.newInstance(markerOptions);
			marker.setMap(mapWidget);
			setMarkers.add(marker);

			marker.addClickHandler(new ClickMapHandler() {
				@Override
				public void onEvent(ClickMapEvent event) {
					drawInfoWindow(dataElement, marker);
		     	}
			});
			
			injectDataIntoHeatMapLayers();
		}
	}

	private void drawInfoWindow(MapDataElement dataElement, Marker marker) {
		// Display only one InfoWindow on the map.
		if(currentInfoWindow != null) {
			currentInfoWindow.close();
		}
		
		if (marker == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		
		String city = dataElement.getCity();
		String period1Start = Integer.toString(dataElement.getComparisonPeriod1Start());
		String period2Start = Integer.toString(dataElement.getComparisonPeriod2Start());
		String period1End = Integer.toString(dataElement.getComparisonPeriod1Start()
				+ (MapView.COMPARISON_PERIOD_LENGTH - 1)); 
		String period2End = Integer.toString(dataElement.getComparisonPeriod2Start()
				+ (MapView.COMPARISON_PERIOD_LENGTH - 1));
		String validYearsPeriod1 = Integer.toString(dataElement.getValidYearsPeriod1());
		String validYearsPeriod2 = Integer.toString(dataElement.getValidYearsPeriod2());
		String temperaturePeriod1 = dataElement.getTemperaturePeriod1String();
		String temperaturePeriod2 = dataElement.getTemperaturePeriod2String();
		String uncertaintyPeriod1 = dataElement.getUncertaintyPeriod1String();
		String uncertaintyPeriod2 = dataElement.getUncertaintyPeriod2String();
		String temperatureDifference = dataElement.getTemperatureDifferenceString();

		// Use HTML in a stringBuilder to format the info window.
		sb.append("<style type=\"text/css\"> tab { padding-left: 1em; } </style>");
		sb.append("<b>" + city + "</b><br>");
		
		sb.append(period1Start + " - " + period1End + ":<br>");
		sb.append("<tab>Avg. Temperature: " + temperaturePeriod1);
		if(dataElement.getTemperaturePeriod1() < Double.MAX_VALUE) {
			sb.append(" °C");
		}
		sb.append("</tab><br>");
		sb.append("<tab>Avg. Uncertainty: " + uncertaintyPeriod1); 
		if(dataElement.getUncertaintyPeriod1() < Double.MAX_VALUE) {
			sb.append(" °C");
		}
		sb.append("</tab><br>");
		sb.append("<tab>Valid Years: " + validYearsPeriod1 + "</tab><br>");
		
		
		sb.append(period2Start + " - " + period2End + ":<br>");
		sb.append("<tab>Avg. Temperature: " + temperaturePeriod2);
		if(dataElement.getTemperaturePeriod2() < Double.MAX_VALUE) {
			sb.append(" °C");
		}
		sb.append("</tab><br>");
		sb.append("<tab>Avg. Uncertainty: " + uncertaintyPeriod2); 
		if(dataElement.getUncertaintyPeriod2() < Double.MAX_VALUE) {
			sb.append(" °C");
		}
		sb.append("</tab><br>");		
		sb.append("<tab>Valid Years: " + validYearsPeriod2 + "</tab><br>"); 
		
		sb.append("<b>Temperature Difference: ");
		if(dataElement.getTemperatureDifference() >= Double.MAX_VALUE) {
			sb.append(temperatureDifference);
		} else if(dataElement.getTemperatureDifference() > TEMPERATURE_CHANGE_THRESHOLD_NEUTRAL) {
			sb.append("<span style=\"color:red;\">" + temperatureDifference + " °C </span>");
		} else if (dataElement.getTemperatureDifference() < -TEMPERATURE_CHANGE_THRESHOLD_NEUTRAL){
			sb.append("<span style=\"color:blue;\">" + temperatureDifference + " °C </span>");
		} else {
			sb.append("<span style=\"color:green;\">" + temperatureDifference + " °C </span>");
		}
		sb.append("</b><br>");

		HTML html = new HTML(sb.toString());

		InfoWindowOptions options = InfoWindowOptions.newInstance();
		options.setContent(html);
		
		// TODO: Offset does not seem to work as intended (moving the tip of the info window to the centre of the
		//		 marker). We might need to find another way for this.
		Size offset = Size.newInstance(8, 8);
		options.setPixelOffet(offset);
		
		InfoWindow infoWindow = InfoWindow.newInstance(options);
		currentInfoWindow = infoWindow;
		infoWindow.open(mapWidget, marker);
	}

	private void initializeLayers() {
		// Set the properties for each layer.
		HeatMapLayerOptions heatMapLayerOptionsBlack = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsBlack.setRadius(20);
		heatMapLayerOptionsBlack.setGradient(black());
		heatMapLayerOptionsBlack.setMap(mapWidget);
		heatMapLayerBlack = HeatMapLayer.newInstance(heatMapLayerOptionsBlack);
		
		HeatMapLayerOptions heatMapLayerOptionsGreen = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsGreen.setRadius(20);
		heatMapLayerOptionsGreen.setGradient(green());
		heatMapLayerOptionsGreen.setMap(mapWidget);
		heatMapLayerGreen = HeatMapLayer.newInstance(heatMapLayerOptionsGreen);
		
		HeatMapLayerOptions heatMapLayerOptionsLightBlue = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsLightBlue.setRadius(20);
		heatMapLayerOptionsLightBlue.setGradient(lightBlue());
		heatMapLayerOptionsLightBlue.setMap(mapWidget);
		heatMapLayerLightBlue = HeatMapLayer.newInstance(heatMapLayerOptionsLightBlue);

		HeatMapLayerOptions heatMapLayerOptionsMediumBlue = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsMediumBlue.setRadius(20);
		heatMapLayerOptionsMediumBlue.setGradient(mediumBlue());
		heatMapLayerOptionsMediumBlue.setMap(mapWidget);
		heatMapLayerMediumBlue = HeatMapLayer.newInstance(heatMapLayerOptionsMediumBlue);
		
		HeatMapLayerOptions heatMapLayerOptionsDarkBlue = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsDarkBlue.setRadius(20);
		heatMapLayerOptionsDarkBlue.setGradient(darkBlue());
		heatMapLayerOptionsDarkBlue.setMap(mapWidget);
		heatMapLayerDarkBlue = HeatMapLayer.newInstance(heatMapLayerOptionsDarkBlue);
		
		HeatMapLayerOptions heatMapLayerOptionsLightRed = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsLightRed.setRadius(20);
		heatMapLayerOptionsLightRed.setGradient(lightRed());
		heatMapLayerOptionsLightRed.setMap(mapWidget);
		heatMapLayerLightRed = HeatMapLayer.newInstance(heatMapLayerOptionsLightRed);
		
		HeatMapLayerOptions heatMapLayerOptionsMediumRed = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsMediumRed.setRadius(20);
		heatMapLayerOptionsMediumRed.setGradient(mediumRed());
		heatMapLayerOptionsMediumRed.setMap(mapWidget);
		heatMapLayerMediumRed = HeatMapLayer.newInstance(heatMapLayerOptionsMediumRed);
		
		HeatMapLayerOptions heatMapLayerOptionsDarkRed = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsDarkRed.setRadius(20);
		heatMapLayerOptionsDarkRed.setGradient(darkRed());
		heatMapLayerOptionsDarkRed.setMap(mapWidget);
		heatMapLayerDarkRed = HeatMapLayer.newInstance(heatMapLayerOptionsDarkRed);
	}
	
	private void injectDataIntoHeatMapLayers() {
		// The order in which the data is injected into each layer matters since they overlapp each other.
		// The last layer to which data is added lies on top of every other layer.
		LatLng[] coordinatesInvalidArray = new LatLng[coordinatesInvalid.size()];
		coordinatesInvalidArray = coordinatesInvalid.toArray(coordinatesInvalidArray);
		heatMapLayerBlack.setData(getLocation(coordinatesInvalidArray));
		
		LatLng[] coordinatesNeutralArray = new LatLng[coordinatesNeutral.size()];
		coordinatesNeutralArray = coordinatesNeutral.toArray(coordinatesNeutralArray);
		heatMapLayerGreen.setData(getLocation(coordinatesNeutralArray));
		
		LatLng[] coordinatesDecendingModerateArray = new LatLng[coordinatesDecendingModerate.size()];
		coordinatesDecendingModerateArray = coordinatesDecendingModerate.toArray(coordinatesDecendingModerateArray);
		heatMapLayerLightBlue.setData(getLocation(coordinatesDecendingModerateArray));
		
		LatLng[] coordinatesDecendingMediumArray = new LatLng[coordinatesDecendingMedium.size()];
		coordinatesDecendingMediumArray = coordinatesDecendingMedium.toArray(coordinatesDecendingMediumArray);
		heatMapLayerMediumBlue.setData(getLocation(coordinatesDecendingMediumArray));
		
		LatLng[] coordinatesDecendingHighArray = new LatLng[coordinatesDecendingHigh.size()];
		coordinatesDecendingHighArray = coordinatesDecendingHigh.toArray(coordinatesDecendingHighArray);
		heatMapLayerDarkBlue.setData(getLocation(coordinatesDecendingHighArray));
		
		LatLng[] coordinatesAscendingModerateArray = new LatLng[coordinatesAscendingModerate.size()];
		coordinatesAscendingModerateArray = coordinatesAscendingModerate.toArray(coordinatesAscendingModerateArray);
		heatMapLayerLightRed.setData(getLocation(coordinatesAscendingModerateArray));

		LatLng[] coordinatesAscendingMediumArray = new LatLng[coordinatesAscendingMedium.size()];
		coordinatesAscendingMediumArray = coordinatesAscendingMedium.toArray(coordinatesAscendingMediumArray);
		heatMapLayerMediumRed.setData(getLocation(coordinatesAscendingMediumArray));
		
		LatLng[] coordinatesAscendingHighArray = new LatLng[coordinatesAscendingHigh.size()];
		coordinatesAscendingHighArray = coordinatesAscendingHigh.toArray(coordinatesAscendingHighArray);
		heatMapLayerDarkRed.setData(getLocation(coordinatesAscendingHighArray));
	}
	
	private void resetData() {
		for(int i = 0; i < setMarkers.size(); i++) {
			setMarkers.get(i).clear();
		}
		setMarkers.clear();
		coordinatesInvalid.clear();
		coordinatesNeutral.clear();
		coordinatesDecendingModerate.clear();
		coordinatesDecendingMedium.clear();
		coordinatesDecendingHigh.clear();
		coordinatesAscendingModerate.clear();
		coordinatesAscendingMedium.clear();
		coordinatesAscendingHigh.clear();
		
	}

	private List<LatLng> getHeatMapLayerIDForClimateChange(double temperaturePeriod1, double temperaturePeriod2) {
		// TODO: We might want to set a city invalid if one period has not a certain amount of valid years (meaning
		// years that have at least one invalid month)
		
		if(temperaturePeriod1 >= Double.MAX_VALUE || temperaturePeriod2 >= Double.MAX_VALUE) {
			return coordinatesInvalid;
		}
		
		double temperatureDifference = temperaturePeriod2 - temperaturePeriod1;

		if(temperatureDifference >= 0) {
			if(temperatureDifference <= TEMPERATURE_CHANGE_THRESHOLD_NEUTRAL) {
				return coordinatesNeutral;
			} else if(temperatureDifference <= TEMPERATURE_CHANGE_THRESHOLD_ASCENDING_MODERATE) {
				return coordinatesAscendingModerate;
			} else if(temperatureDifference <= TEMPERATURE_CHANGE_THRESHOLD_ASCENDING_MEDIUM) {
				return coordinatesAscendingMedium;
			} else {
				return coordinatesAscendingHigh;
			}
		} else {
			if(temperatureDifference >= TEMPERATURE_CHANGE_THRESHOLD_NEUTRAL * (-1)) {
				return coordinatesNeutral;
			} else if(temperatureDifference >= TEMPERATURE_CHANGE_THRESHOLD_DECENDING_MODERATE) {
				return coordinatesDecendingModerate;
			} else if(temperatureDifference >= TEMPERATURE_CHANGE_THRESHOLD_DECENDING_MEDIUM) {
				return coordinatesDecendingMedium;
			} else {
				return coordinatesDecendingHigh;
			}
		}
	}
	
	private JsArray<LatLng> getLocation(LatLng[] coordinates) {
		return ArrayHelper.toJsArray(coordinates);
	}

	// Layer 1: black.
	private JsArrayString black() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(150,150,150, 1)", "rgba(150,150,150, 1)",
				"rgba(150,150,150, 1)", "rgba(150,150,150, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}
	
	// Layer 2: green.
	private JsArrayString green() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)","rgba(70,255,70, 1)", "rgba(70,255,70, 1)",
				"rgba(70,255,70, 1)", "rgba(70,255,70, 1)", "rgba(70,255,70, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}
	
	// Layer 3: light blue.
	private JsArrayString lightBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,210,255, 1)", "rgba(0,210,255, 1)",
				"rgba(0,210,255, 1)", "rgba(0,210,250, 1)", "rgba(0,210,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// Layer 4: medium blue.
	private JsArrayString mediumBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)",
				"rgba(0,80,255, 1)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// Layer 5: dark blue.
	private JsArrayString darkBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)",
				"rgba(0,40,255, 1)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 6 light red.
	private JsArrayString lightRed() {
		String[] sampleColors = new String[] {  "rgba(0,0,0, 0)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)",
				"rgba(255,119,0, 1)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// Layer 7: medium red.
	private JsArrayString mediumRed() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)",
				"rgba(255,70,0, 1)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// Layer 8: dark red.
	private JsArrayString darkRed() {
		String[] sampleColors = new String[] {"rgba(0,0,0, 0)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)",
				"rgba(255,20,0, 1)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

}
