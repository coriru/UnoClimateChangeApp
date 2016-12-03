package ch.uzh.softwareengineering.climatechangeviewer.resources;

import com.google.gwt.user.cellview.client.DataGrid;

public interface TableResource extends DataGrid.Resources {
	@Source({DataGrid.Style.DEFAULT_CSS, "DataGridOverride.css"})
	TableStyle dataGridStyle();
 
	interface TableStyle extends DataGrid.Style {}
}