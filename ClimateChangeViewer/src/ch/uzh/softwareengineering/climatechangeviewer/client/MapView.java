package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MapView extends View {
	
	private static final String GOOGLE_MAPS_API_KEY = "key=AIzaSyAD26ixCbchqW1MM5LieOuIj8VJZR3k6KM";
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private ClimateChangeMapWidget climateChangeMapWidget;
	
	public MapView() {
		loadMapApi();
		getMapData();
	}
	
	public void loadMapApi() {	
		// Load Map API.
		boolean sensor = true;
		ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
	    loadLibraries.add(LoadLibrary.ADSENSE);
	    loadLibraries.add(LoadLibrary.DRAWING);
	    loadLibraries.add(LoadLibrary.GEOMETRY);
	    loadLibraries.add(LoadLibrary.PANORAMIO);
	    loadLibraries.add(LoadLibrary.PLACES);
	    loadLibraries.add(LoadLibrary.WEATHER);
	    loadLibraries.add(LoadLibrary.VISUALIZATION);

	    Runnable onLoad = new Runnable() {
	    	@Override
	    	public void run() {
	    		climateChangeMapWidget = new ClimateChangeMapWidget();
	    		mainPanel.add(climateChangeMapWidget);
	    	}
	    };
	    LoadApi.go(onLoad, loadLibraries, sensor, GOOGLE_MAPS_API_KEY);
	}
	
	public void getMapData() {
		// Initialize the service proxy.
		if (querySvc == null) {
			querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<MapDataElement>> callback = new AsyncCallback<List<MapDataElement>>() {
			public void onFailure(Throwable caught) {
				if(caught instanceof DataFileCorruptedException) {
					// Create error message for the user.
					Window.alert("The datafile is corrupted. The service is unavailable at the moment.");
					
				} else {
					// Create error message for the user.
					Window.alert("Unknown error. The service is unavailable at the moment.");
				}
			}

			public void onSuccess(List<MapDataElement> result) {
				climateChangeMapWidget.drawClimateChangeModel(result);
			}
		};

		// Make the call to the queryService.		
		querySvc.getMapData(1900, 2000, callback);

	}
	
	public Panel getPanel() {
		return mainPanel;
	}
	
}
