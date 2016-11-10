package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import ch.uzh.softwareengineering.climatechangeviewer.client.QueryService;
import ch.uzh.softwareengineering.climatechangeviewer.client.QueryServiceAsync;

public class ClimateChangeViewer implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable dataTable = new FlexTable();
	private Button filterButton = new Button("Filter");
	  
	private Filter filter1 = new Filter();
	  
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	  
	  	/**
	  	 * Entry point method.
	  	 */
		public void onModuleLoad() {
			// Create table for stock data.
			dataTable.setText(0, 0, "Nr.");
			dataTable.setText(0, 1, "Date");
			dataTable.setText(0, 2, "City");
			dataTable.setText(0, 3, "Country");

			// Add styles to elements in the stock list table.
			dataTable.getRowFormatter().addStyleName(0, "dataListHeader");
			dataTable.addStyleName("dataList");
			dataTable.getCellFormatter().addStyleName(0, 0, "dataListRowCountColumn");
			dataTable.getCellFormatter().addStyleName(0, 1, "dataListDateColumn");
			dataTable.getCellFormatter().addStyleName(0, 2, "dataListColumn");
			dataTable.getCellFormatter().addStyleName(0, 3, "dataListColumn");
	    
			// Add styles to elements in the stock list table.
			dataTable.setCellPadding(6);

			// Assemble Main panel.
			mainPanel.add(filter1.getPanel());
			mainPanel.add(filterButton);
			mainPanel.add(dataTable);
			
	    
			// Associate the Main panel with the HTML host page.
			RootPanel.get("dataList").add(mainPanel);
	     
			//Filter and import data
			filterButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String date = filter1.getTextBoxDate().getText();
					
					filter1.getTextBoxDate().setText("");
					
					String country = filter1.getTextBoxCountry().getText();
					filter1.getTextBoxCountry().setText("");
					
					String city = filter1.getTextBoxCity().getText();					
					filter1.getTextBoxCity().setText("");
					
					filter1.setValue(date, country, city);
					importData();
				}
			});
		}
	  
		
		private void addData(List<City> data) {	
			
			// Remove old table content if there is any.
			if (dataTable.getRowCount() != 0) {
				dataTable.removeAllRows();
			}
			
			// Add the stock to the table
			for(int i = 0; i < data.size(); i++) {
	    		int row = dataTable.getRowCount();
	        	String date = data.get(i).getDate();
	        	String city = data.get(i).getCityName();
	        	String country = data.get(i).getCountry();
	            
	            dataTable.setText(row, 0, Integer.toString(row));
	            dataTable.setText(row, 1, date);
	            dataTable.setText(row, 2, city);
	            dataTable.setText(row, 3, country);
	            
	            dataTable.getCellFormatter().addStyleName(row, 0, "dataListRowCountColumn");
	            dataTable.getCellFormatter().addStyleName(row, 1, "dataListDateColumn");
	            dataTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
	            dataTable.getCellFormatter().addStyleName(row, 3, "watchListNumericColumn"); 
	    	}        
	    }
		
	    
		private void importData() {
			// Initialize the service proxy.
			if (querySvc == null) {
				querySvc = GWT.create(QueryService.class);
			}

			// Set up the callback object.
			AsyncCallback<List<City>> callback = new AsyncCallback<List<City>>() {
				public void onFailure(Throwable caught) {
					// TODO: Do something with errors.
				}

				public void onSuccess(List<City> result) {
					addData(result);
				}
			};
			// Make the call to the stock price service.
			querySvc.getData(filter1.getValueDate(), filter1.getValueCountry(), filter1.getValueCity(), callback);
		}
}
