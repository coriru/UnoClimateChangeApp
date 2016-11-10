package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class Filter {
	
	private TextBox filterBoxDate = new TextBox();
	TextBox filterBoxCountry = new TextBox();
	TextBox filterBoxCity = new TextBox();
	
	private String valueDate;
	String valueCountry;
	String valueCity;
	
	private Label labelDate = new Label("Datum-Filter");
	Label labelCountry = new Label("Land-Filter");
	Label labelCity = new Label("Stadt-Filter");
	
	private HorizontalPanel filterPanel = new HorizontalPanel();
	
	public TextBox getTextBoxDate() {
		return filterBoxDate;		
	}
	
	public TextBox getTextBoxCountry() {
		return filterBoxCountry;		
	}
	
	public TextBox getTextBoxCity() {
		return filterBoxCity;		
	}
	
	public HorizontalPanel getPanel() {
		filterPanel.add(filterBoxDate);
		filterPanel.add(labelDate);
		filterPanel.add(filterBoxCountry);
		filterPanel.add(labelCountry);
		filterPanel.add(filterBoxCity);
		filterPanel.add(labelCity);
		return filterPanel;
	}
	
	public void setValue(String date, String country, String city) {
		valueDate = date;
		valueCountry = country;
		valueCity = city;
	}
	
	public String getValueDate() {
		return valueDate;
	}
	
	public String getValueCountry() {
		return valueCountry;
	}
	
	public String getValueCity() {
		return valueCity;
	}
	
}
