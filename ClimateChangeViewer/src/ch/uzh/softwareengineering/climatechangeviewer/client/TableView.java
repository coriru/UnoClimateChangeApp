package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class TableView extends View {
	
	private static final int MAX_DATA_LINES_TO_SEND = 1000;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Button filterButton = new Button("Filter");
	private Filter filter = new Filter(this);
	
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	private DataGrid<DataElement> table = new DataGrid<DataElement>();
	ListDataProvider<DataElement> dataProvider = new ListDataProvider<DataElement>();
	ListHandler<DataElement> sortHandler = new ListHandler<DataElement>(dataProvider.getList());
	
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
			return dataElement.getUncertaintyString();
		}
	};
	

	public TableView() {
		// Setting up data-grid table.
		sortHandler.setComparator(nameColumn, new Comparator<DataElement>() {
			@Override
			public int compare(DataElement o1, DataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getCity().compareTo(o2.getCity()) : 1;
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(countryColumn, new Comparator<DataElement>() {
			@Override
			public int compare(DataElement o1, DataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getCountry().compareTo(o2.getCountry()) : 1;
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(dateColumn, new Comparator<DataElement>() {
			@Override
			public int compare(DataElement o1, DataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getDateForStringSorting().compareTo(o2.getDateForStringSorting()) : 1;
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(temperatureColumn, new Comparator<DataElement>() {
			@Override
			public int compare(DataElement o1, DataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					if(o2 != null) {
						if(o1.getTemperature() > o2.getTemperature()) {
							return 1;
						} else {
							return -1;
						}
					}
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(uncertaintyColumn, new Comparator<DataElement>() {
			@Override
			public int compare(DataElement o1, DataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					if(o2 != null) {
						if(o1.getUncertainty() > o2.getUncertainty()) {
							return 1;
						} else {
							return -1;
						}
					}
				}
				return -1;
			}
		});
		
		nameColumn.setSortable(true);
		countryColumn.setSortable(true);
		dateColumn.setSortable(true);
		temperatureColumn.setSortable(true);
		uncertaintyColumn.setSortable(true);
		table.addColumnSortHandler(sortHandler);
		
		table.addColumn(nameColumn, "City");
		table.addColumn(countryColumn, "Country");
		table.addColumn(dateColumn, "Date");
		table.addColumn(temperatureColumn, "Avg. Temperature");
		table.addColumn(uncertaintyColumn, "Avg. Uncertainty");

		table.setHeight("600px");
		table.setWidth("1000px");
		table.setPageSize(MAX_DATA_LINES_TO_SEND);
		table.setLoadingIndicator(null);

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

		// Set custom loading indicator.
		setLoadingIndicator();
		table.setRowCount(0, false);

		// Initialize the service proxy.
		if (querySvc == null) {
			querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<DataElement>> callback = new AsyncCallback<List<DataElement>>() {
			public void onFailure(Throwable caught) {
				if(caught instanceof FilterOverflowException) {
					// Remove loading indicator.
					table.setRowCount(0, true);
					
					// Create error message for the user.
					Window.alert("More than " + Integer.toString(MAX_DATA_LINES_TO_SEND)
							+ " entries found. Please set more precise filter criterias.");
					
				} else if(caught instanceof NoEntriesFoundException) {
					// Remove loading indicator.
					table.setRowCount(0, true);
					
					// Create error message for the user.
					Window.alert("No Entries found. Please adjust the filter criterias.");
					
				} else if(caught instanceof DataFileCorruptedException) {
					// Remove loading indicator.
					table.setRowCount(0, true);
					
					// Create error message for the user.
					Window.alert("The datafile is corrupted. The service is unavailable at the moment.");
					
				} else {
					// Remove loading indicator.
					table.setRowCount(0, true);
					
					// Create error message for the user.
					Window.alert("Unknown error. The service is unavailable at the moment.");
				}
			}

			public void onSuccess(List<DataElement> result) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(result);
				table.getColumnSortList().push(uncertaintyColumn);
				table.getColumnSortList().push(temperatureColumn);
				table.getColumnSortList().push(dateColumn);
				table.getColumnSortList().push(countryColumn);
				table.getColumnSortList().push(nameColumn);
				
				// Only invoke addDataDisplay method at this point, otherwise the loading indicator won't be shown.
				dataProvider.addDataDisplay(table);
			}
		};

		// Make the call to the queryService.		
		querySvc.getData(filter.getMonth(), filter.getYear1(), filter.getYear2(),
				filter.getCountry(), filter.getCity(), filter.getMinTemperature(),
				filter.getMaxTemperature(), filter.getUncertainty(), callback);

	}
	
	public void setLoadingIndicator() {
        table.setLoadingIndicator(new Image("/images/loadingboxes_128x128.gif"));
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
