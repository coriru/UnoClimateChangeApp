package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ClimateChangeViewer implements EntryPoint {
	
	private TableView tableView = new TableView();
	private MapView mapView = new MapView();
	private HorizontalPanel switchPanel = new HorizontalPanel();
	private Button switchToTableViewButton = new Button("Table");
	private Button switchToMapViewButton = new Button("Map");
	private boolean isMapView;
	  
	  	/**
	  	 * Entry point method.
	  	 */
		public void onModuleLoad() {
			switchPanel.add(switchToMapViewButton);
			switchPanel.add(switchToTableViewButton);
			
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
			
			// MapView is shown by default.
			isMapView = true;
			switchToMapView();
			
		}
		
		private void switchToTableView() {
			// Remove mapView first if necessary.
			if(isMapView) {
				RootPanel.get("dataList").remove(mapView.getPanel());
				isMapView = false;
			}
			RootPanel.get("dataList").add(tableView.getPanel());

			// Set initial focus to a text box.
			tableView.getFilter().getFilterBoxCountry().setFocus(true);
			
			// Change active button.
			switchToTableViewButton.setEnabled(false);
			switchToMapViewButton.setEnabled(true);
		}
		
		private void switchToMapView() {
			// Remove tableView first if necessary.
			if(!isMapView) {
				RootPanel.get("dataList").remove(tableView.getPanel());
				isMapView = true;
			}
			RootPanel.get("dataList").add(mapView.getPanel());

			// Change active button.
			switchToMapViewButton.setEnabled(false);
			switchToTableViewButton.setEnabled(true);
		}
	
}