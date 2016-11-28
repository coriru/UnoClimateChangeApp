package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.Window;

// TODO: Append "query" to every filter variable name.

public class MapFilter {
	
	public static final int COMPARISON_PERIOD_LENGTH = MapView.COMPARISON_PERIOD_LENGTH;
	public static final int OLDEST_YEAR_IN_DATAFILE = MapView.OLDEST_YEAR_IN_DATAFILE;
	public static final int YOUNGEST_YEAR_IN_DATAFILE = MapView.YOUNGEST_YEAR_IN_DATAFILE;
	
	private MapFilterEventHandler filterEventHandler;
	
	private TextBox filterBoxPeriod1 = new TextBox();
	private TextBox filterBoxPeriod2 = new TextBox();
	private TextBox filterBoxUncertainty = new TextBox();
	
	private int period1 = Integer.MIN_VALUE;
	private int period2 = Integer.MIN_VALUE;
	private float uncertainty = Float.MAX_VALUE;
	
	private Label labelPeriod1 = new Label("Starting Year Of First Period");
	private Label labelPeriod2 = new Label("Starting Year Of Second Period");
	private Label labelUncertainty = new Label("Maximum Avg. Uncertainty");
	
	private VerticalPanel filterPanel = new VerticalPanel();
	private HorizontalPanel row1 = new HorizontalPanel();
	
	public MapFilter(MapView mapView) {	
		// Adding styles to filter elements.
		row1.addStyleName("filterPanel1");
		labelUncertainty.addStyleName("filterLabel");
		labelPeriod1.addStyleName("filterLabel");
		labelPeriod2.addStyleName("filterLabel");
		
			
		// Create Handlers for popups.
		filterEventHandler = new MapFilterEventHandler(this, mapView);
		
		// Adding the filters to the panels.
		VerticalPanel uncertaintyPanel = new VerticalPanel();
		uncertaintyPanel.add(labelUncertainty);
		uncertaintyPanel.add(filterBoxUncertainty);
		row1.add(uncertaintyPanel);
		
		VerticalPanel yearPanel1 = new VerticalPanel();
		yearPanel1.add(labelPeriod1);;
		yearPanel1.add(filterBoxPeriod1);
		row1.add(yearPanel1);
		
		VerticalPanel yearPanel2 = new VerticalPanel();
		yearPanel2.add(labelPeriod2);
		yearPanel2.add(filterBoxPeriod2);
		row1.add(yearPanel2);
		
		// Adding the filter button to row1.
		row1.add(mapView.getFilterButton());
		
		filterPanel.add(row1);
	}
	
	public void setFilterValues() throws InvalidInputException {
		resetFilterValues();
		
		// Check input for uncertainty.
		String uncertaintyInput = filterBoxUncertainty.getText().trim(); 
		if(InputValidityChecker.checkUncertaintyString(uncertaintyInput)) {
			if(!InputValidityChecker.isEmpty(uncertaintyInput)) {
				uncertainty = Float.parseFloat(uncertaintyInput);
			} else {
				uncertainty = Float.MAX_VALUE;
			}
		} else {
			Window.alert("'" + uncertaintyInput + "' is not a valid uncertainty value.");
			throw new InvalidInputException();
		}	
		// Check input for period1.
		String period1Input = filterBoxPeriod1.getText().trim();
		if(InputValidityChecker.checkYearString(period1Input)) {
			if(!InputValidityChecker.isEmpty(period1Input)) {
				period1 = Integer.parseInt(period1Input);
			} else {
				Window.alert("Invalid filter request. Please choose the starting year of the 'first period'.");
				throw new InvalidInputException();
			}
		} else {
			Window.alert("'" + period1Input + "' is not a valid starting year of 'first period'.");
			throw new InvalidInputException();
		}
				
		// Check input for period2.
		String period2Input = filterBoxPeriod2.getText().trim();
		if(InputValidityChecker.checkYearString(period2Input)) {
			if(!InputValidityChecker.isEmpty(period2Input)) {
				period2 = Integer.parseInt(period2Input);
			} else {
				Window.alert("Invalid filter request. Please choose the starting year of the 'second period'.");
				throw new InvalidInputException();
			}
		} else {
			Window.alert("'" + period2Input + "' is not a valid starting year of 'second period'.");
			throw new InvalidInputException();
		}
				
		// Check if a valid time period is entered.
		if(period1 != Integer.MIN_VALUE && period2 != Integer.MIN_VALUE && period1 >= period2) {
			Window.alert("The entered 'Starting Year of First Period' is greater than or equal to the entered"
					+ "'Ending Year of Second Period'.");
			throw new InvalidInputException();
		}
		// Check if periods are overlapping.
		if(period1 != Integer.MIN_VALUE && period2 != Integer.MIN_VALUE && period1 + COMPARISON_PERIOD_LENGTH > period2) {
			Window.alert("The entered 'First Period' and 'Second Period' are overlapping.");
			throw new InvalidInputException();
		}
		// Check if entered periods ask for years that are not in the data file.
		if(period1 != Integer.MIN_VALUE && period1 < OLDEST_YEAR_IN_DATAFILE) {
			Window.alert("The data file cannot provide any data for the requested periods. Please use periods younger"
					+ " than or equal to " + OLDEST_YEAR_IN_DATAFILE + ".");
			throw new InvalidInputException();
		}
		if(period2 != Integer.MIN_VALUE && period2 > (YOUNGEST_YEAR_IN_DATAFILE - COMPARISON_PERIOD_LENGTH + 1)) {
			Window.alert("The data file cannot provide any data for the requested periods. Please use periods younger"
					+ " than or equal to " + (YOUNGEST_YEAR_IN_DATAFILE - COMPARISON_PERIOD_LENGTH + 1) + ".");
			throw new InvalidInputException();
		}		
	}
	
	private void resetFilterValues() {
		uncertainty = Float.MAX_VALUE;
		period1 = Integer.MIN_VALUE;
		period2 = Integer.MIN_VALUE;
	}
	
	public Panel getPanel() {
		return filterPanel;
	}
	
	public int getPeriod1() {
		return period1;
	}
	
	public int getPeriod2() {
		return period2;
	}
	
	public float getUncertainty() {
		return uncertainty;
	}
	
	public TextBox getFilterBoxPeriod1() {
		return filterBoxPeriod1;
	}
	
	public TextBox getFilterBoxPeriod2() {
		return filterBoxPeriod2;
	}
	
	public TextBox getFilterBoxUncertainty() {
		return filterBoxUncertainty;
	}
	
	public Label getLabelPeriod1() {
		return labelPeriod1;
	}
	
	public Label getLabelPeriod2() {
		return labelPeriod2;
	}
	
	public Label getLabelUncertainty() {
		return labelUncertainty;
	}
	
}
