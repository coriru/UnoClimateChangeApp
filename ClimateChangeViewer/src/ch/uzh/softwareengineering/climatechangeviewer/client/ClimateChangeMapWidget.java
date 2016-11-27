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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClimateChangeMapWidget extends Composite {

	private static final int COMPARISON_PERIOD_LENGTH = 10;
	private static float TEMPERATURE_CHANGE_THRESHOLD_NEUTRAL = 0.25f;
	private static float TEMPERATURE_CHANGE_THRESHOLD_ASCENDING_MODERATE = 1.0f;
	private static float TEMPERATURE_CHANGE_THRESHOLD_ASCENDING_MEDIUM = 2.0f;
	private static float TEMPERATURE_CHANGE_THRESHOLD_DECENDING_MODERATE = -1.0f;
	private static float TEMPERATURE_CHANGE_THRESHOLD_DECENDING_MEDIUM = -2.0f;

	private VerticalPanel mapPanel = new VerticalPanel();
	private MapWidget mapWidget;

	private HeatMapLayer heatMapLayerLightBlue;
	private HeatMapLayer heatMapLayerMediumBlue;
	private HeatMapLayer heatMapLayerDarkBlue;
	private HeatMapLayer heatMapLayerLightRed;
	private HeatMapLayer heatMapLayerMediumRed;
	private HeatMapLayer heatMapLayerDarkRed;
	private HeatMapLayer heatMapLayerGreen;
	private HeatMapLayer heatMapLayerBlack;
	
	
	private List<LatLng> coordinatesAscendingModerate;
	private List<LatLng> coordinatesAscendingMedium;
	private List<LatLng> coordinatesAscendingHigh;
	private List<LatLng> coordinatesDecendingModerate;
	private List<LatLng> coordinatesDecendingMedium;
	private List<LatLng> coordinatesDecendingHigh;
	private List<LatLng> coordinatesNeutral;
	private List<LatLng> coordinatesInvalid;
	
	public ClimateChangeMapWidget() {
		initWidget(mapPanel);
		draw();
	}

	private void draw() {
		// Set map options and draw it.
		LatLng center = LatLng.newInstance(25, 0);
		MapOptions opts = MapOptions.newInstance();
		opts.setStreetViewControl(false);
		opts.setMapTypeControl(false);
		opts.setKeyboardShortcuts(false);
		opts.setCenter(center);
		opts.setMapTypeId(MapTypeId.HYBRID);
		opts.setDraggable(true);
		opts.setScrollWheel(true);
		opts.setZoom(2);
		opts.setMaxZoom(4);
		opts.setMinZoom(2);
		mapWidget = new MapWidget(opts);

		// Add map to panel.
		mapPanel.add(mapWidget);
		mapWidget.setSize("1000px", "600px");
	}
	
	public void drawClimateChangeModel(final List<MapDataElement> mapData) {
		initializeLayers();
		initializeCoordinatesArrays();
		
		Size imageSize = Size.newInstance(32,32);
		Point imageOrigin = Point.newInstance(10,10);
		Point imageAnchor = Point.newInstance(15,15);
		MarkerImage markerImage = MarkerImage.newInstance("images/invisible32x32.ico", imageSize, imageOrigin, imageAnchor);
		
		for(int i = 0; i <= mapData.size(); i++) {
			final MapDataElement dataElement = mapData.get(i);
			float latitude = dataElement.getLatitude();
			float longitude = dataElement.getLongitude();
			LatLng location = LatLng.newInstance(latitude, longitude);
			float temperaturePeriod1 = dataElement.getTemperaturePeriod1();
			float temperaturePeriod2 = dataElement.getTemperaturePeriod2();
			
			getHeatMapLayerIDForClimateChange(temperaturePeriod1, temperaturePeriod2).add(location);
			
			MarkerOptions markerOptions = MarkerOptions.newInstance();
			markerOptions.setMap(mapWidget);
			markerOptions.setPosition(location);
			markerOptions.setIcon(markerImage);
			markerOptions.setVisible(true);
			
			final Marker marker = Marker.newInstance(markerOptions);
			marker.setMap(mapWidget);

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
		if (marker == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		String period1Start = Integer.toString(dataElement.getComparisonPeriod1Start());
		String period2Start = Integer.toString(dataElement.getComparisonPeriod2Start());
		String period1End = Integer.toString(dataElement.getComparisonPeriod1Start() + COMPARISON_PERIOD_LENGTH); 
		String period2End = Integer.toString(dataElement.getComparisonPeriod2Start() + COMPARISON_PERIOD_LENGTH); 

		sb.append("<style type=\"text/css\"> tab { padding-left: 1em; } </style>");
		sb.append("<b>" + dataElement.getCity() + "</b><br>");
		sb.append(period1Start + " - " + period1End + ":<br>");
		sb.append("<tab>Avg. Temperature: " + dataElement.getTemperaturePeriod1String() + " °C</tab><br>"); 
		sb.append("<tab>Avg. Uncertainty: " + dataElement.getUncertaintyPeriod1String() + " °C</tab><br>"); 
		sb.append("<tab>Valid Years: " + dataElement.getValidYearsPeriod1() + "</tab><br>"); 
		sb.append(period2Start + " - " + period2End + ":<br>");
		sb.append("<tab>Avg. Temperature: " + dataElement.getTemperaturePeriod2String() + " °C</tab><br>"); 
		sb.append("<tab>Avg. Uncertainty: " + dataElement.getUncertaintyPeriod2String() + " °C</tab><br>");
		sb.append("<tab>Valid Years: " + dataElement.getValidYearsPeriod2() + "</tab><br>"); 

		HTML html = new HTML(sb.toString());

		InfoWindowOptions options = InfoWindowOptions.newInstance();
		options.setContent(html);
		InfoWindow iw = InfoWindow.newInstance(options);
		iw.open(mapWidget, marker);
	}

	private void initializeLayers() {
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
		
		HeatMapLayerOptions heatMapLayerOptionsGreen = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsGreen.setRadius(20);
		heatMapLayerOptionsGreen.setGradient(green());
		heatMapLayerOptionsGreen.setMap(mapWidget);
		heatMapLayerGreen = HeatMapLayer.newInstance(heatMapLayerOptionsGreen);
		
		HeatMapLayerOptions heatMapLayerOptionsBlack = HeatMapLayerOptions.newInstance();
		heatMapLayerOptionsBlack.setRadius(20);
		heatMapLayerOptionsBlack.setGradient(black());
		heatMapLayerOptionsBlack.setMap(mapWidget);
		heatMapLayerBlack = HeatMapLayer.newInstance(heatMapLayerOptionsBlack);
	}
	
	private void initializeCoordinatesArrays() {
		coordinatesAscendingModerate = new ArrayList<LatLng>();
		coordinatesAscendingMedium = new ArrayList<LatLng>();
		coordinatesAscendingHigh = new ArrayList<LatLng>();
		coordinatesDecendingModerate = new ArrayList<LatLng>();
		coordinatesDecendingMedium = new ArrayList<LatLng>();
		coordinatesDecendingHigh = new ArrayList<LatLng>();
		coordinatesNeutral = new ArrayList<LatLng>();
		coordinatesInvalid = new ArrayList<LatLng>();
	}
	
	private void injectDataIntoHeatMapLayers() {
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

		LatLng[] coordinatesNeutralArray = new LatLng[coordinatesNeutral.size()];
		coordinatesNeutralArray = coordinatesNeutral.toArray(coordinatesNeutralArray);
		heatMapLayerGreen.setData(getLocation(coordinatesNeutralArray));
		
		LatLng[] coordinatesInvalidArray = new LatLng[coordinatesInvalid.size()];
		coordinatesNeutralArray = coordinatesInvalid.toArray(coordinatesInvalidArray);
		heatMapLayerBlack.setData(getLocation(coordinatesInvalidArray));
	}

	private List<LatLng> getHeatMapLayerIDForClimateChange(float temperaturePeriod1, float temperaturePeriod2) {
		if(temperaturePeriod1 >= Float.MAX_VALUE || temperaturePeriod2 >= Float.MAX_VALUE) {
			return coordinatesInvalid;
		}
		
		float temperatureDifference = temperaturePeriod2 - temperaturePeriod1;

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

	// layer 1 light blue
	private JsArrayString lightBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,210,255, 1)", "rgba(0,210,255, 1)",
				"rgba(0,210,255, 1)", "rgba(0,210,250, 1)", "rgba(0,210,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 2 medium blue
	private JsArrayString mediumBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)",
				"rgba(0,80,255, 1)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 3 dark blue
	private JsArrayString darkBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)",
				"rgba(0,40,255, 1)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 4 light red
	private JsArrayString lightRed() {
		String[] sampleColors = new String[] {  "rgba(0,0,0, 0)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)",
				"rgba(255,119,0, 1)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 5 medium red
	private JsArrayString mediumRed() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)",
				"rgba(255,70,0, 1)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 6 dark red
	private JsArrayString darkRed() {
		String[] sampleColors = new String[] {"rgba(0,0,0, 0)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)",
				"rgba(255,20,0, 1)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 7 green
	private JsArrayString green() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)","rgba(70,255,70, 1)", "rgba(70,255,70, 1)",
				"rgba(70,255,70, 1)", "rgba(70,255,70, 1)", "rgba(70,255,70, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 8 black
	private JsArrayString black() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(150,150,150, 1)", "rgba(150,150,150, 1)",
				"rgba(150,150,150, 1)", "rgba(150,150,150, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

}
