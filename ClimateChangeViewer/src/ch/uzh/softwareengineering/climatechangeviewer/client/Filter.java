package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Filter {
	
	private FilterEventHandler filterEventHandler;
	
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
	private Label labelMinTemperature = new Label("Minimal Avg. Temperature:");
	private Label labelMaxTemperature = new Label("Maximum Avg. Temperature:");
	private Label labelMaxTemperatureUncertainty = new Label("Maximum Avg. Uncertainty:");
	
	private VerticalPanel filterPanel = new VerticalPanel();
	private HorizontalPanel row1 = new HorizontalPanel();
	private HorizontalPanel row2 = new HorizontalPanel();
	
	public Filter(TableView tableView) {	
		// Adding styles to filter elements.
		row1.addStyleName("filterPanel1");
		row2.addStyleName("filterPanel2");
		labelYear1.addStyleName("filterLabel");
		labelYear2.addStyleName("filterLabel");
		labelMonth.addStyleName("filterLabel");
		labelCountry.addStyleName("filterLabel");
		labelCity.addStyleName("filterLabel");
		labelMinTemperature.addStyleName("filterLabel");
		labelMaxTemperature.addStyleName("filterLabel");
		labelMaxTemperatureUncertainty.addStyleName("filterLabel");
		
		// Set drop-down option to choose month and add items.
		filterBoxMonth.setVisibleItemCount(1);
		filterBoxMonth.addItem("All Months");
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
			
		// Create Handlers for popups.
		filterEventHandler = new FilterEventHandler(this, tableView);
		
		// Adding the filters to the panels.
		VerticalPanel countryPanel = new VerticalPanel();
		countryPanel.add(labelCountry);
		countryPanel.add(filterBoxCountry);
		row1.add(countryPanel);

		VerticalPanel cityPanel = new VerticalPanel();
		cityPanel.add(labelCity);
		cityPanel.add(filterBoxCity);
		row1.add(cityPanel);
	
		VerticalPanel yearPanel1 = new VerticalPanel();
		yearPanel1.add(labelYear1);
		yearPanel1.add(filterBoxYear1);
		row1.add(yearPanel1);
		
		VerticalPanel yearPanel2 = new VerticalPanel();
		yearPanel2.add(labelYear2);
		yearPanel2.add(filterBoxYear2);
		row1.add(yearPanel2);
		
		VerticalPanel monthPanel = new VerticalPanel();
		monthPanel.add(labelMonth);
		monthPanel.add(filterBoxMonth);
		row1.add(monthPanel);
		
		VerticalPanel minTemperaturePanel = new VerticalPanel();
		minTemperaturePanel.add(labelMinTemperature);
		minTemperaturePanel.add(filterBoxMinTemperature);
		row2.add(minTemperaturePanel);

		VerticalPanel maxTemperaturePanel = new VerticalPanel();
		maxTemperaturePanel.add(labelMaxTemperature);
		maxTemperaturePanel.add(filterBoxMaxTemperature);
		row2.add(maxTemperaturePanel);
		
		VerticalPanel maxTemperatureUncertaintyPanel = new VerticalPanel();
		maxTemperatureUncertaintyPanel.add(labelMaxTemperatureUncertainty);
		maxTemperatureUncertaintyPanel.add(filterBoxMaxTemperatureUncertainty);
		row2.add(maxTemperatureUncertaintyPanel);
		
		// Adding the filter button to row2.
		row2.add(tableView.getFilterButton());
		
		filterPanel.add(row1);
		filterPanel.add(row2);	
	}
	
	public void setValues() throws InvalidCharacterException {
		month = filterBoxMonth.getSelectedIndex();
		
		// Check input validity.
		if(checkNameString(filterBoxCountry.getText().toUpperCase().trim())) {
			country = filterBoxCountry.getText();
		} else {
			Window.alert("'" + filterBoxCountry.getText() + "' is not a valid name.");
			throw new InvalidCharacterException();
		}
		
		if(checkNameString(filterBoxCity.getText().toUpperCase().trim())) {
			city = filterBoxCity.getText();
		} else {
			Window.alert("'" + filterBoxCountry.getText() + "' is not a valid name.");
			throw new InvalidCharacterException();
		}
		
		if(checkYearString(filterBoxYear1.getText().toUpperCase().trim())) {
			if(!isEmpty(filterBoxYear1.getText())) {
				year1 = Integer.parseInt(filterBoxYear1.getText());
			} else {
				year1 = Integer.MAX_VALUE;
			}
		} else {
			Window.alert("'" + filterBoxYear1.getText() + "' is not a valid year.");
			throw new InvalidCharacterException();
		}
		
		if(checkYearString(filterBoxYear2.getText().toUpperCase().trim())) {
			if(!isEmpty(filterBoxYear2.getText())) {
				year2 = Integer.parseInt(filterBoxYear2.getText());
			} else {
				year2 = Integer.MAX_VALUE;
			}
		} else {
			Window.alert("'" + filterBoxYear2.getText() + "' is not a valid year.");
			throw new InvalidCharacterException();
		}
		
		if(checkTemperatureString(filterBoxMinTemperature.getText().toUpperCase().trim())) {
			if(!isEmpty(filterBoxMinTemperature.getText())) {
				minTemperature = Float.parseFloat(filterBoxMinTemperature.getText());
			} else {
				minTemperature = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + filterBoxMinTemperature.getText() + "' is not a valid temperature.");
			throw new InvalidCharacterException();
		}
		
		if(checkTemperatureString(filterBoxMaxTemperature.getText().toUpperCase().trim())) {
			if(!isEmpty(filterBoxMaxTemperature.getText())) {
				maxTemperature = Float.parseFloat(filterBoxMaxTemperature.getText());
			} else {
				maxTemperature = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + filterBoxMaxTemperature.getText() + "' is not a valid temperature.");
			throw new InvalidCharacterException();
		}

		if(checkUncertaintyString(filterBoxMaxTemperatureUncertainty.getText().toUpperCase().trim())) {
			if(!isEmpty(filterBoxMaxTemperatureUncertainty.getText())) {
				maxTemperatureUncertainty = Float.parseFloat(filterBoxMaxTemperatureUncertainty.getText());
			} else {
				maxTemperatureUncertainty = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + filterBoxMaxTemperatureUncertainty.getText() + "' is not a valid temperature.");
			throw new InvalidCharacterException();
		}
	}
	
	public boolean checkNameString(String s) {
		if(s == null) {
			return true;
		} else if(s.toUpperCase().trim().matches("^[A-Z ÄÖÜÉÈÀÃ]{0,30}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkYearString(String s) {
		if(s == null) {
			return true;
		} else if (s.toUpperCase().trim().matches("^[0-9]{0,4}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkTemperatureString(String s) {
		if (s == null) {
			return true;
		} else if (s.toUpperCase().trim().matches("^([-]{0,1}[0-9]{0,2}[.]{1}[0-9]{0,3})|([-]{0,1}[0-9]{0,2})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkUncertaintyString(String s) {
		if (s == null) {
			return true;
		} else if (s.toUpperCase().trim().matches("^([0-9]{0,2}[.]{1}[0-9]{0,3})|([0-9]{0,2})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmpty(String s) {
		if(s == null) {
			return true;
		} else if(s.equals("")) {
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
	
	public TextBox getFilterBoxCountry() {
		return filterBoxCountry;
	}

	public ListBox getFilterBoxMonth() {
		return filterBoxMonth;
	}

	public TextBox getFilterBoxYear1() {
		return filterBoxYear1;
	}

	public TextBox getFilterBoxYear2() {
		return filterBoxYear2;
	}

	public TextBox getFilterBoxCity() {
		return filterBoxCity;
	}

	public TextBox getFilterBoxMinTemperature() {
		return filterBoxMinTemperature;
	}

	public TextBox getFilterBoxMaxTemperature() {
		return filterBoxMaxTemperature;
	}

	public TextBox getFilterBoxMaxTemperatureUncertainty() {
		return filterBoxMaxTemperatureUncertainty;
	}

	public Label getLabelMonth() {
		return labelMonth;
	}

	public Label getLabelYear1() {
		return labelYear1;
	}

	public Label getLabelYear2() {
		return labelYear2;
	}

	public Label getLabelCountry() {
		return labelCountry;
	}

	public Label getLabelCity() {
		return labelCity;
	}

	public Label getLabelMinTemperature() {
		return labelMinTemperature;
	}

	public Label getLabelMaxTemperature() {
		return labelMaxTemperature;
	}

	public Label getLabelMaxTemperatureUncertainty() {
		return labelMaxTemperatureUncertainty;
	}
}
