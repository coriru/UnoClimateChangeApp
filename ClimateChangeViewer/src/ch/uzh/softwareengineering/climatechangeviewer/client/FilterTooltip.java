package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class FilterTooltip extends PopupPanel {
	
	public FilterTooltip(String s) {
		super(false);
		
		setAnimationEnabled(false);
		setStyleName("tooltip");
		
	    HTMLPanel tooltip = new HTMLPanel(s);
	    tooltip.setWidth("180");
	    tooltip.setHeight("50");
	    setWidget(tooltip);	    
	}
	
}
