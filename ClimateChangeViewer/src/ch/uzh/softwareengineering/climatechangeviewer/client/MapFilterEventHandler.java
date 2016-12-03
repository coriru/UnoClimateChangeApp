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
import com.google.gwt.user.client.ui.Widget;

public class MapFilterEventHandler extends Composite implements KeyDownHandler, MouseOverHandler, MouseOutHandler{
	
	private static final int FIXED_TOOLTIP_POSITON_X = 1120;
	private static final int FIXED_TOOLTIP_POSITON_Y = 110;
	
	private MapView mapView;
	
	private FilterTooltip period1QueryTooltip = new FilterTooltip("Enter the first year of the first period that should be used for comparison.");
	
	private FilterTooltip period2QueryTooltip = new FilterTooltip("Enter the first year of the second period that should be used for comparison.");
	
	private FilterTooltip uncertaintyQueryTooltip = new FilterTooltip("Only data with a lower than the maximum average uncertainty entered here will be shown.");
	
	private Label period1QueryLabel;
	private Label period2QueryLabel;
	private Label uncertaintyQueryLabel;
	
	private IntegerBox period1QueryInputBox;
	private IntegerBox period2QueryInputBox;
	private DoubleBox uncertaintyQueryInputBox;
	
	public MapFilterEventHandler(MapFilter filter, MapView mapView) {
		this.mapView = mapView;
		
		// Adding MouseOverHandler and MouseOutHandler for the Labels.
		period1QueryLabel = filter.getPeriod1QueryLabel();
		period1QueryLabel.addMouseOverHandler(this);
		period1QueryLabel.addMouseOutHandler(this);
		
		period2QueryLabel = filter.getPeriod2QueryLabel();
		period2QueryLabel.addMouseOverHandler(this);
		period2QueryLabel.addMouseOutHandler(this);
		
		uncertaintyQueryLabel = filter.getUncertaintyQueryLabel();
		uncertaintyQueryLabel.addMouseOverHandler(this);
		uncertaintyQueryLabel.addMouseOutHandler(this);

		//Adding KeyDownHandler to the text boxes.

		period1QueryInputBox = filter.getPeriod1QueryInputBox();
		period1QueryInputBox.addKeyDownHandler(this);
		
		period2QueryInputBox = filter.getPeriod2QueryInputBox();
		period2QueryInputBox.addKeyDownHandler(this);
		
		uncertaintyQueryInputBox = filter.getUncertaintyQueryInputBox();
		uncertaintyQueryInputBox.addKeyDownHandler(this);
		
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == period1QueryLabel) {
			period1QueryTooltip.setPopupPosition(FIXED_TOOLTIP_POSITON_X, FIXED_TOOLTIP_POSITON_Y);
			period1QueryTooltip.show();
		} else if(sender == period2QueryLabel) {
			period2QueryTooltip.setPopupPosition(FIXED_TOOLTIP_POSITON_X, FIXED_TOOLTIP_POSITON_Y);
			period2QueryTooltip.show();
		} else if(sender == uncertaintyQueryLabel) {
			uncertaintyQueryTooltip.setPopupPosition(FIXED_TOOLTIP_POSITON_X, FIXED_TOOLTIP_POSITON_Y);
			uncertaintyQueryTooltip.show();
		}	
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();

		if(sender == period1QueryLabel) {
			period1QueryTooltip.hide();
		} else if(sender == period2QueryLabel) {
			period2QueryTooltip.hide();
		} else if(sender == uncertaintyQueryLabel) {
			uncertaintyQueryTooltip.hide();
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == period1QueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		} else if(sender == period2QueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		} else if(sender == uncertaintyQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		}
	}
	
}
