package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class FilterPopup extends PopupPanel {

	//TODO Add custom style to the filter popups.
	
	public FilterPopup(String s) {
		// PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		// If this is set, the panel closes itself automatically when the user
		// clicks outside of it.
		super(true);
		
		// PopupPanel is a SimplePanel, so you have to set it's widget property to
	    // whatever you want its contents to be.
	    setWidget(new Label(s));
	    setHeight("100px");
	    setWidth("150px");
	}
}
