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
	private Button importDataButton = new Button("Import Data");
	private Button filterButton = new Button("Filter");
	  
	private Filter filter1 = new Filter();
	private Filter filter2 = new Filter();
	  
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
			mainPanel.add(filter2.getTextBox());
			mainPanel.add(filterButton);
			mainPanel.add(dataTable);
			mainPanel.add(importDataButton);
			
	    
			// Associate the Main panel with the HTML host page.
			RootPanel.get("dataList").add(mainPanel);
	     
			// Listen for mouse events on the Import button.
			importDataButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					importData();
	        	//new MyDialog("Imported successful").show();       	
	        	}
			});
	    
			filterButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					new MyDialog("filtering...").show(); 
					String v1 = filter1.getTextBox().getText();
					filter1.setValue(v1);
					String v2 = filter2.getTextBox().getText();
					filter1.setValue(v2);
					filter1.getTextBox().setText("");
					filter2.getTextBox().setText("");
				}
			});
		}
	  
		private static class MyDialog extends DialogBox {
			public MyDialog(String s) {
				// Set the dialog box's caption.
				setText(s);
				// Enable animation.
				setAnimationEnabled(true);
				// Enable glass background.
				setGlassEnabled(true);
				
				// DialogBox is a SimplePanel, so you have to set its widget property to
				// whatever you want its contents to be.
				
				Button ok = new Button("OK");
				ok.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						MyDialog.this.hide();
					}
				});
				setWidget(ok);
			}
		}
	    
		private void addData(List<City> data) {	
			for(int i = 0; i < data.size(); i++) {
	    		int row = dataTable.getRowCount();
	        	String date = data.get(i).getDate();
	        	String city = data.get(i).getCityName();
	        	String country = data.get(i).getCountry();
	            
	            // Add the stock to the table.
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
			querySvc.getData(filter1.getValue(), callback);
		}
}
