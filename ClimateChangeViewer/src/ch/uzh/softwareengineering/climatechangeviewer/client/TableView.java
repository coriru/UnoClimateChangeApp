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
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TableView extends View {
	
	private static final int MAX_DATA_LINES_TO_SEND = 1000;
	
	private List<DataElement> data = new ArrayList<DataElement>();
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Button filterButton = new Button("Filter");
	private Filter filter = new Filter(this);
	private DataGrid<DataElement> table = new DataGrid<DataElement>();
	
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
	
	private TextColumn<DataElement> dateColumn = new TextColumn<DataElement>() {
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
		table.addColumn(nameColumn, "City");
		table.addColumn(countryColumn, "Country");
		table.addColumn(dateColumn, "Date");
		table.addColumn(temperatureColumn, "Avg. Temperature");
		table.addColumn(uncertaintyColumn, "Avg. Temperature Uncertainty");
		table.setColumnWidth(temperatureColumn,"300px");
		table.setColumnWidth(uncertaintyColumn,"300px");
		
		nameColumn.setSortable(true);
		countryColumn.setSortable(true);
		dateColumn.setSortable(true);
		temperatureColumn.setSortable(true);
		uncertaintyColumn.setSortable(true);
		
		
		table.setHeight("600px");
		table.setWidth("1000px");
		table.setLoadingIndicator(null);
		table.setPageSize(1000);

		// Assemble Main panel.
		mainPanel.add(filter.getPanel());
		mainPanel.add(table);

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
			filter.setFilterValues();

		} catch (InvalidInputException e) {
			return;
		}
		
		// Clear table and set Loading indicator
		setLoadingIndicator(table);	
		table.setVisibleRangeAndClearData(table.getVisibleRange(), true);

		// Initialize the service proxy.
		if (querySvc == null) {
			querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<DataElement>> callback = new AsyncCallback<List<DataElement>>() {
			public void onFailure(Throwable caught) {
				if(caught instanceof FilterOverflowException) {
					// Remove loading indicator.
					table.setLoadingIndicator(null);

					// Create error message for the user.
					Window.alert("More than " + Integer.toString(MAX_DATA_LINES_TO_SEND)
							+ " entries found. Please set more precise filter criterias.");
					
				} else if(caught instanceof NoEntriesFoundException) {
					// Remove loading indicator.
					table.setLoadingIndicator(null);
					
					// Create error message for the user.
					Window.alert("No Entries found. Please adjust the filter criterias.");
					
				} else if(caught instanceof DataFileCorruptedException) {
					// Remove loading indicator.
					table.setLoadingIndicator(null);
					
					// Create error message for the user.
					Window.alert("The datafile is corrupted. The service is unavailable at the moment.");
					
				} else {
					// Remove loading indicator.
					table.setLoadingIndicator(null);
					
					// Create error message for the user.
					Window.alert("Unknown error. The service is unavailable at the moment.");
				}
			}

			public void onSuccess(List<DataElement> result) {
				// Save returned and filtered data for possible later use.
				data = result;

				// Remove old entries first before adding the new ones.
				table.setRowCount(0);
				table.setRowCount(data.size(), true);
				table.setRowData(0, data);
			}
		};

		// Make the call to the queryService.		
		querySvc.getData(filter.getMonth(), filter.getYear1(), filter.getYear2(),
				filter.getCountry(), filter.getCity(), filter.getMinTemperature(),
				filter.getMaxTemperature(), filter.getUncertainty(), callback);

	}
	
	public void setLoadingIndicator(DataGrid<DataElement> table) {
        VerticalPanel vp = new VerticalPanel();
        AbsolutePanel ap = new AbsolutePanel();
        Image image = new Image("/images/loadingboxes.gif");
        HorizontalPanel hp = new HorizontalPanel();
        AbsolutePanel ap1 = new AbsolutePanel();

        ap1.setWidth("30px");
        hp.add(ap1);
        hp.add(image);

        ap.setHeight("50px");
        vp.add(ap);
        vp.add(hp);
        vp.setSpacing(10);
        
        table.setLoadingIndicator(vp);
    }
	
	public DataGrid<DataElement> getTable() {
		return table;
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
}
