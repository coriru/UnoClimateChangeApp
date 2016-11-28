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

public class MapFilterEventHandler extends Composite implements KeyDownHandler, MouseOverHandler, MouseOutHandler{
	
	private MapView mapView;
	
	private FilterPopup popupPeriod1 = new FilterPopup("Enter the first year of the first period that should be used for comparison.");
	
	private FilterPopup popupPeriod2 = new FilterPopup("Enter the first year of the second period that should be used for comparison.");
	
	private FilterPopup popupUncertaintyMap = new FilterPopup("Only data with a lower than the maximum average uncertainty entered here will be shown.");
	
	private Label labelPeriod1;
	private Label labelPeriod2;
	private Label labelUncertaintyMap;
	
	private TextBox filterBoxPeriod1;
	private TextBox filterBoxPeriod2;
	private TextBox filterBoxUncertaintyMap;
	
	public MapFilterEventHandler(MapFilter filter, MapView mapView) {
		this.mapView = mapView;
		
		// Adding MouseOverHandler and MouseOutHandler for the Labels.
		labelUncertaintyMap = filter.getLabelUncertainty();
		labelUncertaintyMap.addMouseOverHandler(this);
		labelUncertaintyMap.addMouseOutHandler(this);

		labelPeriod1 = filter.getLabelPeriod1();
		labelPeriod1.addMouseOverHandler(this);
		labelPeriod1.addMouseOutHandler(this);
		
		labelPeriod2 = filter.getLabelPeriod2();
		labelPeriod2.addMouseOverHandler(this);
		labelPeriod2.addMouseOutHandler(this);
		
		//Adding KeyDownHandler to the text boxes.
		filterBoxUncertaintyMap = filter.getFilterBoxUncertainty();
		filterBoxUncertaintyMap.addKeyDownHandler(this);
		
		filterBoxPeriod1 = filter.getFilterBoxPeriod1();
		filterBoxPeriod1.addKeyDownHandler(this);
		
		filterBoxPeriod2 = filter.getFilterBoxPeriod2();
		filterBoxPeriod2.addKeyDownHandler(this);
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == labelPeriod1) {
			popupPeriod1.showRelativeTo(labelPeriod1);
		} else if(sender == labelPeriod2) {
			popupPeriod2.showRelativeTo(labelPeriod2);
		} else if(sender == labelUncertaintyMap) {
			popupUncertaintyMap.showRelativeTo(labelUncertaintyMap);
		}	
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == labelPeriod1) {
			popupPeriod1.hide();
		} else if(sender == labelPeriod2) {
			popupPeriod2.hide();
		} else if(sender == labelUncertaintyMap) {
			popupUncertaintyMap.hide();
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == filterBoxPeriod1) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		} else if(sender == filterBoxPeriod2) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		} else if(sender == filterBoxUncertaintyMap) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		}
	}
	
}
