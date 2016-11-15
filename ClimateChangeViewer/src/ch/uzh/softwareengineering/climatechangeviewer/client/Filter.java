package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.Window;

public class Filter {
	
	private FilterEventHandler filterEventHandler;
	private InputValidityChecker inputValidityChecker = new InputValidityChecker();
	
	private ListBox filterBoxMonth = new ListBox();
	private TextBox filterBoxYear1 = new TextBox();
	private TextBox filterBoxYear2 = new TextBox();
	private TextBox filterBoxCountry = new TextBox();
	private TextBox filterBoxCity = new TextBox();
	private TextBox filterBoxMinTemperature = new TextBox();
	private TextBox filterBoxMaxTemperature = new TextBox();
	private TextBox filterBoxUncertainty = new TextBox();
	
	private int month = 0;
	private int year1 = Integer.MAX_VALUE;
	private int year2 = Integer.MAX_VALUE;
	private String country = "";
	private String city = "";
	private float minTemperature = Float.MAX_VALUE;
	private float maxTemperature = Float.MAX_VALUE;
	private float uncertainty = Float.MAX_VALUE;
	
	private Label labelMonth = new Label("Month");
	private Label labelYear1 = new Label("Starting Year");
	private Label labelYear2 = new Label("Ending Year");
	private Label labelCountry = new Label("Country");
	private Label labelCity = new Label("City");
	private Label labelMinTemperature = new Label("Minimal Avg. Temperature");
	private Label labelMaxTemperature = new Label("Maximum Avg. Temperature");
	private Label labelUncertainty = new Label("Maximum Avg. Uncertainty");
	
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
		labelUncertainty.addStyleName("filterLabel");
		
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
		
		VerticalPanel uncertaintyPanel = new VerticalPanel();
		uncertaintyPanel.add(labelUncertainty);
		uncertaintyPanel.add(filterBoxUncertainty);
		row2.add(uncertaintyPanel);
		
		// Adding the filter button to row2.
		row2.add(tableView.getFilterButton());
		
		filterPanel.add(row1);
		filterPanel.add(row2);	
	}
	
	public void setFilterValues() throws InvalidInputException {
		resetFilterValues();
		
		// Input from drop-down menu doesn't need to be checked.
		month = filterBoxMonth.getSelectedIndex();
		
		// Check input for city name.
		String cityInput = filterBoxCity.getText().trim();
		if(inputValidityChecker.checkNameString(cityInput)) {
			if(!inputValidityChecker.isEmpty(cityInput)) {
				city = cityInput;
			} else {
				city = "";
			}
		} else {
			Window.alert("'" + cityInput + "' is not a valid city name.");
			throw new InvalidInputException();
		}
	
		// Check input for country name.
		String countryInput = filterBoxCountry.getText().trim();
		if(inputValidityChecker.checkNameString(countryInput)) {
			if(!inputValidityChecker.isEmpty(countryInput)) {
				country = countryInput;
			} else {
				country = "";
			}
		} else {
			Window.alert("'" + countryInput + "' is not a valid country name.");
			throw new InvalidInputException();
		}
		
		// Check input for year1.
		String year1Input = filterBoxYear1.getText().trim();
		if(inputValidityChecker.checkYearString(year1Input)) {
			if(!inputValidityChecker.isEmpty(year1Input)) {
				year1 = Integer.parseInt(year1Input);
			} else {
				year1 = Integer.MAX_VALUE;
			}
		} else {
			Window.alert("'" + year1Input + "' is not a valid year.");
			throw new InvalidInputException();
		}
		
		// Check input for year2.
		String year2Input = filterBoxYear2.getText().trim();
		if(inputValidityChecker.checkYearString(year2Input)) {
			if(!inputValidityChecker.isEmpty(year2Input)) {
				year2 = Integer.parseInt(year2Input);
			} else {
				year2 = Integer.MAX_VALUE;
			}
		} else {
			Window.alert("'" + year2Input + "' is not a valid year2.");
			throw new InvalidInputException();
		}
		
		// Check if a valid time period is entered.
		if(year1 != Integer.MAX_VALUE && year2 != Integer.MAX_VALUE && year1 > year2) {
			Window.alert("The entered 'Starting Year' is greater than the entered 'Ending Year'");
			throw new InvalidInputException();
		}
		
		// Check input for minTemperature.
		String minTemperatureInput = filterBoxMinTemperature.getText().trim();
		if(inputValidityChecker.checkTemperatureString(minTemperatureInput)) {
			if(!inputValidityChecker.isEmpty(minTemperatureInput)) {
				minTemperature = Float.parseFloat(minTemperatureInput);
			} else {
				minTemperature = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + minTemperatureInput + "' is not a valid temperature.");
			throw new InvalidInputException();
		}
		
		// Check input for maxTemperature.
		String maxTemperatureInput = filterBoxMaxTemperature.getText().trim();
		if(inputValidityChecker.checkTemperatureString(maxTemperatureInput)) {
			if(!inputValidityChecker.isEmpty(maxTemperatureInput)) {
				maxTemperature = Float.parseFloat(maxTemperatureInput);
			} else {
				maxTemperature = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + maxTemperatureInput + "' is not a valid temperature.");
			throw new InvalidInputException();
		}
		
		// Check if a valid temperature range is entered.
		if(minTemperature < Float.MAX_VALUE && maxTemperature < Float.MAX_VALUE && minTemperature > maxTemperature) {
			Window.alert("The entered 'Minimal Avg. Temperature' is greater than the entered 'Minimal Avg. Temperature'.");
			throw new InvalidInputException();
		}

		// Check input for uncertainty.
		String uncertaintyInput = filterBoxUncertainty.getText().trim(); 
		if(inputValidityChecker.checkUncertaintyString(uncertaintyInput)) {
			if(!inputValidityChecker.isEmpty(uncertaintyInput)) {
				uncertainty = Float.parseFloat(uncertaintyInput);
			} else {
				uncertainty = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + uncertaintyInput + "' is not a valid uncertainty value.");
			throw new InvalidInputException();
		}	
	}
	
	private void resetFilterValues() {
		month = 0;
		year1 = Integer.MAX_VALUE;
		year2 = Integer.MAX_VALUE;
		country = "";
		city = "";
		minTemperature = Float.MAX_VALUE;
		maxTemperature = Float.MAX_VALUE;
		uncertainty = Float.MAX_VALUE;
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

	public float getUncertainty() {
		return uncertainty;
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

	public TextBox getFilterBoxUncertainty() {
		return filterBoxUncertainty;
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

	public Label getLabelUncertainty() {
		return labelUncertainty;
	}
}
