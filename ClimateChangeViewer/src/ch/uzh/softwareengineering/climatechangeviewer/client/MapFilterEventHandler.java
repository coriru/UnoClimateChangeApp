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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.softwareengineering.climatechangeviewer.client.widget.slider.SliderEvent;
import ch.uzh.softwareengineering.climatechangeviewer.client.widget.slider.SliderListener;

public class MapFilterEventHandler extends Composite implements KeyDownHandler, MouseOverHandler, MouseOutHandler, SliderListener {
	
	public static final int COMPARISON_PERIOD_LENGTH = MapView.COMPARISON_PERIOD_LENGTH;
	public static final int TOOLTIP_OFFSET_Y = 70;
	
	private MapView mapView;
	
	private FilterTooltip period1QueryTooltip = new FilterTooltip("Enter the first year of the first period that should be used for comparison.");
	private FilterTooltip period2QueryTooltip = new FilterTooltip("Enter the first year of the second period that should be used for comparison.");
	private FilterTooltip uncertaintyQueryTooltip = new FilterTooltip("Only data with a lower than the maximum average uncertainty entered here will be shown.");
	
	private DoubleBox uncertaintyQueryInputBox;
	
	private Label period1QueryLabel;
	private Label period2QueryLabel;
	private Label period1QueryValueLabel;
	private Label period2QueryValueLabel;
	private Label uncertaintyQueryLabel;
	
	public MapFilterEventHandler(MapFilter filter, MapView mapView) {
		this.mapView = mapView;
		
		// Adding MouseOverHandler and MouseOutHandler for the Labels.
		period1QueryLabel = filter.getPeriod1QueryLabel();
		period1QueryLabel.addMouseOverHandler(this);
		period1QueryLabel.addMouseOutHandler(this);
		
		period2QueryLabel = filter.getPeriod2QueryLabel();
		period2QueryLabel.addMouseOverHandler(this);
		period2QueryLabel.addMouseOutHandler(this);
		
		period1QueryValueLabel = filter.getPeriod1QueryValueLabel();
		period1QueryValueLabel.addMouseOverHandler(this);
		period1QueryValueLabel.addMouseOutHandler(this);
		
		period2QueryValueLabel = filter.getPeriod2QueryValueLabel();
		period2QueryValueLabel.addMouseOverHandler(this);
		period2QueryValueLabel.addMouseOutHandler(this);
		
		uncertaintyQueryLabel = filter.getUncertaintyQueryLabel();
		uncertaintyQueryLabel.addMouseOverHandler(this);
		uncertaintyQueryLabel.addMouseOutHandler(this);

		//Adding KeyDownHandler to the text boxes.
		uncertaintyQueryInputBox = filter.getUncertaintyQueryInputBox();
		uncertaintyQueryInputBox.addKeyDownHandler(this);
		
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == period1QueryLabel) {
			period1QueryTooltip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			period1QueryTooltip.show();
		} else if(sender == period2QueryLabel) {
			period2QueryTooltip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			period2QueryTooltip.show();
		} else if(sender == period1QueryValueLabel) {
			period1QueryTooltip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			period1QueryTooltip.show();
		} else if(sender == period2QueryValueLabel) {
			period2QueryTooltip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			period2QueryTooltip.show();
		} else if(sender == uncertaintyQueryLabel) {
			uncertaintyQueryTooltip.setPopupPosition(event.getClientX(), event.getClientY() - TOOLTIP_OFFSET_Y);
			uncertaintyQueryTooltip.show();
		} else if(sender == uncertaintyQueryTooltip) {
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
		} else if(sender == period1QueryValueLabel) {
			period1QueryTooltip.hide();
		} else if(sender == period2QueryValueLabel) {
			period2QueryTooltip.hide();
		} else if(sender == uncertaintyQueryLabel) {
			uncertaintyQueryTooltip.hide();
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == uncertaintyQueryInputBox) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mapView.getMapData();
			}
		}
	}
	
    @Override
    public void onChange(SliderEvent event) {
        // We don't need to do anything, because everything is done in onSlide.
    }

    @Override
    public boolean onSlide(SliderEvent event) {
        period1QueryValueLabel.setText(event.getValues()[0] + " - " + (event.getValues()[0] + COMPARISON_PERIOD_LENGTH - 1));
        period2QueryValueLabel.setText(event.getValues()[1] + " - " + (event.getValues()[1] + COMPARISON_PERIOD_LENGTH - 1));
        return true;
    }

    @Override
    public void onStart(SliderEvent event) {
        // We are not going to do anything onStart.
    }

    @Override
    public void onStop(SliderEvent event) {
        // We are not going to do anything onStop.      
    }
	
}
