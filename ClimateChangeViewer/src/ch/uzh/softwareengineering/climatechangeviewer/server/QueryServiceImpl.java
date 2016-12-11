package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.TableView;
import ch.uzh.softwareengineering.climatechangeviewer.shared.MapDataElement;
import ch.uzh.softwareengineering.climatechangeviewer.shared.NoEntriesFoundException;
import ch.uzh.softwareengineering.climatechangeviewer.shared.QueryService;
import ch.uzh.softwareengineering.climatechangeviewer.shared.TableDataElement;
import ch.uzh.softwareengineering.climatechangeviewer.client.DataFileCorruptedException;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterOverflowException;
import ch.uzh.softwareengineering.climatechangeviewer.client.MapView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class QueryServiceImpl extends RemoteServiceServlet implements QueryService {
	
	private static final long serialVersionUID = -5976562964019605869L;
	
	public static final String CSV_FILE_LOCATION = "data/GlobalLandTemperaturesByMajorCity_v1.csv";

	// Latest and oldest year with 12 months in the data file.
	public static final int LATEST_YEAR_IN_DATAFILE = 2012;
	public static final int OLDEST_YEAR_IN_DATAFILE = 1745;
	
	private boolean dataFileCorrupted = false;
	private boolean dataFileChecked = false;
	private boolean dataFileLinesCalculated = false;
	
	private List<DataFileLine> dataFileLines = new ArrayList<DataFileLine>();
	
	public List<TableDataElement> getTableData(String cityQuery, String countryQuery, int year1Query, int year2Query, int monthQuery,
			double minTemperatureQuery, double maxTemperatureQuery, double uncertaintyQuery) 
			throws FilterOverflowException, NoEntriesFoundException, DataFileCorruptedException {
		List<TableDataElement> tableData = new ArrayList<TableDataElement>();
		
//		if(isDataFileCorrupted()) {
//			throw new DataFileCorruptedException();
//		}
		
		if(!dataFileLinesCalculated) {
			dataFileLines = DataFileReader.getDataLines(CSV_FILE_LOCATION);
			dataFileLinesCalculated = true;
		}
       
        for(DataFileLine dataFileLine: dataFileLines) {
    		int year = dataFileLine.getYear();
    		int month = dataFileLine.getMonth();
    		double temperature = dataFileLine.getTemperature();
    		double uncertainty = dataFileLine.getUncertainty();
    		String city = dataFileLine.getCity();
    		String country = dataFileLine.getCountry();
    		
    		if(QueryChecker.checkDateQuery(year, month, year1Query, year2Query, monthQuery)
    				&& QueryChecker.checkCityQuery(city, cityQuery)
    				&& QueryChecker.checkCountryQuery(country, countryQuery)
    				&& QueryChecker.checkUncertaintyQuery(uncertainty, uncertaintyQuery)
    				&& QueryChecker.checkTemperatureQuery(temperature, minTemperatureQuery, maxTemperatureQuery)) {
    			addTableDataElement(tableData, year, month, temperature, uncertainty, city, country);
            }
        }
        
        // Only send data to the client if maxDataLinesToSend is not exceeded.
		if(tableData.size() > TableView.MAX_DATA_LINES_TO_SEND) {
			throw new FilterOverflowException();
		} else if(tableData.size() == 0) {
			throw new NoEntriesFoundException();
		} else {
			return tableData;
		}
	}
	
	private void addTableDataElement(List<TableDataElement> tableData, int year, int month, double temperature,
			double uncertainty, String city, String country) {
		TableDataElement dataElement = new TableDataElement();

		dataElement.setCity(city);
		dataElement.setCountry(country);
    	dataElement.setYear(year);
    	dataElement.setMonth(month);
    	dataElement.setTemperature(temperature);
    	dataElement.setUncertainty(uncertainty);
    	
    	tableData.add(dataElement);
	}
	
	public List<MapDataElement> getMapData(int period1StartQuery , int period2StartQuery, double uncertaintyQuery)
			throws NoEntriesFoundException, DataFileCorruptedException {
//		if(isDataFileCorrupted()) {
//			throw new DataFileCorruptedException();
//		}
		
		if(!dataFileLinesCalculated) {
			dataFileLines = DataFileReader.getDataLines(CSV_FILE_LOCATION);
			dataFileLinesCalculated = true;
		}

		List<CityYearTemperature> cityYearTemperatures = CityYearTemperatureCalculator
				.calculateCityYearTemperatures(dataFileLines, uncertaintyQuery);
		List<MapDataElement> mapData = new ArrayList<MapDataElement>();
		
		for(int i = 0; i < cityYearTemperatures.size(); i++) {
			double aggregatedTemperaturePeriod1 = 0;
			double aggregatedTemperaturePeriod2 = 0;
			double averageTemperaturePeriod1 = Double.MAX_VALUE;
			double averageTemperaturePeriod2 = Double.MAX_VALUE;
			double aggregatedUncertaintyPeriod1 = 0;
			double aggregatedUncertaintyPeriod2 = 0;
			double averageUncertaintyPeriod1 = Double.MAX_VALUE;
			double averageUncertaintyPeriod2 = Double.MAX_VALUE;
			int validYearsPeriod1 = 0;
			int validYearsPeriod2 = 0;
			
			do {
				if(cityYearTemperatures.get(i).getTemperature() < Double.MAX_VALUE) {
					if(cityYearTemperatures.get(i).getYear() >= period1StartQuery
							&& cityYearTemperatures.get(i).getYear() < period1StartQuery + MapView.COMPARISON_PERIOD_LENGTH) {
						aggregatedTemperaturePeriod1 += cityYearTemperatures.get(i).getTemperature();
						aggregatedUncertaintyPeriod1 += cityYearTemperatures.get(i).getUncertainty();
						validYearsPeriod1++;
					} else if (cityYearTemperatures.get(i).getYear() >= period2StartQuery
							&& cityYearTemperatures.get(i).getYear() < period2StartQuery + MapView.COMPARISON_PERIOD_LENGTH) {
						aggregatedTemperaturePeriod2 += cityYearTemperatures.get(i).getTemperature();
						aggregatedUncertaintyPeriod2 += cityYearTemperatures.get(i).getUncertainty();
						validYearsPeriod2++;
					}
				}
				i++;
					
			} while(i < cityYearTemperatures.size()
					&& cityYearTemperatures.get(i-1).getCity().equals(cityYearTemperatures.get(i).getCity()));
			
			// The next for-loop will increase 'i' by 1. If 'i' is not decreased again by 1 at the end of the
			// do-while-loop the first year of the next city in the 'cityYearTemperatures' will be left out.
			i--;
			
			if(validYearsPeriod1 > 0) {
				averageTemperaturePeriod1 = aggregatedTemperaturePeriod1 / validYearsPeriod1;
				averageUncertaintyPeriod1 = aggregatedUncertaintyPeriod1 / validYearsPeriod1;
			}
			if(validYearsPeriod2 > 0) {
				averageTemperaturePeriod2 = aggregatedTemperaturePeriod2 / validYearsPeriod2;
				averageUncertaintyPeriod2 = aggregatedUncertaintyPeriod2 / validYearsPeriod2;
			}
			
			addMapDataElement(mapData, cityYearTemperatures.get(i-1).getCity(), cityYearTemperatures.get(i-1).getLatitude(),
					cityYearTemperatures.get(i-1).getLongitude(), averageTemperaturePeriod1, averageTemperaturePeriod2,
					averageUncertaintyPeriod1, averageUncertaintyPeriod2, validYearsPeriod1, validYearsPeriod2, 
					period1StartQuery, period2StartQuery);	
		}
		return mapData;
	}
	
	private void addMapDataElement(List<MapDataElement> mapData, String city, double latitude, double longitude,
			double temperaturePeriod1, double temperaturePeriod2, double uncertaintyPeriod1, double uncertaintyPeriod2, 
			int validYearsPeriod1, int validYearsPeriod2, int period1Start, int period2Start) {
		MapDataElement dataElement = new MapDataElement();
		
		dataElement.setCity(city);
		dataElement.setPeriod1Start(period1Start);
		dataElement.setPeriod2Start(period2Start);
		dataElement.setTemperaturePeriod1(temperaturePeriod1);
		dataElement.setTemperaturePeriod2(temperaturePeriod2);
		dataElement.setUncertaintyPeriod1(uncertaintyPeriod1);
		dataElement.setUncertaintyPeriod2(uncertaintyPeriod2);
		dataElement.setValidYearsPeriod1(validYearsPeriod1);
		dataElement.setValidYearsPeriod2(validYearsPeriod2);
		dataElement.setLatitude(latitude);
		dataElement.setLongitude(longitude);
		
		mapData.add(dataElement);
	}
	
	public boolean isDataFileCorrupted() {
		if(!dataFileChecked) {
			if(DataFileCorruptionChecker.checkDataFileCorruption(CSV_FILE_LOCATION)) {
				dataFileCorrupted = true;
			} else {
				dataFileCorrupted = false;
			}
		}
		return dataFileCorrupted;
	}
	
}
