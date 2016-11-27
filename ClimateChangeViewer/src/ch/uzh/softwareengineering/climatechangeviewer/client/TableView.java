package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
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
	private DataGrid<TableDataElement> table = new DataGrid<TableDataElement>();
	ListDataProvider<TableDataElement> dataProvider = new ListDataProvider<TableDataElement>();
	ListHandler<TableDataElement> sortHandler = new ListHandler<TableDataElement>(dataProvider.getList());
	
	private CustomDataGridFooter footer = new CustomDataGridFooter(0);

	private TextColumn<TableDataElement> nameColumn = new TextColumn<TableDataElement>() {
		@Override
		public String getValue(TableDataElement dataElement) {
			return dataElement.getCity();
		}
	};
	
	private TextColumn<TableDataElement> countryColumn = new TextColumn<TableDataElement>() {
		@Override
		public String getValue(TableDataElement dataElement) {
			return dataElement.getCountry();
		}
	};
	
	private TextColumn<TableDataElement> dateColumn = new TextColumn<TableDataElement>() {
		@Override
		public String getValue(TableDataElement dataElement) {
			return dataElement.getDate();
		}
	};
	
	private TextColumn<TableDataElement> temperatureColumn = new TextColumn<TableDataElement>() {
		@Override
		public String getValue(TableDataElement dataElement) {
			return dataElement.getTemperatureString();
		}
	};
	
	private TextColumn<TableDataElement> uncertaintyColumn = new TextColumn<TableDataElement>() {
		@Override
		public String getValue(TableDataElement dataElement) {
			return dataElement.getUncertaintyString();
		}
	};
	

	public TableView() {
		// Setting up data-grid table.
		sortHandler.setComparator(nameColumn, new Comparator<TableDataElement>() {
			@Override
			public int compare(TableDataElement o1, TableDataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getCity().compareTo(o2.getCity()) : 1;
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(countryColumn, new Comparator<TableDataElement>() {
			@Override
			public int compare(TableDataElement o1, TableDataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getCountry().compareTo(o2.getCountry()) : 1;
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(dateColumn, new Comparator<TableDataElement>() {
			@Override
			public int compare(TableDataElement o1, TableDataElement o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getDateForStringSorting().compareTo(o2.getDateForStringSorting()) : 1;
				}
				return -1;
			}
		});
		
		sortHandler.setComparator(temperatureColumn, new Comparator<TableDataElement>() {
			@Override
			public int compare(TableDataElement o1, TableDataElement o2) {
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
		
		sortHandler.setComparator(uncertaintyColumn, new Comparator<TableDataElement>() {
			@Override
			public int compare(TableDataElement o1, TableDataElement o2) {
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
		
		table.addColumn(nameColumn, new TextHeader("City"), footer); 
		table.addColumn(countryColumn, "Country");
		table.addColumn(dateColumn, "Date");
		table.addColumn(temperatureColumn, "Avg. Temperature");
		table.addColumn(uncertaintyColumn, "Avg. Uncertainty");

		table.setHeight("550px");
		table.setWidth("1000px");
		table.setPageSize(MAX_DATA_LINES_TO_SEND);
		table.setLoadingIndicator(null);

		// Assemble Main panel.
		mainPanel.add(filter.getPanel());
		mainPanel.add(table);
		
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
		
		// Reset row counter
		footer.setCounter(0);

		// Initialize the service proxy.
		if (querySvc == null) {
			querySvc = GWT.create(QueryService.class);
		}

		// Set up the callback object.
		AsyncCallback<List<TableDataElement>> callback = new AsyncCallback<List<TableDataElement>>() {
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

			public void onSuccess(List<TableDataElement> result) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(result);
				footer.setCounter(result.size());
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
		querySvc.getTableData(filter.getMonth(), filter.getYear1(), filter.getYear2(),
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
