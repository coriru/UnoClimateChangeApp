package ch.uzh.softwareengineering.climatechangeviewer.shared;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QueryServiceAsync {

	void getTableData(String cityQuery, String countryQuery, int year1Query, int year2Query, int monthQuery,
			double minTemperatureQuery, double maxTemperatureQuery, double uncertaintyQuery,
			AsyncCallback<List<TableDataElement>> callback);
	
	void getMapData(int period1StartQuery , int period2StartQuery, double uncertaintyQuery,
			AsyncCallback<List<MapDataElement>> callback);
	
}
