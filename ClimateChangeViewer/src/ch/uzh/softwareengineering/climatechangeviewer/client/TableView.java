package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TableView extends View {
	
	private List<DataElement> data = new ArrayList<DataElement>();
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Button filterButton = new Button("Filter");
	private Filter filter = new Filter(this);
	private DataGrid<DataElement> table = new DataGrid<DataElement>();
	//private FlexTable dataTable = new FlexTable();
	
	private TextColumn<DataElement> nameColumn = new TextColumn<DataElement>() {
		@Override
		public String getValue(DataElement dataElement) {
			return dataElement.getCity();
		}
	};
	
	private TextColumn<DataElement> countryColumn = new TextColumn<DataElement>() {
		@Override
		public String getValue(DataElement dataElement) {
			return dataElement.getCountry();
		}
	};
	
	private TextColumn<DataElement> dateColum = new TextColumn<DataElement>() {
		@Override
		public String getValue(DataElement dataElement) {
			return dataElement.getDate();
		}
	};
	
	private TextColumn<DataElement> temperatureColumn = new TextColumn<DataElement>() {
		@Override
		public String getValue(DataElement dataElement) {
			return dataElement.getTemperatureString();
		}
	};
	
	private TextColumn<DataElement> uncertaintyColumn = new TextColumn<DataElement>() {
		@Override
		public String getValue(DataElement dataElement) {
			return dataElement.getTemperatureUncertaintyString();
		}
	};
	
	public TableView() {
		// Setting up data-grid table. 
		
		//nameColumn.setSortable(true);
		//countryColumn.setSortable(true);
		
		table.addColumn(nameColumn, "City");
		table.addColumn(countryColumn, "Country");
		table.addColumn(dateColum, "Date");
		table.addColumn(temperatureColumn, "Avg. Temperature");
		table.addColumn(uncertaintyColumn, "Avg. Temperature Uncertainty");
		table.setColumnWidth(temperatureColumn,"300px");
		table.setColumnWidth(uncertaintyColumn,"300px");

		table.setHeight("600px");
		table.setWidth("1200px");
		table.setPageSize(1000);
		
		// Create table for climate change data.
//		dataTable.setText(0, 0, "Nr.");
//		dataTable.setText(0, 1, "Date");
//		dataTable.setText(0, 2, "City");
//		dataTable.setText(0, 3, "Country");
//		dataTable.setText(0, 4, "Average Temperature");
//		dataTable.setText(0, 5, "Average Temperature Uncertainty");

		// Add styles to elements in the data table.
//		dataTable.getRowFormatter().addStyleName(0, "dataListHeader");
//		dataTable.addStyleName("dataList");
//		dataTable.getCellFormatter().addStyleName(0, 0, "dataListRowCountColumn");
//		dataTable.getCellFormatter().addStyleName(0, 1, "dataListDateColumn");
//		dataTable.getCellFormatter().addStyleName(0, 2, "dataListColumn");
//		dataTable.getCellFormatter().addStyleName(0, 3, "dataListColumn");
//		dataTable.getCellFormatter().addStyleName(0, 4, "dataListColumn");
//		dataTable.getCellFormatter().addStyleName(0, 5, "dataListColumn");

		// Add styles to elements in the data table.
//		dataTable.setCellPadding(6);

		// Assemble Main panel.
		mainPanel.add(filter.getPanel());
		//mainPanel.add(filterButton);
		mainPanel.add(table);
		//mainPanel.add(dataTable);

		// TODO Create a TableViewEventHandler class if there are several more 
		//		EventHandlers added.
		
		// Add ClickEventHandler to the filter button.
		filterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				filterData();
			}
		});
	}
	   
	public void filterData() {
		try {
			filter.setValues();
		} catch (InvalidCharacterException e) {
			return;
		}
		
		// Initialize the service proxy.
		if (querySvc == null) {
			querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<DataElement>> callback = new AsyncCallback<List<DataElement>>() {
			public void onFailure(Throwable caught) {
				if(caught instanceof FilterOverflowException) {
					OverflowDialog dialog = new OverflowDialog();
					int left = Window.getClientWidth()/ 2;
			        int top = Window.getClientHeight()/ 2;
			        dialog.setPopupPosition(left - 150, top - 50);
			        dialog.show();
				} else if(caught instanceof NoEntriesFoundException) {
					NoEntriesFoundDialog dialog = new NoEntriesFoundDialog();
					int left = Window.getClientWidth()/ 2;
			        int top = Window.getClientHeight()/ 2;
			        dialog.setPopupPosition(left - 150, top - 50);
			        dialog.show();
				}
			}

			public void onSuccess(List<DataElement> result) {
				// Save returned and filtered data for possible later use.
				data = result;
				
				// Remove old entries first before adding the new ones. 
				table.setRowCount(0);
				table.setRowCount(data.size(), true);
				table.setRowData(0, data);
				//addDataToTable();
			}
		};

		// Make the call to the queryService.		
		querySvc.getData(filter.getMonth(), filter.getYear1(), filter.getYear2(),
				filter.getCountry(), filter.getCity(), filter.getMinTemperature(),
				filter.getMaxTemperature(), filter.getMaxTemperatureUncertainty(), callback);

	}
	
	public Button getFilterButton() {
		return filterButton;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	public Panel getPanel() {
		return mainPanel;
	}
	
//	private void addDataToTable() {	
//		// Remove old table content if there is any.
//		if (dataTable.getRowCount() > 1) {
//			for(int j = dataTable.getRowCount(); j > 1; j--)  {
//				dataTable.removeRow(j-1);
//			}
//		}
//		
//		// Add the data to the table.
//		for(int i = 0; i < data.size(); i++) {
//    		int row = dataTable.getRowCount();
//        	String date = data.get(i).getDate();
//        	String city = data.get(i).getCityName();
//        	String country = data.get(i).getCountry();
//        	String temp = data.get(i).getAverageTemperatute();
//        	String tempUnc = data.get(i).getAverageTemperatureUncertainty();
//            
//            dataTable.setText(row, 0, Integer.toString(row));
//            dataTable.setText(row, 1, date);
//            dataTable.setText(row, 2, city);
//            dataTable.setText(row, 3, country);
//            dataTable.setText(row, 4, temp);
//            dataTable.setText(row, 5, tempUnc);
//          
//            
//            dataTable.getCellFormatter().addStyleName(row, 0, "dataListRowCountColumn");
//            dataTable.getCellFormatter().addStyleName(row, 1, "dataListDateColumn");
//            dataTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
//            dataTable.getCellFormatter().addStyleName(row, 3, "watchListNumericColumn");
//            dataTable.getCellFormatter().addStyleName(row, 4, "watchListNumericColumn"); 
//            dataTable.getCellFormatter().addStyleName(row, 5, "watchListNumericColumn"); 
//    	}        
//    }
	
}
