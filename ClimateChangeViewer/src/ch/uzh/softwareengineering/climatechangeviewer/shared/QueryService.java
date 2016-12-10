package ch.uzh.softwareengineering.climatechangeviewer.shared;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ch.uzh.softwareengineering.climatechangeviewer.client.DataFileCorruptedException;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterOverflowException;

@RemoteServiceRelativePath("data")
public interface QueryService extends RemoteService {

	List<TableDataElement> getTableData(String cityQuery, String countryQuery, int year1Query, int year2Query, int monthQuery,
			double minTemperatureQuery, double maxTemperatureQuery, double uncertaintyQuery)
					throws FilterOverflowException, NoEntriesFoundException, DataFileCorruptedException;
	
	List<MapDataElement> getMapData(int period1StartQuery , int period2StartQuery, double uncertaintyQuery)
			throws NoEntriesFoundException, DataFileCorruptedException;
	
}