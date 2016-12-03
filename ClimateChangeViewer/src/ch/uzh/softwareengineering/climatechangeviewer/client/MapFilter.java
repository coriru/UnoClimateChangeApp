package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;

import java.text.ParseException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;

public class MapFilter extends Composite {
	
	interface MapFilterUiBinder extends UiBinder<Widget, MapFilter> {}
	private static MapFilterUiBinder uiBinder = GWT.create(MapFilterUiBinder.class);
	
	public static final int COMPARISON_PERIOD_LENGTH = MapView.COMPARISON_PERIOD_LENGTH;
	public static final int OLDEST_YEAR_IN_DATAFILE = MapView.OLDEST_YEAR_IN_DATAFILE;
	public static final int YOUNGEST_YEAR_IN_DATAFILE = MapView.YOUNGEST_YEAR_IN_DATAFILE;
	
	private int period1Query = Integer.MIN_VALUE;
	private int period2Query = Integer.MIN_VALUE;
	private double uncertaintyQuery = Double.MAX_VALUE;

	private MapView mapView;
	
	@UiField IntegerBox period1QueryInputBox;
	@UiField IntegerBox period2QueryInputBox;
	@UiField DoubleBox uncertaintyQueryInputBox;
	
	@UiField Label period1QueryLabel;
	@UiField Label period2QueryLabel;
	@UiField Label uncertaintyQueryLabel;
	
	@UiField Button filterButton;
	
	@UiHandler("filterButton")
	void handleFilterClick(ClickEvent e) {
		mapView.getMapData();
	}
	

	public MapFilter(MapView mapView) {
		this.mapView = mapView;
		initWidget(uiBinder.createAndBindUi(this));
		
		// Create EventHandler for filtering with enter key and tool tips.
		MapFilterEventHandler eventHandler = new MapFilterEventHandler(this, mapView);

	}
	
	public void setFilterValues() throws InvalidInputException {
		resetFilterValues();
		
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
			
		// Check input for period1Query.
		String period1QueryInputString = period1QueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(period1QueryInputString)) {
			try {
				period1Query = period1QueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + period1QueryInputString + "' is not a valid starting year of 'first period'.");
				throw new InvalidInputException();
			}
		} else {
			Window.alert("Invalid filter request. Please choose a 'First Year Period 1'.");
			throw new InvalidInputException();
		}

		// Check input for period2Query.
		String period2QueryInputString = period2QueryInputBox.getText();
		if(!InputValidityChecker.isEmpty(period2QueryInputString)) {
			try {
				period2Query = period2QueryInputBox.getValueOrThrow();
			} catch(ParseException e) {
				Window.alert("'" + period2QueryInputString + "' is not a valid starting year of 'first period'.");
				throw new InvalidInputException();
			}
		} else {
			Window.alert("Invalid filter request. Please choose a 'First Year Period 2'.");
			throw new InvalidInputException();
		}
	
		// Check if a valid time period is entered.
		if(period1Query != Integer.MIN_VALUE && period2Query != Integer.MIN_VALUE && period1Query >= period2Query) {
			Window.alert("The entered 'Starting Year of First Period' is greater than or equal to the entered"
					+ "'Ending Year of Second Period'.");
			throw new InvalidInputException();
		}
		// Check if periods are overlapping.
		if(period1Query != Integer.MIN_VALUE && period2Query != Integer.MIN_VALUE && period1Query + COMPARISON_PERIOD_LENGTH > period2Query) {
			Window.alert("The entered 'First Period' and 'Second Period' are overlapping.");
			throw new InvalidInputException();
		}
		// Check if entered periods ask for years that are not in the data file.
		if(period1Query != Integer.MIN_VALUE && period1Query < OLDEST_YEAR_IN_DATAFILE) {
			Window.alert("The data file cannot provide any data for the requested periods. Please use periods younger"
					+ " than or equal to " + OLDEST_YEAR_IN_DATAFILE + ".");
			throw new InvalidInputException();
		}
		if(period2Query != Integer.MIN_VALUE && period2Query > (YOUNGEST_YEAR_IN_DATAFILE - COMPARISON_PERIOD_LENGTH + 1)) {
			Window.alert("The data file cannot provide any data for the requested periods. Please use periods younger"
					+ " than or equal to " + (YOUNGEST_YEAR_IN_DATAFILE - COMPARISON_PERIOD_LENGTH + 1) + ".");
			throw new InvalidInputException();
		}		
	}
	
	private void resetFilterValues() {
		period1Query = Integer.MIN_VALUE;
		period2Query = Integer.MIN_VALUE;
		uncertaintyQuery = Double.MAX_VALUE;
	}

	public Button getFilterButton() {
		return filterButton;
	}

	public IntegerBox getPeriod1QueryInputBox() {
		return period1QueryInputBox;
	}

	public IntegerBox getPeriod2QueryInputBox() {
		return period2QueryInputBox;
	}

	public DoubleBox getUncertaintyQueryInputBox() {
		return uncertaintyQueryInputBox;
	}

	public Label getPeriod1QueryLabel() {
		return period1QueryLabel;
	}

	public Label getPeriod2QueryLabel() {
		return period2QueryLabel;
	}

	public Label getUncertaintyQueryLabel() {
		return uncertaintyQueryLabel;
	}

	public int getPeriod1Query() {
		return period1Query;
	}

	public int getPeriod2Query() {
		return period2Query;
	}

	public double getUncertaintyQuery() {
		return uncertaintyQuery;
	}
	
}
