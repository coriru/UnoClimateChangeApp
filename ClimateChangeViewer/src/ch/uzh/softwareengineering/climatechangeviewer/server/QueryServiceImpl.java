package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.TableDataElement;
import ch.uzh.softwareengineering.climatechangeviewer.client.TableView;
import ch.uzh.softwareengineering.climatechangeviewer.client.DataFileCorruptedException;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterOverflowException;
import ch.uzh.softwareengineering.climatechangeviewer.client.MapDataElement;
import ch.uzh.softwareengineering.climatechangeviewer.client.MapView;
import ch.uzh.softwareengineering.climatechangeviewer.client.NoEntriesFoundException;
import ch.uzh.softwareengineering.climatechangeviewer.client.QueryService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class QueryServiceImpl extends RemoteServiceServlet implements QueryService {
	
	private static final long serialVersionUID = -5976562964019605869L;
	
	public static final int MAX_DATA_LINES_TO_SEND = TableView.MAX_DATA_LINES_TO_SEND;
	public static final int COMPARISON_PERIOD_LENGTH = MapView.COMPARISON_PERIOD_LENGTH;
	public static final String CSV_FILE_LOCATION = "data/GlobalLandTemperaturesByMajorCity_v1.csv";
	
	private boolean dataFileCorrupted = false;
	private boolean dataFileChecked = false;
	private boolean cityYearTemperatureCalculated = false;
	
	private List<CityYearTemperature> cityYearTemperatures = new ArrayList<CityYearTemperature>();
	
	public List<TableDataElement> getTableData(int monthQuery, int year1Query, int year2Query, String countryQuery,
			String cityQuery, float minTemperatureQuery, float maxTemperatureQuery, float uncertaintyQuery)
					throws FilterOverflowException, NoEntriesFoundException, DataFileCorruptedException {
		List<TableDataElement> tableData = new ArrayList<TableDataElement>();
		
		if(isDataFileCorrupted()) {
			throw new DataFileCorruptedException();
		}
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        String dateSplitBy = "-";
       
        try {
            br = new BufferedReader(new FileReader(CSV_FILE_LOCATION));
            int lineCounter = 0;
            
            while ((line = br.readLine()) != null) {
            	lineCounter++;
            	
            	// Ignore first line of the file.
            	if(lineCounter > 1) {
            		// Use comma as separator for the values in each line.
            		String[] values = line.split(csvSplitBy);

            		// Use hyphen as separator for the date values
            		String[] date = values[0].split(dateSplitBy);		
            		int year = Integer.parseInt(date[0]);
            		int month = Integer.parseInt(date[1]);
            		float temperature = Float.parseFloat(values[1]);
            		float uncertainty = Float.parseFloat(values[2]);
	        		String city = values[3];
	        		String country = values[4];

            		if(QueryChecker.checkDateQuery(year, month, year1Query, year2Query, monthQuery)
            				&& QueryChecker.checkCityQuery(values[3], cityQuery)
            				&& QueryChecker.checkCountryQuery(values[4], countryQuery)
            				&& QueryChecker.checkUncertaintyQuery(uncertainty, uncertaintyQuery)
            				&& QueryChecker.checkTemperatureQuery(temperature, minTemperatureQuery, maxTemperatureQuery)) {
            			addTableDataElement(tableData, year, month, temperature, uncertainty, city, country);
                    }
            	}         
            }
      
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Only send data to the client if maxDataLinesToSend is not exceeded.
		if(tableData.size() > MAX_DATA_LINES_TO_SEND) {
			throw new FilterOverflowException();
		} else if(tableData.size() == 0) {
			throw new NoEntriesFoundException();
		} else {
			return tableData;
		}
	}
	
	private void addTableDataElement(List<TableDataElement> tableData, int year, int month, float temperature,
			float uncertainty, String city, String country) {
		TableDataElement dataElement = new TableDataElement();
    	dataElement.setMonth(month);
    	dataElement.setYear(year);
    	dataElement.setTemperature(temperature);
    	dataElement.setUncertainty(uncertainty);
    	dataElement.setCity(city);
    	dataElement.setCountry(country);
    	
    	tableData.add(dataElement);
	}
	
	public List<MapDataElement> getMapData(int comparisonPerdiod1Start , int comparisonPerdiod2Start, float maxUncertaintyQuery)
			throws NoEntriesFoundException, DataFileCorruptedException {
		if(isDataFileCorrupted()) {
			throw new DataFileCorruptedException();
		}
		if(!cityYearTemperatureCalculated) {
			cityYearTemperatures = CityYearTemperatureCalculator.calculateCityYearTemperatures(CSV_FILE_LOCATION, maxUncertaintyQuery);
		}
		List<MapDataElement> mapData = new ArrayList<MapDataElement>();
		
		for(int i = 0; i < cityYearTemperatures.size(); i++) {
			float aggregatedTemperaturePeriod1 = 0;
			float aggregatedTemperaturePeriod2 = 0;
			float averageTemperaturePeriod1 = Float.MAX_VALUE;
			float averageTemperaturePeriod2 = Float.MAX_VALUE;
			float aggregatedUncertaintyPeriod1 = 0;
			float aggregatedUncertaintyPeriod2 = 0;
			float averageUncertaintyPeriod1 = Float.MAX_VALUE;
			float averageUncertaintyPeriod2 = Float.MAX_VALUE;
			int validYearsPeriod1 = 0;
			int validYearsPeriod2 = 0;
			
			do {
				if(cityYearTemperatures.get(i).getTemperature() < Float.MAX_VALUE) {
					if(cityYearTemperatures.get(i).getYear() >= comparisonPerdiod1Start
							&& cityYearTemperatures.get(i).getYear() < comparisonPerdiod1Start + COMPARISON_PERIOD_LENGTH) {
						aggregatedTemperaturePeriod1 += cityYearTemperatures.get(i).getTemperature();
						aggregatedUncertaintyPeriod1 += cityYearTemperatures.get(i).getUncertainty();
						validYearsPeriod1++;
					} else if (cityYearTemperatures.get(i).getYear() >= comparisonPerdiod2Start
							&& cityYearTemperatures.get(i).getYear() < comparisonPerdiod2Start + COMPARISON_PERIOD_LENGTH) {
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
					comparisonPerdiod1Start, comparisonPerdiod2Start);	
		}
		return mapData;
	}
	
	private void addMapDataElement(List<MapDataElement> mapData, String city, float latitude, float longitude,
			float temperaturePeriod1, float temperaturePeriod2, float uncertaintyPeriod1, float uncertaintyPeriod2, 
			int validYearsPeriod1, int validYearsPeriod2, int comparisonPerdiod1Start, int comparisonPerdiod2Start) {
		MapDataElement dataElement = new MapDataElement();
		dataElement.setCity(city);
		dataElement.setLatitude(latitude);
		dataElement.setLongitude(longitude);
		dataElement.setTemperaturePeriod1(temperaturePeriod1);
		dataElement.setTemperaturePeriod2(temperaturePeriod2);
		dataElement.setUncertaintyPeriod1(uncertaintyPeriod1);
		dataElement.setUncertaintyPeriod2(uncertaintyPeriod2);
		dataElement.setValidYearsPeriod1(validYearsPeriod1);
		dataElement.setValidYearsPeriod2(validYearsPeriod2);
		dataElement.setComparisonPeriod1Start(comparisonPerdiod1Start);
		dataElement.setComparisonPeriod2Start(comparisonPerdiod2Start);
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
		if(dataFileCorrupted) {
			return true;
		} else {
			return false;
		}
	}
	
}
