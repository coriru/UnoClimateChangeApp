package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class Filter {
	
	private ListBox filterBoxMonth = new ListBox();
	private TextBox filterBoxYear1 = new TextBox();
	private TextBox filterBoxYear2 = new TextBox();
	private TextBox filterBoxCountry = new TextBox();
	private TextBox filterBoxCity = new TextBox();
	private TextBox filterBoxMinTemperature = new TextBox();
	private TextBox filterBoxMaxTemperature = new TextBox();
	private TextBox filterBoxMaxTemperatureUncertainty = new TextBox();
	
	private int month = -1;
	private int year1 = Integer.MAX_VALUE;
	private int year2 = Integer.MAX_VALUE;
	private String country = "";
	private String city = "";
	private float minTemperature = Float.MAX_VALUE;
	private float maxTemperature = Float.MAX_VALUE;
	private float maxTemperatureUncertainty = Float.MAX_VALUE;
	
	private Label labelMonth = new Label("Month:");
	private Label labelYear1 = new Label("Start Year:");
	private Label labelYear2 = new Label("End Year:");
	private Label labelCountry = new Label("Country:");
	private Label labelCity = new Label("City: ");
	private Label labelMinTemperature = new Label("Minimal Average Temperature:");
	private Label labelMaxTemperature = new Label("Maximum Average Temperature:");
	private Label labelMaxTemperatureUncertainty = new Label("Maximum Uncertainty:");
	
	private VerticalPanel filterPanel = new VerticalPanel();
	private HorizontalPanel row1 = new HorizontalPanel();
	private HorizontalPanel row2 = new HorizontalPanel();
	
	public Filter() {
		
		// Set drop-down option to choose month and add items.
		filterBoxMonth.setVisibleItemCount(1);
		filterBoxMonth.addItem("January");
		filterBoxMonth.addItem("February");
		filterBoxMonth.addItem("March");
		filterBoxMonth.addItem("April");
		filterBoxMonth.addItem("May");
		filterBoxMonth.addItem("June");
		filterBoxMonth.addItem("July");
		filterBoxMonth.addItem("August");
		filterBoxMonth.addItem("September");
		filterBoxMonth.addItem("October");
		filterBoxMonth.addItem("November");
		filterBoxMonth.addItem("December");
		
		// Add boxes to the panels.
		row1.add(labelMonth);
		row1.add(filterBoxMonth);
		
		row1.add(labelYear1);
		row1.add(filterBoxYear1);
		
		row1.add(labelYear2);
		row1.add(filterBoxYear2);
		
		row1.add(labelCountry);
		row1.add(filterBoxCountry);

		row1.add(labelCity);
		row1.add(filterBoxCity);
		
		row2.add(labelMinTemperature);
		row2.add(filterBoxMinTemperature);

		row2.add(labelMaxTemperature);
		row2.add(filterBoxMaxTemperature);
		
		row2.add(labelMaxTemperatureUncertainty);
		row2.add(filterBoxMaxTemperatureUncertainty);
		
		filterPanel.add(row1);
		filterPanel.add(row2);
		
	}
	
	public void setValues() {
		month = filterBoxMonth.getSelectedIndex() + 1;
		country = filterBoxCountry.getText();
		city = filterBoxCity.getText();
		
		if(!isEmpty(filterBoxYear1.getText())) {
			year1 = Integer.parseInt(filterBoxYear1.getText());;
		} else {
			year1 = Integer.MAX_VALUE;
		}
		
		if(!isEmpty(filterBoxYear2.getText())) {
			year2 = Integer.parseInt(filterBoxYear2.getText());;
		} else {
			year2 = Integer.MAX_VALUE;
		}
		
		if(!isEmpty(filterBoxMinTemperature.getText())) {
			minTemperature = Float.parseFloat(filterBoxMinTemperature.getText());
		} else {
			minTemperature = Float.MAX_VALUE;
		}
		
		if(!isEmpty(filterBoxMaxTemperature.getText())) {
			maxTemperature = Float.parseFloat(filterBoxMaxTemperature.getText());
		} else {
			maxTemperature = Float.MAX_VALUE;
		}
		
		if(!isEmpty(filterBoxMaxTemperatureUncertainty.getText())) {
			maxTemperatureUncertainty = Float.parseFloat(filterBoxMaxTemperatureUncertainty.getText());
		} else {
			maxTemperatureUncertainty = Float.MAX_VALUE;
		}
	}
	
	private boolean isEmpty(String s) {
		if(s.equals("")) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public Panel getPanel() {
		return filterPanel;
	}

	public int getMonth() {
		return month;
	}

	public int getYear1() {
		return year1;
	}

	public int getYear2() {
		return year2;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public float getMinTemperature() {
		return minTemperature;
	}

	public float getMaxTemperature() {
		return maxTemperature;
	}

	public float getMaxTemperatureUncertainty() {
		return maxTemperatureUncertainty;
	}	
}
