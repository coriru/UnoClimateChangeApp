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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.softwareengineering.climatechangeviewer.shared.InvalidInputException;
import ch.uzh.softwareengineering.climatechangeviewer.shared.MapDataElement;
import ch.uzh.softwareengineering.climatechangeviewer.shared.QueryService;
import ch.uzh.softwareengineering.climatechangeviewer.shared.QueryServiceAsync;

public class MapView extends Composite {
	
	interface MapViewUiBinder extends UiBinder<Widget, MapView> {}
	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);
	
	private static final String GOOGLE_MAPS_API_KEY = "key=AIzaSyAD26ixCbchqW1MM5LieOuIj8VJZR3k6KM";
	public static final int COMPARISON_PERIOD_LENGTH = 10;

	private QueryServiceAsync querySvc = GWT.create(QueryService.class);	
	private boolean isBusy = false;
	
	private Image loadingIndicator = new Image("/images/loading-indicator_map_1000x555.gif");
	private VerticalPanel loadingPanel = new VerticalPanel();
	private VerticalPanel climateChangeMapWidgetWidgetPanel = new VerticalPanel();
	private ClimateChangeMapWidget climateChangeMapWidget;
	
	@UiField FlowPanel mapViewPanel;
	@UiField(provided = true) MapFilter filter = new MapFilter(this);
	private Button filterButton = filter.filterButton;
	
	public MapView() {
		initWidget(uiBinder.createAndBindUi(this));
		loadMapApi();
		
		// This panel is only used because the climateChangeMapWidget breaks if it gets removed from the mapViewPanel
		// directly.
		climateChangeMapWidgetWidgetPanel.setStyleName("mapWidgetPanel");
		loadingPanel.setStyleName("mapLoadingPanel");
		loadingPanel.add(loadingIndicator);
		mapViewPanel.add(climateChangeMapWidgetWidgetPanel);
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
		showLoadingIndicator();
		
		// Initialize the service proxy.
		if (querySvc == null) {
		querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<MapDataElement>> callback = new AsyncCallback<List<MapDataElement>>() {
			public void onFailure(Throwable e) {
				if(e instanceof DataFileCorruptedException) {
					hideLoadingIndicator();
					setFilterReady();
					
					// Create error message for the user.
					Window.alert("The datafile is corrupted. The service is unavailable at the moment.");
				} else {
					hideLoadingIndicator();
					setFilterReady();
					
					// Create error message for the user.
					Window.alert("Unknown error. The service is unavailable at the moment.");
				}
			}

			public void onSuccess(List<MapDataElement> result) {
				hideLoadingIndicator();
				setFilterReady();
				
				// Calling the following method before the widget is shown on the screen will break the widget.
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
	    		climateChangeMapWidgetWidgetPanel.add(climateChangeMapWidget);
	    	}
	    };
	    LoadApi.go(onLoad, loadLibraries, sensor, GOOGLE_MAPS_API_KEY);
	}
	
	public void showLoadingIndicator() {
		mapViewPanel.remove(climateChangeMapWidgetWidgetPanel);
		mapViewPanel.add(loadingPanel);
	}
	
	public void hideLoadingIndicator() {
		mapViewPanel.remove(loadingPanel);
		mapViewPanel.add(climateChangeMapWidgetWidgetPanel);
	}
	
	private void setFilterReady() {
		//Allow new requests.
		isBusy = false;
		filterButton.setEnabled(true);
	}

	public MapFilter getFilter() {
		return filter;
	}
	
}
