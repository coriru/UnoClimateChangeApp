package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TableExport extends Composite {
	
	interface TableExportUiBinder extends UiBinder<Widget, TableExport> {}
	
	private static TableExportUiBinder uiBinder = GWT.create(TableExportUiBinder.class);
	
	public static final String PARAMETER_DELIMITER = "_";
	public static final String PARAMETER_NOT_SET_INDICATOR = "?";
	
	private String exportParameters = "?_?_?_?_?_?_?_?";
	private String cityParameter = PARAMETER_NOT_SET_INDICATOR;
	private String countryParameter = PARAMETER_NOT_SET_INDICATOR;
	private String year1Parameter = PARAMETER_NOT_SET_INDICATOR;
	private String year2Parameter = PARAMETER_NOT_SET_INDICATOR;
	private String monthParameter = PARAMETER_NOT_SET_INDICATOR;
	private String minTemperatureParameter = PARAMETER_NOT_SET_INDICATOR;
	private String maxTemperatureParameter = PARAMETER_NOT_SET_INDICATOR;
	private String uncertaintyParameter = PARAMETER_NOT_SET_INDICATOR;
	
	@UiField Button exportButton;
	
	@UiHandler("exportButton")
	void handleFilterClick(ClickEvent e) {
		setExportParameters();
		String url = GWT.getModuleBaseURL() + "tableExportService?parameters=" + exportParameters;
		Window.open(url, "_self", "status=0,toolbar=0,menubar=0,location=0,resizable=1");
	}
	
	public TableExport() {
		initWidget(uiBinder.createAndBindUi(this));
		deactivateExportButton();
	}
	
	public void activateExportButton() {
		exportButton.setEnabled(true);
	}
	
	public void deactivateExportButton() {
		exportButton.setEnabled(false);
	}	
	
	private void setExportParameters() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(cityParameter + PARAMETER_DELIMITER);
		sb.append(countryParameter + PARAMETER_DELIMITER);
		sb.append(year1Parameter + PARAMETER_DELIMITER);
		sb.append(year2Parameter + PARAMETER_DELIMITER);
		sb.append(monthParameter + PARAMETER_DELIMITER);
		sb.append(minTemperatureParameter + PARAMETER_DELIMITER);
		sb.append(maxTemperatureParameter + PARAMETER_DELIMITER);
		sb.append(uncertaintyParameter);
		
		exportParameters = sb.toString();
	}

	public void setCityParameter(String cityParameter) {
		this.cityParameter = cityParameter;
	}

	public void setCountryParameter(String countryParameter) {
		this.countryParameter = countryParameter;
	}

	public void setYear1Parameter(String year1Parameter) {
		this.year1Parameter = year1Parameter;
	}

	public void setYear2Parameter(String year2Parameter) {
		this.year2Parameter = year2Parameter;
	}

	public void setMonthParameter(String monthParameter) {
		this.monthParameter = monthParameter;
	}

	public void setMinTemperatureParameter(String minTemperatureParameter) {
		this.minTemperatureParameter = minTemperatureParameter;
	}

	public void setMaxTemperatureParameter(String maxTemperaturParameter) {
		this.maxTemperatureParameter = maxTemperaturParameter;
	}

	public void setUncertaintyParameter(String uncertaintyParameter) {
		this.uncertaintyParameter = uncertaintyParameter;
	}
	
}
