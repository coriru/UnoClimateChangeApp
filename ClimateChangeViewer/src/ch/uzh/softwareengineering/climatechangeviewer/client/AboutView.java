package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AboutView extends Composite {
	
	interface AboutViewUiBinder extends UiBinder<Widget, AboutView> {}
	
	private static AboutViewUiBinder uiBinder = GWT.create(AboutViewUiBinder.class);
	
	public AboutView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
