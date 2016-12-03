package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QueryServiceAsync {

	void getTableData(int month, int year1, int year2, String country, String city,
			double minTemperature, double maxTemperature, double maxTemperatureUncertainty, AsyncCallback<List<TableDataElement>> callback);
	
	void getMapData(int comparisonPerdiod1Start , int comparisonPerdiod2Start, double maxTemperatureUncertainty, AsyncCallback<List<MapDataElement>> callback);
	
}
