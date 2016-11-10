package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class Filter {
	
	private TextBox filterBoxDate = new TextBox();
	private TextBox filterBoxCountry = new TextBox();
	private TextBox filterBoxCity = new TextBox();
	private TextBox filterBoxTemp = new TextBox();
	private TextBox filterBoxTempUnc = new TextBox();
	
	private String valueDate;
	private String valueCountry;
	private String valueCity;
	private float valueTemp = (float) 0.0;
	private float valueTempUnc = (float) 0.0;
	
	private Label labelDate = new Label("Datum-Filter");
	private Label labelCountry = new Label("Land-Filter");
	private Label labelCity = new Label("Stadt-Filter");
	private Label labelTemp = new Label("Temperatur-Filter");
	private Label labelTempUnc = new Label("Messungenauigkeit-Filter");
	
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
	
	public TextBox getTextBoxTemp() {
		return filterBoxTemp;		
	}
	
	public TextBox getTextBoxTempUnc() {
		return filterBoxTempUnc;		
	}
	
	public HorizontalPanel getPanel() {
		filterPanel.add(filterBoxDate);
		filterPanel.add(labelDate);
		
		filterPanel.add(filterBoxCountry);
		filterPanel.add(labelCountry);
		
		filterPanel.add(filterBoxCity);
		filterPanel.add(labelCity);
		
		filterPanel.add(filterBoxTemp);
		filterPanel.add(labelTemp);
		
		filterPanel.add(filterBoxTempUnc);
		filterPanel.add(labelTempUnc);
		
		return filterPanel;
	}
	
//	public void setValue(String date, String country, String city, float temp, float tempUnc) {
//		valueDate = date;
//		valueCountry = country;
//		valueCity = city;
//		valueTemp = temp;
//		valueTempUnc = tempUnc;
//	}
	
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
	
	public float getValueTemp() {
		return valueTemp;
	}
	
	public float getValueTempUnc() {
		return valueTempUnc;
	}
	
}
