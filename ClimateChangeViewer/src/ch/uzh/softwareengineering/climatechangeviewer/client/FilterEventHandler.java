package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FilterEventHandler extends Composite implements KeyDownHandler, MouseOverHandler, MouseOutHandler{
	
	private Filter filter;
	private TableView tableView;
	
	private FilterPopup popupMonth = new FilterPopup("If you choose a month from"
			+ " the drop-down menu only data from that month will be shown.");
	
	private FilterPopup popupYear1 = new FilterPopup("Year1 Text");
	
	private FilterPopup popupYear2 = new FilterPopup("Year2 Text");
	
	private FilterPopup popupCountry = new FilterPopup("Country Text");
	
	private FilterPopup popupCity = new FilterPopup("City Text");
	
	private FilterPopup popupMinTemperature = new FilterPopup("MinTemperature Text");
	
	private FilterPopup popupMaxTemperature = new FilterPopup("MaxTemperature Text");
	
	private FilterPopup popupMaxTemperatureUncertainty = new FilterPopup("MaxTemperatureUncertainty Text");
	
	private Label labelMonth;
	private Label labelYear1;
	private Label labelYear2;
	private Label labelCountry;
	private Label labelCity;
	private Label labelMinTemperature;
	private Label labelMaxTemperature;
	private Label labelMaxTemperatureUncertainty;
	
	private ListBox filterBoxMonth;
	private TextBox filterBoxYear1;
	private TextBox filterBoxYear2;
	private TextBox filterBoxCountry;
	private TextBox filterBoxCity;
	private TextBox filterBoxMinTemperature;
	private TextBox filterBoxMaxTemperature;
	private TextBox filterBoxMaxTemperatureUncertainty;
	
	
	public FilterEventHandler(Filter filter, TableView tableView) {
		this.filter = filter;
		this.tableView = tableView;
		
		// Adding MouseOverHandler and MouseOutHandler for the Labels.
		labelMonth = filter.getLabelMonth();
		labelMonth.addMouseOverHandler(this);
		labelMonth.addMouseOutHandler(this);
		
		labelYear1 = filter.getLabelYear1();
		labelYear1.addMouseOverHandler(this);
		labelYear1.addMouseOutHandler(this);
		
		labelYear2 = filter.getLabelYear2();
		labelYear2.addMouseOverHandler(this);
		labelYear2.addMouseOutHandler(this);
		
		labelCountry = filter.getLabelCountry();
		labelCountry.addMouseOverHandler(this);
		labelCountry.addMouseOutHandler(this);
		
		labelCity = filter.getLabelCity();
		labelCity.addMouseOverHandler(this);
		labelCity.addMouseOutHandler(this);
		
		labelMinTemperature = filter.getLabelMinTemperature();
		labelMinTemperature.addMouseOverHandler(this);
		labelMinTemperature.addMouseOutHandler(this);
		
		labelMaxTemperature = filter.getLabelMaxTemperature();
		labelMaxTemperature.addMouseOverHandler(this);
		labelMaxTemperature.addMouseOutHandler(this);
		
		labelMaxTemperatureUncertainty = filter.getLabelMaxTemperatureUncertainty();
		labelMaxTemperatureUncertainty.addMouseOverHandler(this);
		labelMaxTemperatureUncertainty.addMouseOutHandler(this);
		
		//Adding KeyDownHandler to the text boxes.
		filterBoxMonth = filter.getFilterBoxMonth();
		filterBoxMonth.addKeyDownHandler(this);

		filterBoxYear1 = filter.getFilterBoxYear1();
		filterBoxYear1.addKeyDownHandler(this);
		
		filterBoxYear2 = filter.getFilterBoxYear2();
		filterBoxYear2.addKeyDownHandler(this);
		
		filterBoxCountry = filter.getFilterBoxCountry();
		filterBoxCountry.addKeyDownHandler(this);
		
		filterBoxCity = filter.getFilterBoxCity();
		filterBoxCity.addKeyDownHandler(this);
		
		filterBoxMinTemperature = filter.getFilterBoxMinTemperature();
		filterBoxMinTemperature.addKeyDownHandler(this);
		
		filterBoxMaxTemperature = filter.getFilterBoxMaxTemperature();
		filterBoxMaxTemperature.addKeyDownHandler(this);
		
		filterBoxMaxTemperatureUncertainty = filter.getFilterBoxMaxTemperatureUncertainty();
		filterBoxMaxTemperatureUncertainty.addKeyDownHandler(this);
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == labelMonth) {
			popupMonth.showRelativeTo(labelMonth);
		} else if(sender == labelYear1) {
			popupYear1.showRelativeTo(labelYear1);
		} else if(sender == labelYear2) {
			popupYear2.showRelativeTo(labelYear2);
		} else if(sender == labelCountry) {
			popupCountry.showRelativeTo(labelCountry);
		} else if(sender == labelCity) {
			popupCity.showRelativeTo(labelCity);
		} else if(sender == labelMinTemperature) {
			popupMinTemperature.showRelativeTo(labelMinTemperature);
		} else if(sender == labelMaxTemperature) {
			popupMaxTemperature.showRelativeTo(labelMaxTemperature);
		} else if(sender == labelMaxTemperatureUncertainty) {
			popupMaxTemperatureUncertainty.showRelativeTo(labelMaxTemperatureUncertainty);
		}	
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == labelMonth) {
			popupMonth.hide();
		} else if(sender == labelYear1) {
			popupYear1.hide();
		} else if(sender == labelYear2) {
			popupYear2.hide();
		} else if(sender == labelCountry) {
			popupCountry.hide();
		} else if(sender == labelCity) {
			popupCity.hide();
		} else if(sender == labelMinTemperature) {
			popupMinTemperature.hide();
		} else if(sender == labelMaxTemperature) {
			popupMaxTemperature.hide();
		} else if(sender == labelMaxTemperatureUncertainty) {
			popupMaxTemperatureUncertainty.hide();
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == filterBoxYear1) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		} else if(sender == filterBoxYear2) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		} else if(sender == filterBoxCountry) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		} else if(sender == filterBoxCity) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		} else if(sender == filterBoxMinTemperature) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		} else if(sender == filterBoxMaxTemperature) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		} else if(sender == filterBoxMaxTemperatureUncertainty) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter.setValues();
				tableView.filterData();
			}
		}
	}
}
