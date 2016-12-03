package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MapView extends Composite {
	
	interface MapViewUiBinder extends UiBinder<Widget, MapView> {}
	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);
	
	private static final String GOOGLE_MAPS_API_KEY = "key=AIzaSyAD26ixCbchqW1MM5LieOuIj8VJZR3k6KM";
	public static final int COMPARISON_PERIOD_LENGTH = 10;
	// TODO: We might want to get these values directly from the QueryServiceImpl instead of hard coding them.
	public static final int OLDEST_YEAR_IN_DATAFILE = 1745;
	public static final int YOUNGEST_YEAR_IN_DATAFILE = 2012;

	private boolean isBusy = false;
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	
	@UiField FlowPanel mapViewPanel;
	@UiField(provided = true) MapFilter filter = new MapFilter(this);
	
	private ClimateChangeMapWidget climateChangeMapWidget;
	private Button filterButton = filter.filterButton;
	
	public MapView() {
		initWidget(uiBinder.createAndBindUi(this));
		loadMapApi();
	}
	
	public void getMapData() {
		if(isBusy) {
			return;
		} else {
			filterButton.setEnabled(false);
			isBusy = true;
		}

		try {
			filter.setFilterValues();
		} catch (InvalidInputException e) {
			isBusy = false;
			filterButton.setEnabled(true);
			return;
		}

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
					isBusy = false;
					filterButton.setEnabled(true);	
				} else {
					// Create error message for the user.
					Window.alert("Unknown error. The service is unavailable at the moment.");
					isBusy = false;
					filterButton.setEnabled(true);	
				}
			}

			public void onSuccess(List<MapDataElement> result) {
				isBusy = false;
				filterButton.setEnabled(true);
				climateChangeMapWidget.drawClimateChangeModel(result);
			}
		};

		// Make the call to the queryService.		
		querySvc.getMapData(filter.getPeriod1Query(), filter.getPeriod2Query(), filter.getUncertaintyQuery(), callback);

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
	    		mapViewPanel.add(climateChangeMapWidget);
	    	}
	    };
	    LoadApi.go(onLoad, loadLibraries, sensor, GOOGLE_MAPS_API_KEY);
	}
	
	public MapFilter getFilter() {
		return filter;
	}
	
}
