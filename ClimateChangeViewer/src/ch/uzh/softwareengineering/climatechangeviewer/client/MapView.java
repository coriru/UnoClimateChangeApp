package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MapView extends View {

	private VerticalPanel mainPanel = new VerticalPanel();
	private MapInitializer mapInitializer;
	
	
	public MapView() {
		mapInitializer = new MapInitializer();
		mainPanel.add(mapInitializer.getMapPanel());
	}
	
	public Panel getPanel() {
		return mainPanel;
	}
}
