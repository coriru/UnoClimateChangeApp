package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.softwareengineering.climatechangeviewer.client.widget.slider.RangeSlider;
import ch.uzh.softwareengineering.climatechangeviewer.server.QueryServiceImpl;
import ch.uzh.softwareengineering.climatechangeviewer.shared.InvalidInputException;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HorizontalPanel;

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
	
	private int period1Query = Integer.MIN_VALUE;
	private int period2Query = Integer.MIN_VALUE;
	private double uncertaintyQuery = Double.MAX_VALUE;

	private MapView mapView;
	
    private RangeSlider rangeSlider;
    @UiField HorizontalPanel sliderPanel;
    @UiField DoubleBox uncertaintyQueryInputBox;
        
    @UiField Label uncertaintyQueryLabel;
    @UiField Label period1QueryLabel;
    @UiField Label period2QueryLabel;
    @UiField Label period1QueryValueLabel;
    @UiField Label period2QueryValueLabel;
	
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
		
		int sliderMinimum = QueryServiceImpl.OLDEST_YEAR_IN_DATAFILE;
		int sliderMaximum = QueryServiceImpl.LATEST_YEAR_IN_DATAFILE - MapView.COMPARISON_PERIOD_LENGTH + 1;
		rangeSlider = new RangeSlider("range", sliderMinimum, sliderMaximum, sliderMinimum, sliderMaximum);
		sliderPanel.add(rangeSlider);
		rangeSlider.addListener(eventHandler);
		
		period1QueryValueLabel.setText(sliderMinimum + " - " + (sliderMinimum + MapView.COMPARISON_PERIOD_LENGTH - 1));
		period2QueryValueLabel.setText(sliderMaximum + " - " + (sliderMaximum + MapView.COMPARISON_PERIOD_LENGTH - 1));
	}
	
	public void setFilterValues() throws InvalidInputException {
		resetFilterValues();
		
		// Check input for uncertaintyQuery. Replace ',' with '.' because GWT DoubleBox will ignore ','.
		String uncertaintyQueryInputString = uncertaintyQueryInputBox.getText();
		uncertaintyQueryInputBox.setText(uncertaintyQueryInputString.replace(',', '.'));
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
		
		// Get input from the slider.
		period1Query = rangeSlider.getValueAtIndex(0);
		period2Query = rangeSlider.getValueAtIndex(1);
	
		// Check if periods are overlapping.
		if(period1Query != Integer.MIN_VALUE && period2Query != Integer.MIN_VALUE && period1Query
				+ MapView.COMPARISON_PERIOD_LENGTH > period2Query) {
			Window.alert("The chosen 'Period 1' and 'Period 2' are overlapping.");
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
	
	public RangeSlider getRangeSlider() {
		return rangeSlider;
	}
	
	public void setRangeSliderValues(int min, int max) {
		rangeSlider.setValues(min, max);
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
	
	public Label getPeriod1QueryValueLabel() {
		return period1QueryValueLabel;
	}

	public Label getPeriod2QueryValueLabel() {
		return period2QueryValueLabel;
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
