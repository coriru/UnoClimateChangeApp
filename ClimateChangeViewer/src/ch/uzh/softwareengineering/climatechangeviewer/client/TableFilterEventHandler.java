package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TableFilterEventHandler extends Composite implements KeyDownHandler, MouseOverHandler, MouseOutHandler {
	
	public static final int TOOLTIP_OFFSET_Y = 70;
	
	private TableView tableView;

	private FilterTooltip popupMonth = new FilterTooltip("If you choose a month from"
			+ " the drop-down menu only data from that month will be shown.");
	private FilterTooltip year1QueryToolTip = new FilterTooltip("Only data starting from the year entered here will be shown.");
	private FilterTooltip year2QueryToolTip = new FilterTooltip("Only data until the year entered here will be shown.");
	private FilterTooltip countryQueryToolTip = new FilterTooltip("Only data for the country entered here will be shown.");
	private FilterTooltip cityQueryToolTip = new FilterTooltip("Only data for the city entered here will be shown.");
	private FilterTooltip minTemperatureQueryToolTip = new FilterTooltip("Only data starting from the temperature entered will be shown.");
	private FilterTooltip maxTemperatureQueryToolTip = new FilterTooltip("Only data until the temperature entered here will be shown.");
	private FilterTooltip uncertaintyQueryToolTip = new FilterTooltip("Only data with a lower than the maximum average uncertainty entered here will be shown.");
	
	private Label cityQueryLabel;
	private Label countryQueryLabel;
	private Label year1QueryLabel;
	private Label year2QueryLabel;
	private Label monthQueryLabel;
	private Label minTemperatureQueryLabel;
	private Label maxTemperatureQueryLabel;
	private Label uncertaintyQueryLabel;
	
	private TextBox cityQueryInputBox;
	private TextBox countryQueryInputBox;
	private IntegerBox year1QueryInputBox;
	private IntegerBox year2QueryInputBox;
	private ListBox monthQueryInputBox;
	private DoubleBox minTemperatureQueryInputBox;
	private DoubleBox maxTemperatureQueryInputBox;
	private DoubleBox uncertaintyQueryInputBox;
	
	public TableFilterEventHandler(TableFilter filter, TableView tableView) {
		this.tableView = tableView;
		
		// Adding MouseOverHandler and MouseOutHandler for the Labels.
		cityQueryLabel = filter.getCityQueryLabel();
		cityQueryLabel.addMouseOverHandler(this);
		cityQueryLabel.addMouseOutHandler(this);
		
		countryQueryLabel = filter.getCountryQueryLabel();
		countryQueryLabel.addMouseOverHandler(this);
		countryQueryLabel.addMouseOutHandler(this);
		
		year1QueryLabel = filter.getYear1QueryLabel();
		year1QueryLabel.addMouseOverHandler(this);
		year1QueryLabel.addMouseOutHandler(this);
		
		year2QueryLabel = filter.getYear2QueryLabel();
		year2QueryLabel.addMouseOverHandler(this);
		year2QueryLabel.addMouseOutHandler(this);
		
		monthQueryLabel = filter.getMonthQueryLabel();
		monthQueryLabel.addMouseOverHandler(this);
		monthQueryLabel.addMouseOutHandler(this);
		
		minTemperatureQueryLabel = filter.getMinTemperatureQueryLabel();
		minTemperatureQueryLabel.addMouseOverHandler(this);
		minTemperatureQueryLabel.addMouseOutHandler(this);
		
		maxTemperatureQueryLabel = filter.getMaxTemperatureQueryLabel();
		maxTemperatureQueryLabel.addMouseOverHandler(this);
		maxTemperatureQueryLabel.addMouseOutHandler(this);
		
		uncertaintyQueryLabel = filter.getUncertaintyQueryLabel();
		uncertaintyQueryLabel.addMouseOverHandler(this);
		uncertaintyQueryLabel.addMouseOutHandler(this);
		
		//Adding KeyDownHandler to the text boxes.
		cityQueryInputBox = filter.getCityQueryInputBox();
		cityQueryInputBox.addKeyDownHandler(this);
		
		countryQueryInputBox = filter.getCountryQueryInputBox();
		countryQueryInputBox.addKeyDownHandler(this);
		
		year1QueryInputBox = filter.getYear1QueryInputBox();
		year1QueryInputBox.addKeyDownHandler(this);
		
		year2QueryInputBox = filter.getYear2QueryInputBox();
		year2QueryInputBox.addKeyDownHandler(this);
		
		monthQueryInputBox = filter.getMonthQueryInputBox();
		monthQueryInputBox.addKeyDownHandler(this);
		
		minTemperatureQueryInputBox = filter.getMinTemperatureQueryInputBox();
		minTemperatureQueryInputBox.addKeyDownHandler(this);
		
		maxTemperatureQueryInputBox = filter.getMaxTemperatureQueryInputBox();
		maxTemperatureQueryInputBox.addKeyDownHandler(this);
		
		uncertaintyQueryInputBox = filter.getUncertaintyQueryInputBox();
		uncertaintyQueryInputBox.addKeyDownHandler(this);
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == monthQueryLabel) {
			popupMonth.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			popupMonth.show();
		} else if(sender == year1QueryLabel) {
			year1QueryToolTip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			year1QueryToolTip.show();
		} else if(sender == year2QueryLabel) {
			year2QueryToolTip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			year2QueryToolTip.show();
		} else if(sender == countryQueryLabel) {
			countryQueryToolTip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			countryQueryToolTip.show();
		} else if(sender == cityQueryLabel) {
			cityQueryToolTip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			cityQueryToolTip.show();
		} else if(sender == minTemperatureQueryLabel) {
			minTemperatureQueryToolTip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			minTemperatureQueryToolTip.show();
		} else if(sender == maxTemperatureQueryLabel) {
			maxTemperatureQueryToolTip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			maxTemperatureQueryToolTip.show();
		} else if(sender == uncertaintyQueryLabel) {
			uncertaintyQueryToolTip.setPopupPosition(event.getClientX(), event.getClientY()- TOOLTIP_OFFSET_Y);
			uncertaintyQueryToolTip.show();
		}
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == monthQueryLabel) {
			popupMonth.hide();
		} else if(sender == year1QueryLabel) {
			year1QueryToolTip.hide();
		} else if(sender == year2QueryLabel) {
			year2QueryToolTip.hide();
		} else if(sender == countryQueryLabel) {
			countryQueryToolTip.hide();
		} else if(sender == cityQueryLabel) {
			cityQueryToolTip.hide();
		} else if(sender == minTemperatureQueryLabel) {
			minTemperatureQueryToolTip.hide();
		} else if(sender == maxTemperatureQueryLabel) {
			maxTemperatureQueryToolTip.hide();
		} else if(sender == uncertaintyQueryLabel) {
			uncertaintyQueryToolTip.hide();
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == year1QueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				tableView.filterData();
			}
		} else if(sender == year2QueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				tableView.filterData();
			}
		} else if(sender == countryQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {		
				tableView.filterData();
			}
		} else if(sender == cityQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				tableView.filterData();
			}
		} else if(sender == minTemperatureQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				tableView.filterData();
			}
		} else if(sender == maxTemperatureQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				tableView.filterData();
			}
		} else if(sender == uncertaintyQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				tableView.filterData();
			}
		}
	}
	
}
