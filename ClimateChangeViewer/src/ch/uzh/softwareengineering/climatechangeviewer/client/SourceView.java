package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SourceView extends Composite {
	
	interface SourceViewUiBinder extends UiBinder<Widget, SourceView> {}
	
	private static SourceViewUiBinder uiBinder = GWT.create(SourceViewUiBinder.class);
	
	public SourceView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
