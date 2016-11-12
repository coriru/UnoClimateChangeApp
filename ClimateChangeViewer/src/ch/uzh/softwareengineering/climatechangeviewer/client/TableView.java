package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TableView extends View {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable dataTable = new FlexTable();
	private Button filterButton = new Button("Filter");
	private Filter filter = new Filter();
	
	private List<City> data = new ArrayList<City>();
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	
	public TableView() {
		// Create table for climate change data.
		dataTable.setText(0, 0, "Nr.");
		dataTable.setText(0, 1, "Date");
		dataTable.setText(0, 2, "City");
		dataTable.setText(0, 3, "Country");
		dataTable.setText(0, 4, "Average Temperature");
		dataTable.setText(0, 5, "Average Temperature Uncertainity");

		// Add styles to elements in the data table.
		dataTable.getRowFormatter().addStyleName(0, "dataListHeader");
		dataTable.addStyleName("dataList");
		dataTable.getCellFormatter().addStyleName(0, 0, "dataListRowCountColumn");
		dataTable.getCellFormatter().addStyleName(0, 1, "dataListDateColumn");
		dataTable.getCellFormatter().addStyleName(0, 2, "dataListColumn");
		dataTable.getCellFormatter().addStyleName(0, 3, "dataListColumn");
		dataTable.getCellFormatter().addStyleName(0, 4, "dataListColumn");
		dataTable.getCellFormatter().addStyleName(0, 5, "dataListColumn");

		// Add styles to elements in the stock list table.
		dataTable.setCellPadding(6);

		// Assemble Main panel.
		mainPanel.add(filter.getPanel());
		mainPanel.add(filterButton);
		mainPanel.add(dataTable);

		// TODO Create an EventHandler class if there are several more 
		//		EventHandlers added.
		
		// TODO Add KeyPressedEventHandler to make filtering possible by
		//		pressing the enter key.
		
		// Add ClickEventHandler to the filter button.
		filterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				filter.setValues();
				OverflowDialog dialog = new OverflowDialog();

		        dialog.show();
				filterData();
			}
		});

		
		// Can't add KeyDownHandler to Panel...
//		mainPanel.addKeyDownHandler(new KeyDownHandler() {
//			public void onKeyDown(KeyDownEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					filter.setValues();
//					filterData();
//				}
//			}
//		});
	}
	
	public Panel getPanel() {
		return mainPanel;
	}
	   
	private void filterData() {
		// Initialize the service proxy.
		if (querySvc == null) {
			querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<City>> callback = new AsyncCallback<List<City>>() {
			public void onFailure(Throwable caught) {
				OverflowDialog dialog = new OverflowDialog();
				int left = Window.getClientWidth()/ 2;
		        int top = Window.getClientHeight()/ 2;
		        dialog.setPopupPosition(left - 150, top - 50);
		        dialog.show();
			}

			public void onSuccess(List<City> result) {
				// save returned and filtered data for possible later use.
				data = result;
				addDataToTable();
			}
		};

		// Make the call to the queryService.		
		querySvc.getData(filter.getValueMonth(), filter.getValueYear1(), filter.getValueYear2(),
				filter.getValueCountry(), filter.getValueCity(), filter.getValueMinTemperature(),
				filter.getValueMaxTemperature(), filter.getValueMaxTemperatureUncertainty(), callback);

	}
	
	private void addDataToTable() {	
		// Remove old table content if there is any.
		if (dataTable.getRowCount() > 1) {
			for(int j = dataTable.getRowCount(); j > 1; j--)  {
				dataTable.removeRow(j-1);
			}
		}
		
		// Add the data to the table.
		for(int i = 0; i < data.size(); i++) {
    		int row = dataTable.getRowCount();
        	String date = data.get(i).getDate();
        	String city = data.get(i).getCityName();
        	String country = data.get(i).getCountry();
        	String temp = data.get(i).getAverageTemperatute();
        	String tempUnc = data.get(i).getAverageTemperatureUncertainty();
            
            dataTable.setText(row, 0, Integer.toString(row));
            dataTable.setText(row, 1, date);
            dataTable.setText(row, 2, city);
            dataTable.setText(row, 3, country);
            dataTable.setText(row, 4, temp);
            dataTable.setText(row, 5, tempUnc);
          
            
            dataTable.getCellFormatter().addStyleName(row, 0, "dataListRowCountColumn");
            dataTable.getCellFormatter().addStyleName(row, 1, "dataListDateColumn");
            dataTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
            dataTable.getCellFormatter().addStyleName(row, 3, "watchListNumericColumn");
            dataTable.getCellFormatter().addStyleName(row, 4, "watchListNumericColumn"); 
            dataTable.getCellFormatter().addStyleName(row, 5, "watchListNumericColumn"); 
    	}        
    }
	
}
