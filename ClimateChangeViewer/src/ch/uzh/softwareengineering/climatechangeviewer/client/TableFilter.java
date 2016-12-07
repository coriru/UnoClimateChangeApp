package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.text.ParseException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TableFilter extends Composite {
	
	interface TableFilterUiBinder extends UiBinder<Widget, TableFilter> {}
	
	private static TableFilterUiBinder uiBinder = GWT.create(TableFilterUiBinder.class);
	
	private String cityQuery = "";
	private String countryQuery = "";
	private int monthQuery = 0;
	private int year1Query = Integer.MIN_VALUE;
	private int year2Query = Integer.MIN_VALUE;
	private double minTemperatureQuery = Double.MAX_VALUE;
	private double maxTemperatureQuery = Double.MAX_VALUE;
	private double uncertaintyQuery = Double.MAX_VALUE;
			
	private TableView tableView;
	
	@UiField TextBox cityQueryInputBox;
	@UiField TextBox countryQueryInputBox;
	@UiField IntegerBox year1QueryInputBox;
	@UiField IntegerBox year2QueryInputBox;
	@UiField ListBox monthQueryInputBox;
	@UiField DoubleBox minTemperatureQueryInputBox;
	@UiField DoubleBox maxTemperatureQueryInputBox;
	@UiField DoubleBox uncertaintyQueryInputBox;
	
	@UiField Label cityQueryLabel;
	@UiField Label countryQueryLabel;
	@UiField Label year1QueryLabel;
	@UiField Label year2QueryLabel;
	@UiField Label monthQueryLabel;
	@UiField Label minTemperatureQueryLabel;
	@UiField Label maxTemperatureQueryLabel;
	@UiField Label uncertaintyQueryLabel;
	
	@UiField Button filterButton;
	
	@UiHandler("filterButton")
	void handleFilterClick(ClickEvent e) {
		tableView.filterData();
	}
	
	
	public TableFilter(TableView tableView) {
		this.tableView = tableView;
		initWidget(uiBinder.createAndBindUi(this));
		initMonthQueryInputBox();
		
		// Create EventHandler for filtering with enter key and tool tips.
		@SuppressWarnings("unused")
		TableFilterEventHandler eventHandler = new TableFilterEventHandler(this, tableView);
	}
	
	private void initMonthQueryInputBox() {
		// Set drop-down option to choose monthQuery and add items.
		monthQueryInputBox.setVisibleItemCount(1);
		monthQueryInputBox.addItem("All Months");
		monthQueryInputBox.addItem("January");
		monthQueryInputBox.addItem("February");
		monthQueryInputBox.addItem("March");
		monthQueryInputBox.addItem("April");
		monthQueryInputBox.addItem("May");
		monthQueryInputBox.addItem("June");
		monthQueryInputBox.addItem("July");
		monthQueryInputBox.addItem("August");
		monthQueryInputBox.addItem("September");
		monthQueryInputBox.addItem("October");
		monthQueryInputBox.addItem("November");
		monthQueryInputBox.addItem("December");
	}
	
	public void setFilterValues() throws InvalidInputException {
		resetFilterValues();
		
		// Input from drop-down menu doesn't need to be checked.
		monthQuery = monthQueryInputBox.getSelectedIndex();
		
		// Check input for cityQuery name.
		String cityQueryInput = cityQueryInputBox.getText().trim();
		if(InputValidityChecker.checkNameString(cityQueryInput)) {
			if(!InputValidityChecker.isEmpty(cityQueryInput)) {
				cityQuery = cityQueryInput;
			} else {
				cityQuery = "";
			}
		} else {
			Window.alert("'" + cityQueryInput + "' is not a valid city name.");
			throw new InvalidInputException();
		}
	
		// Check input for countryQuery name.
		String countryQueryInput = countryQueryInputBox.getText().trim();
		if(InputValidityChecker.checkNameString(countryQueryInput)) {
			if(!InputValidityChecker.isEmpty(countryQueryInput)) {
				countryQuery = countryQueryInput;
			} else {
				countryQuery = "";
			}
		} else {
			Window.alert("'" + countryQueryInput + "' is not a valid country name.");
			throw new InvalidInputException();
		}
		
		// Check input for period1Query.
		String year1QueryInputString = year1QueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(year1QueryInputString)) {
			try {
				year1Query = year1QueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + year1QueryInputString + "' is not a valid year.");
				throw new InvalidInputException();
			}
		} else {
			year1Query = Integer.MIN_VALUE;
		}
		
		// Check input for period2Query.
		String year2QueryInputString = year2QueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(year2QueryInputString)) {
			try {
				year2Query = year2QueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + year2QueryInputString + "' is not a valid year.");
				throw new InvalidInputException();
			}
		} else {
			year2Query = Integer.MIN_VALUE;
		}
		
		// Check if a valid time period is entered.
		if(year1Query != Integer.MIN_VALUE && year2Query != Integer.MIN_VALUE && year1Query > year2Query) {
			Window.alert("The entered 'First Year' is greater than the entered 'Last Year'");
			throw new InvalidInputException();
		}
		
		// Check input for minTemperatureQuery.
		String minTemperatureQueryInputString = minTemperatureQueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(minTemperatureQueryInputString)) {
			try {
				minTemperatureQuery = minTemperatureQueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + minTemperatureQueryInputString + "' is not a valid temperature.");
				throw new InvalidInputException();
			}
		} else {
			minTemperatureQuery = Double.MAX_VALUE;
		}
		
		// Check input for minTemperatureQuery.
		String maxTemperatureQueryInputString = maxTemperatureQueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(maxTemperatureQueryInputString)) {
			try {
				maxTemperatureQuery = maxTemperatureQueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + maxTemperatureQueryInputString + "' is not a valid temperature.");
				throw new InvalidInputException();
			}
		} else {
			maxTemperatureQuery = Double.MAX_VALUE;
		}

		// Check if a valid temperature range is entered.
		if(minTemperatureQuery < Double.MAX_VALUE && maxTemperatureQuery < Double.MAX_VALUE && minTemperatureQuery > maxTemperatureQuery) {
			Window.alert("The entered 'Minimum Temperature' is greater than the entered 'Maximum Temperature'.");
			throw new InvalidInputException();
		}
		
		// Check input for uncertaintyQuery.
		String uncertaintyQueryInputString = uncertaintyQueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(uncertaintyQueryInputString)) {
			try {
				uncertaintyQuery = uncertaintyQueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + uncertaintyQueryInputString + "' is not a valid uncertainty value.");
				throw new InvalidInputException();
			}
		} else {
			uncertaintyQuery = Double.MAX_VALUE;
		}
	}
	
	private void resetFilterValues() {
		year1Query = Integer.MIN_VALUE;
		year2Query = Integer.MIN_VALUE;
		monthQuery = 0;
		countryQuery = "";
		cityQuery = "";
		minTemperatureQuery = Double.MAX_VALUE;
		maxTemperatureQuery = Double.MAX_VALUE;
		uncertaintyQuery = Double.MAX_VALUE;
	}

	public TextBox getCityQueryInputBox() {
		return cityQueryInputBox;
	}

	public TextBox getCountryQueryInputBox() {
		return countryQueryInputBox;
	}


	public IntegerBox getYear1QueryInputBox() {
		return year1QueryInputBox;
	}

	public IntegerBox getYear2QueryInputBox() {
		return year2QueryInputBox;
	}

	public ListBox getMonthQueryInputBox() {
		return monthQueryInputBox;
	}

	public DoubleBox getMinTemperatureQueryInputBox() {
		return minTemperatureQueryInputBox;
	}

	public DoubleBox getMaxTemperatureQueryInputBox() {
		return maxTemperatureQueryInputBox;
	}

	public DoubleBox getUncertaintyQueryInputBox() {
		return uncertaintyQueryInputBox;
	}

	public Label getCityQueryLabel() {
		return cityQueryLabel;
	}

	public Label getCountryQueryLabel() {
		return countryQueryLabel;
	}

	public Label getYear1QueryLabel() {
		return year1QueryLabel;
	}

	public Label getYear2QueryLabel() {
		return year2QueryLabel;
	}

	public Label getMonthQueryLabel() {
		return monthQueryLabel;
	}

	public Label getMinTemperatureQueryLabel() {
		return minTemperatureQueryLabel;
	}

	public Label getMaxTemperatureQueryLabel() {
		return maxTemperatureQueryLabel;
	}

	public Label getUncertaintyQueryLabel() {
		return uncertaintyQueryLabel;
	}

	public String getCityQuery() {
		return cityQuery;
	}

	public String getCountryQuery() {
		return countryQuery;
	}

	public int getMonthQuery() {
		return monthQuery;
	}


	public int getYear1Query() {
		return year1Query;
	}


	public int getYear2Query() {
		return year2Query;
	}

	public double getMinTemperatureQuery() {
		return minTemperatureQuery;
	}

	public double getMaxTemperatureQuery() {
		return maxTemperatureQuery;
	}

	public double getUncertaintyQuery() {
		return uncertaintyQuery;
	}

}
