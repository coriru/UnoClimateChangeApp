package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ClimateChangeViewer implements EntryPoint {

	private TableView tableView = new TableView();
	private HorizontalPanel switchPanel = new HorizontalPanel();
	private Button switchToTableViewButton = new Button("Table");
	private Button switchToMapViewButton = new Button("Map");
	  
	  	/**
	  	 * Entry point method.
	  	 */
		public void onModuleLoad() {

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
			
			// Associate the panel tableView with the HTML host page.
			// This panel is considered as the start screen of the app.
			switchToTableView();
			
		}
	  
		private void switchToTableView() {
			RootPanel.get("dataList").add(tableView.getPanel());
			// TODO Remove first the MapView from RootPanel once implemented.
			
			// Set initial focus
			tableView.getFilter().getFilterBoxCountry().setFocus(true);
		}
		
		private void switchToMapView() {
			RootPanel.get("dataList").remove(tableView.getPanel());
			// TODO Add MapView to RootPanel once implemented.
		}
		

}
