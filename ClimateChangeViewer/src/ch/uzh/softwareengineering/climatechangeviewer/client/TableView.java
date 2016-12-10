package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import ch.uzh.softwareengineering.climatechangeviewer.resources.TableResource;
import ch.uzh.softwareengineering.climatechangeviewer.shared.InvalidInputException;
import ch.uzh.softwareengineering.climatechangeviewer.shared.NoEntriesFoundException;
import ch.uzh.softwareengineering.climatechangeviewer.shared.QueryService;
import ch.uzh.softwareengineering.climatechangeviewer.shared.QueryServiceAsync;
import ch.uzh.softwareengineering.climatechangeviewer.shared.TableDataElement;

public class TableView extends Composite {

	public static final int MAX_DATA_LINES_TO_SEND = 1000;
	
	private QueryServiceAsync querySvc = GWT.create(QueryService.class);
	private boolean isBusy = false;
	
	private DataGrid.Resources tableResource = GWT.create(TableResource.class);
	private DataGrid<TableDataElement> table = new DataGrid<TableDataElement>(MAX_DATA_LINES_TO_SEND, tableResource);
	private CustomDataGridFooter footer = new CustomDataGridFooter(0);
	private ListDataProvider<TableDataElement> dataProvider = new ListDataProvider<TableDataElement>();
	private ListHandler<TableDataElement> sortHandler = new ListHandler<TableDataElement>(dataProvider.getList());
	private Image loadingIndicator = new Image("/images/loading-indicator_table_400x400.gif");

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
			return dataElement.getDateString();
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
	
	interface TableViewUiBinder extends UiBinder<Widget, TableView> {}
	private static TableViewUiBinder uiBinder = GWT.create(TableViewUiBinder.class);
	
	@UiField(provided = true) final TableFilter filter = new TableFilter(this);
	@UiField FlowPanel tableViewPanel;
	private Button filterButton = filter.filterButton;
	
	private TableExport tableExport = new TableExport();
	
	public TableView() {
		setupTable();
		initWidget(uiBinder.createAndBindUi(this));
		tableViewPanel.add(table);
		tableViewPanel.add(tableExport);
	}
	
	private void setupTable() {
		// Setting up sort function of the table.
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
					return (o2 != null) ? o1.getNumericalDateString().compareTo(o2.getNumericalDateString()) : 1;
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
		
		// Add footer and columns.
		table.addColumn(nameColumn, new TextHeader("City"), footer); 
		table.addColumn(countryColumn, "Country");
		table.addColumn(dateColumn, "Date");
		table.addColumn(temperatureColumn, "Avg. Temperature (°C)");
		table.addColumn(uncertaintyColumn, "Avg. Uncertainty (°C)");
		
		// Set layout options.
		table.setHeight("500px");
		table.setWidth("1000px");
		table.setPageSize(MAX_DATA_LINES_TO_SEND);
		table.setLoadingIndicator(null);
	}
	   
	public void filterData() {
		if(isBusy) {
			return;
		} else {
			filterButton.setEnabled(false);
			isBusy = true;
		}
	
		try {
			filter.setFilterValues();
		} catch (InvalidInputException e) {
			isBusy = false;
			filterButton.setEnabled(true);
			return;
		}
		// Deactivate export functionality.
		tableExport.deactivateExportButton();

		// Set custom loading indicator.
		showLoadingIndicator();
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
					setFilterReady();
					
					// Create error message for the user.
					Window.alert("More than " + Integer.toString(MAX_DATA_LINES_TO_SEND)
							+ " entries found. Please set more precise filter criterias.");
					
				} else if(caught instanceof NoEntriesFoundException) {
					setFilterReady();
					
					// Create error message for the user.
					Window.alert("No Entries found. Please adjust the filter criterias.");
					
				} else if(caught instanceof DataFileCorruptedException) {
					setFilterReady();
					
					// Create error message for the user.
					Window.alert("The data service is corrupted. The service is unavailable at the moment.");
					
				} else {
					setFilterReady();
					
					// Create error message for the user.
					Window.alert("Unknown error. The service is unavailable at the moment.");
				}
			}

			public void onSuccess(List<TableDataElement> result) {
				// Enable export functionality.
				tableExport.activateExportButton();
				
				// Allow new filter requests.
				isBusy = false;
				filterButton.setEnabled(true);
				
				// Inject data into the table.
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
		querySvc.getTableData(filter.getCityQuery(), filter.getCountryQuery(), filter.getYear1Query(),
				filter.getYear2Query(),  filter.getMonthQuery(), filter.getMinTemperatureQuery(),
				filter.getMaxTemperatureQuery(), filter.getUncertaintyQuery(), callback);

	}
	
	private void setFilterReady() {
		// Remove loading indicator.
		table.setRowCount(0, true);
		
		//Allow new requests.
		isBusy = false;
		filterButton.setEnabled(true);
	}

	
	public void showLoadingIndicator() {
        table.setLoadingIndicator(loadingIndicator);
    }
	
	public Button getFilterButton() {
		return filterButton;
	}
	
	public TableExport getTableExport() {
		return tableExport;
	}
	
	public TableFilter getFilter() {
		return filter;
	}
	
}
