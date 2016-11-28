package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SourceView extends View {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private DataGrid<SourceDataElement> table = new DataGrid<SourceDataElement>();
	
	private TextColumn<SourceDataElement> dataColumn = new TextColumn<SourceDataElement>() {
		@Override
		public String getValue(SourceDataElement dataElement) {
			return dataElement.getData();
		}
	};
	
	private TextColumn<SourceDataElement> sourceColumn = new TextColumn<SourceDataElement>() {
		@Override
		public String getValue(SourceDataElement dataElement) {
			return dataElement.getSource();
		}
	};
	
	public List<SourceDataElement> tableData = new ArrayList<SourceDataElement>();
	
	private void addSourceDataElement(String data, String Source) {
		SourceDataElement dataElement = new SourceDataElement();
    	dataElement.setData(data);
    	dataElement.setSource(Source);
    	
    	tableData.add(dataElement);
	}
	
	public SourceView() {
		table.addColumn(dataColumn, "Data");
		table.addColumn(sourceColumn, "Source");
		
		table.setHeight("550px");
		table.setWidth("1000px");
		
		// Assemble Main panel.
		mainPanel.add(table);
		
		//Collect all different data types with their sources in a list.
		addSourceDataElement("Temperature Data", "Berkeley Earth Project: www.berkeleyearth.org");
		addSourceDataElement("Map View", "2016, Google Maps / NASA TerraMetrics: www.google.com/intl/de-DE_US/help/terms_maps.html");

		//Add all SourceDataElements of the list to the table.
		table.setRowData(tableData);
	}
	
	@Override
	public Panel getPanel() {
		return mainPanel;
	}

}
