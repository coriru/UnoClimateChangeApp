package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OverflowDialog extends DialogBox {

	public OverflowDialog() {
		setText("Too many entries found.");
		setAnimationEnabled(true);
		setGlassEnabled(true);
		
		Button ok = new Button ("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				OverflowDialog.this.hide();
			}
		});
		
        Label label = new Label("Please add more precise filter criterias.");
        VerticalPanel panel = new VerticalPanel();
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setSpacing(10);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);
        panel.add(ok);

        setWidget(panel);
	}
	
	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		if (!event.isCanceled() && event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
        	 this.hide();
        }
		super.onPreviewNativeEvent(event);
	}
}
