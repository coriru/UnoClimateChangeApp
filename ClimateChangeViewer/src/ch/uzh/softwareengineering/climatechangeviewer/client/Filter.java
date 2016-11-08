package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class Filter {
	
	private TextBox filterBox = new TextBox();
	private String value;
	private Label label = new Label("Datum-Filter");
	private HorizontalPanel filterPanel = new HorizontalPanel();
	
	public TextBox getTextBox() {
		return filterBox;		
	}
	
	public HorizontalPanel getPanel() {
		filterPanel.add(filterBox);
		filterPanel.add(label);
		return filterPanel;
	}
	public void setValue(String s) {
		value = s;
	}
	
	public String getValue() {
		return value;
	}
	

}
