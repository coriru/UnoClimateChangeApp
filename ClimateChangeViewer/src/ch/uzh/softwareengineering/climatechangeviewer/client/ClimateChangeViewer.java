package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ClimateChangeViewer implements EntryPoint {
	
	private TableView tableView = new TableView();
	private MapView mapView;
	private HorizontalPanel switchPanel = new HorizontalPanel();
	private Button switchToTableViewButton = new Button("Table");
	private Button switchToMapViewButton = new Button("Map");
	private boolean initializing = true;
	  
	  	/**
	  	 * Entry point method.
	  	 */
		public void onModuleLoad() {
			
			loadMapApi();
			
			switchPanel.add(switchToTableViewButton);
			switchPanel.add(switchToMapViewButton);
			
			switchToTableViewButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					switchToTableView();
				}
			});
			
			switchToMapViewButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					switchToMapView();
				}
			});
			
			// Associate the switch panel with the HTML host page.
			RootPanel.get("dataList").add(switchPanel);
			
			// The tableView panel is considered as the start screen of the app (for now).
			switchToTableView();
			
		}
		
		private void switchToTableView() {
			if(initializing != true) {
				RootPanel.get("dataList").remove(mapView.getPanel());
			}else {
				initializing = false;
			}
			RootPanel.get("dataList").add(tableView.getPanel());
			// TODO Remove first the MapView from RootPanel once implemented.
			
			// Set initial focus to a text box.
			tableView.getFilter().getFilterBoxCountry().setFocus(true);
			
			// Change active button.
			switchToTableViewButton.setEnabled(false);
			switchToMapViewButton.setEnabled(true);
		}
		
		private void switchToMapView() {
			RootPanel.get("dataList").remove(tableView.getPanel());
			addMapToPanel();
			
			// TODO Add MapView to RootPanel once implemented.
			
			// Change active button.
			switchToMapViewButton.setEnabled(false);
			switchToTableViewButton.setEnabled(true);
		}
		
		private void loadMapApi() {
			
			//loads map api
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
		        draw();
		      }
		    };

		    LoadApi.go(onLoad, loadLibraries, sensor);
		}
		
		private void addMapToPanel() {
			
			//ads map to root panel
			RootPanel.get("dataList").add(mapView.getPanel());
		}
		
		private void draw() {
			
			//initializes map
		    mapView = new MapView();
		    addMapToPanel();
		}
}