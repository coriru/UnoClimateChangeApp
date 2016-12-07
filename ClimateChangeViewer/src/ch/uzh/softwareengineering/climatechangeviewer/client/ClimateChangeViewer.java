package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class ClimateChangeViewer implements EntryPoint {
	
	private AppView appView;

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		appView = new AppView();

		// Associate the switch panel with the HTML host page.
		Window.setMargin("0px");
		RootPanel.get().add(appView);
		
		// Set focus to the first textbox in the default view of the application. The following line should only be
		// executed once appView was added to the RootPanel.
		//appView.getDefaultView().getFilter().getPeriod1QueryInputBox().setFocus(true);
	}

}