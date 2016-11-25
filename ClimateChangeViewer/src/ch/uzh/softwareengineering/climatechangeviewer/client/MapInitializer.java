package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.ajaxloader.client.ArrayHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.adsense.AdFormat;
import com.google.gwt.maps.client.adsense.AdUnitOptions;
import com.google.gwt.maps.client.adsense.AdUnitWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.controls.ControlPosition;
import com.google.gwt.maps.client.events.channelnumber.ChannelNumberChangeMapEvent;
import com.google.gwt.maps.client.events.channelnumber.ChannelNumberChangeMapHandler;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.events.format.FormatChangeMapEvent;
import com.google.gwt.maps.client.events.format.FormatChangeMapHandler;
import com.google.gwt.maps.client.events.mapchange.MapChangeMapEvent;
import com.google.gwt.maps.client.events.mapchange.MapChangeMapHandler;
import com.google.gwt.maps.client.events.position.PositionChangeMapEvent;
import com.google.gwt.maps.client.events.position.PositionChangeMapHandler;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.visualizationlib.HeatMapLayer;
import com.google.gwt.maps.client.visualizationlib.HeatMapLayerOptions;
import com.google.gwt.maps.client.visualizationlib.WeightedLocation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MapInitializer extends Composite {
	
	private VerticalPanel mapPanel = new VerticalPanel();
	private MapWidget mapWidget;
	
	private HeatMapLayerOptions lightBlueOption;
	private HeatMapLayerOptions mediumBlueOption;
	private HeatMapLayerOptions darkBlueOption;
	private HeatMapLayerOptions lightRedOption;
	private HeatMapLayerOptions mediumRedOption;
	private HeatMapLayerOptions darkRedOption;
	private HeatMapLayerOptions greenOption;
	private HeatMapLayerOptions blackOption;
	
	public MapInitializer() {
		draw();
	}
	
	public void draw() {
		drawMap();
		drawMapAds();
		initializeLayerOptions();
		
		//following lines serve testing purposes only and are safe to delete
		addDataPointToMap(30, 30, lightBlueOption);
		addDataPointToMap(30, 40, mediumBlueOption);
		addDataPointToMap(30, 50, darkBlueOption);
		addDataPointToMap(20, 30, lightRedOption);
		addDataPointToMap(20, 40, mediumRedOption);
		addDataPointToMap(20, 50, darkRedOption);
		addDataPointToMap(10, 30, greenOption);
		addDataPointToMap(0, 30, blackOption);
		
	}
	
	private void initializeLayerOptions() {
		lightBlueOption = HeatMapLayerOptions.newInstance();
		lightBlueOption.setRadius(15);
		lightBlueOption.setGradient(lightBlue());
		lightBlueOption.setMap(mapWidget);
		
		mediumBlueOption = HeatMapLayerOptions.newInstance();
		mediumBlueOption.setRadius(15);
		mediumBlueOption.setGradient(mediumBlue());
		mediumBlueOption.setMap(mapWidget);
		
		darkBlueOption = HeatMapLayerOptions.newInstance();
		darkBlueOption.setRadius(15);
		darkBlueOption.setGradient(darkBlue());
		darkBlueOption.setMap(mapWidget);
		
		lightRedOption = HeatMapLayerOptions.newInstance();
		lightRedOption.setRadius(15);
		lightRedOption.setGradient(lightRed());
		lightRedOption.setMap(mapWidget);
		
		mediumRedOption = HeatMapLayerOptions.newInstance();
		mediumRedOption.setRadius(15);
		mediumRedOption.setGradient(mediumRed());
		mediumRedOption.setMap(mapWidget);
		
		darkRedOption= HeatMapLayerOptions.newInstance();
		darkRedOption.setRadius(15);
		darkRedOption.setGradient(darkRed());
		darkRedOption.setMap(mapWidget);
		
		greenOption= HeatMapLayerOptions.newInstance();
		greenOption.setRadius(15);
		greenOption.setGradient(green());
		greenOption.setMap(mapWidget);
		
		blackOption= HeatMapLayerOptions.newInstance();
		blackOption.setRadius(15);
		blackOption.setGradient(black());
		blackOption.setMap(mapWidget);
	}
	
	private void drawMap() {
		
		//sets map options and draws it
	    LatLng center = LatLng.newInstance(25, 0);
	    MapOptions opts = MapOptions.newInstance();
	    opts.setZoom(2);
	    opts.setCenter(center);
	    opts.setMapTypeId(MapTypeId.HYBRID);
	    opts.setDraggable(true);
	    opts.setScrollWheel(true);
	    opts.setMaxZoom(8);
	    opts.setMinZoom(2);

	    mapWidget = new MapWidget(opts);
	    //adds map to panel
	    mapPanel.add(mapWidget);
	    mapWidget.setSize("1000px", "600px");

	    mapWidget.addClickHandler(new ClickMapHandler() {
	      @Override
	      public void onEvent(ClickMapEvent event) {
	        // TODO fix the event getting, getting ....
	        GWT.log("clicked on latlng=" + event.getMouseEvent().getLatLng());
	      }
	    });
	  }
	
	private void drawMapAds() {
		
		//sets map ads options and draws them
		AdUnitOptions options = AdUnitOptions.newInstance();
		options.setFormat(AdFormat.HALF_BANNER);
		options.setPosition(ControlPosition.RIGHT_CENTER);
		//adds map ads to panel
		options.setMap(mapWidget);
		options.setPublisherId("pub-0032065764310410");
		options.setChannelNumber("4000893900");

		AdUnitWidget adUnit = new AdUnitWidget(options);

		adUnit.addChannelNumberChangeHandler(new ChannelNumberChangeMapHandler() {
	      @Override
	      public void onEvent(ChannelNumberChangeMapEvent event) {
		  }
		});

		adUnit.addFormatChangeHandler(new FormatChangeMapHandler() {
		  @Override
		  public void onEvent(FormatChangeMapEvent event) {
		  }
		});

		adUnit.addMapChangeHandler(new MapChangeMapHandler() {
		  @Override
		  public void onEvent(MapChangeMapEvent event) {
		  }
		});

	    adUnit.addPositionChangeHandler(new PositionChangeMapHandler() {
	      @Override
	      public void onEvent(PositionChangeMapEvent event) {
	      }
	    });
	}
	
	private void addDataPointToMap(float latitude, float longitude, HeatMapLayerOptions layerOptions) {
		
		Size imageSize = Size.newInstance(32,32);
		Point imageOrigin = Point.newInstance(10,10);
		Point imageAnchor = Point.newInstance(15,15);
		MarkerImage markerImage = MarkerImage.newInstance("images/invisible32x32.ico", imageSize, imageOrigin, imageAnchor);
		
		HeatMapLayer heatMapLayer = HeatMapLayer.newInstance(layerOptions);
		heatMapLayer.setDataWeighted(getLocation(latitude, longitude));
		MarkerOptions markerOptions = MarkerOptions.newInstance();
		LatLng markerPostition = LatLng.newInstance(30,30);
		markerOptions.setMap(mapWidget);
		markerOptions.setPosition(markerPostition);
		markerOptions.setIcon(markerImage);
		markerOptions.setVisible(true);
		Marker marker = Marker.newInstance(markerOptions);
		marker.setMap(mapWidget);
	}
	
	//returns coordinates for heat map layer
	private JsArray<WeightedLocation> getLocation(float latitude, float longitude) {
		
		LatLng cordinates = LatLng.newInstance(latitude, longitude);
		WeightedLocation[] weightedCoordinates  = {WeightedLocation.newInstance(cordinates, 1)};
		return ArrayHelper.toJsArray(weightedCoordinates);
	}
	
	// layer 1 light blue
	private JsArrayString lightBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,210,255, 1)", "rgba(0,210,255, 1)", "rgba(0,210,255, 1)", "rgba(0,210,250, 1)", "rgba(0,210,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 2 medium blue
	private JsArrayString mediumBlue() {
	    String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)", "rgba(0,80,255, 1)"};
	    return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 3 dark blue
	private JsArrayString darkBlue() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)", "rgba(0,40,255, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}
		
	// layer 4 light red
	private JsArrayString lightRed() {
		String[] sampleColors = new String[] {  "rgba(0,0,0, 0)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)", "rgba(255,119,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 5 medium red
	private JsArrayString mediumRed() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)", "rgba(255,70,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 6 dark red
	private JsArrayString darkRed() {
		String[] sampleColors = new String[] {"rgba(0,0,0, 0)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)", "rgba(255,20,0, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}

	// layer 7 green
	private JsArrayString green() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)","rgba(70,255,70, 1)", "rgba(70,255,70, 1)", "rgba(70,255,70, 1)", "rgba(70,255,70, 1)", "rgba(70,255,70, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}
		
	// layer 8 black
	private JsArrayString black() {
		String[] sampleColors = new String[] { "rgba(0,0,0, 0)", "rgba(150,150,150, 1)", "rgba(150,150,150, 1)", "rgba(150,150,150, 1)", "rgba(150,150,150, 1)"};
		return ArrayHelper.toJsArrayString(sampleColors);
	}
	
	public Panel getMapPanel(){
		
		return mapPanel;
	}
}
