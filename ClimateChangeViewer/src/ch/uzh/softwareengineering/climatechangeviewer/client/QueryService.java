package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("data")
public interface QueryService extends RemoteService {

	List<TableDataElement> getTableData(int month, int year1, int year2, String country, String city,
			double minTemperature, double maxTemperature, double maxTemperatureUncertainty)
					throws FilterOverflowException, NoEntriesFoundException, DataFileCorruptedException;
	
	List<MapDataElement> getMapData(int comparisonPerdiod1Start , int comparisonPerdiod2Start, double maxTemperatureUncertainty)
			throws NoEntriesFoundException, DataFileCorruptedException;
	
}