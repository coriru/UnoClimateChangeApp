package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppView extends Composite {
	
	interface AppViewUiBinder extends UiBinder<Widget, AppView> {}
	
	private static AppViewUiBinder uiBinder = GWT.create(AppViewUiBinder.class);
	
	private AboutView aboutView = new AboutView();
	private TableView tableView = new TableView();
	private MapView mapView = new MapView();
	private SourceView sourceView = new SourceView();
	
	@UiField FlowPanel centerPanel;
	
	@UiField Button aboutViewButton;
	@UiField Button mapViewButton;
	@UiField Button tableViewButton;
	@UiField Button sourceViewButton;	
	
	public AppView() {	
		initWidget(uiBinder.createAndBindUi(this));
		
		// Set map as the default view.
		mapViewButton.setEnabled(false);
		centerPanel.add(mapView);		
	}
	
	@UiHandler("aboutViewButton")
	void handleAboutViewClick(ClickEvent e) {
		aboutViewButton.setEnabled(false);
		mapViewButton.setEnabled(true);
		tableViewButton.setEnabled(true);
		sourceViewButton.setEnabled(true);
		
		centerPanel.clear();
		centerPanel.add(aboutView);
	}
	
	@UiHandler("mapViewButton")
	void handleMapViewClick(ClickEvent e) {
		aboutViewButton.setEnabled(true);
		mapViewButton.setEnabled(false);
		tableViewButton.setEnabled(true);
		sourceViewButton.setEnabled(true);
		
		centerPanel.clear();
		centerPanel.add(mapView);
		mapView.getFilter().getPeriod1QueryInputBox().setFocus(true);
	}
	
	@UiHandler("tableViewButton")
	void handleTableViewClick(ClickEvent e) {
		aboutViewButton.setEnabled(true);
		mapViewButton.setEnabled(true);
		tableViewButton.setEnabled(false);
		sourceViewButton.setEnabled(true);
		
		centerPanel.clear();
		centerPanel.add(tableView);
		tableView.getFilter().getCityQueryInputBox().setFocus(true);
	}
	
	@UiHandler("sourceViewButton")
	void handleSourceViewClick(ClickEvent e) {
		aboutViewButton.setEnabled(true);
		mapViewButton.setEnabled(true);
		tableViewButton.setEnabled(true);
		sourceViewButton.setEnabled(false);
		
		centerPanel.clear();
		centerPanel.add(sourceView);		
	}

	public MapView getDefaultView() {
		return mapView;
	}

}
