package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.TableDataElement;
import ch.uzh.softwareengineering.climatechangeviewer.client.DataFileCorruptedException;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterOverflowException;
import ch.uzh.softwareengineering.climatechangeviewer.client.MapDataElement;
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
	public static final int MAX_DATA_LINES_TO_SEND = 1000;
	public static final int COMPARISON_PERIOD_LENGTH = 10;
	public static final float MAX_VALID_UNCERTAINTY = 1.0f;
	private static final String CSV_FILE_LOCATION = "data/GlobalLandTemperaturesByMajorCity_v1.csv";
	
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
        String cvsSplitBy = ",";
        String dateSplitBy = "-";
       
        try {
            br = new BufferedReader(new FileReader(CSV_FILE_LOCATION));
            int lineCounter = 0;
            
            while ((line = br.readLine()) != null) {
            	lineCounter++;
            	
            	// Ignore first line of the file.
            	if(lineCounter > 1) {
            		// Use comma as separator for the values in each line.
            		String[] values = line.split(cvsSplitBy);

            		// Use hyphen as separator for the date values
            		String[] date = values[0].split(dateSplitBy);		
            		int year = Integer.parseInt(date[0]);
            		int month = Integer.parseInt(date[1]);
            		float temperature = Float.parseFloat(values[1]);
            		float uncertainty = Float.parseFloat(values[2]);

            		if(checkDateQuery(year, month, year1Query, year2Query, monthQuery)
            				&& checkCityQuery(values[3], cityQuery)
            				&& checkCountryQuery(values[4], countryQuery)
            				&& checkUncertaintyQuery(uncertainty, uncertaintyQuery)
            				&& checkTemperatureQuery(temperature, minTemperatureQuery, maxTemperatureQuery)) {
            			addTableDataElement(tableData, year, month, temperature, uncertainty, values[3], values[4]);
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
	
	private boolean checkDateQuery(int year, int month, int year1Query, int year2Query, int monthQuery) {
		if(monthQuery == 0 || monthQuery == month) {
			if(year1Query == Integer.MIN_VALUE || year >= year1Query) {
				if((year2Query == Integer.MIN_VALUE || year <= year2Query)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkCityQuery(String city, String cityQuery) {
		if(city == null && cityQuery == null) {
			return false;
		}
		if(city.equalsIgnoreCase("") || city.toUpperCase().contains(cityQuery.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	private boolean checkCountryQuery(String country, String countryQuery) {
		if(country == null && countryQuery == null) {
			return false;
		}
		if(country.equalsIgnoreCase("") || country.toUpperCase().contains(countryQuery.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	private boolean checkUncertaintyQuery(float uncertainty, float uncertaintyQuery) {
		if(uncertaintyQuery >= Float.MAX_VALUE  || uncertainty <= uncertaintyQuery) {
			return true;
		}
		return false;
	}
	
	private boolean checkTemperatureQuery(float temperature, float minTemperatureQuery, float maxTemperatureQuery) {
		if(minTemperatureQuery >= Float.MAX_VALUE || temperature >= minTemperatureQuery) {
			if(maxTemperatureQuery >= Float.MAX_VALUE || temperature <= maxTemperatureQuery) {
				return true;
			}
		}
		return false;
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
	
	public List<MapDataElement> getMapData(int comparisonPerdiod1Start , int comparisonPerdiod2Start)
			throws NoEntriesFoundException, DataFileCorruptedException {
		if(isDataFileCorrupted()) {
			throw new DataFileCorruptedException();
		}
		if(!cityYearTemperatureCalculated) {
			calculateCityYearTemperatures();
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

	private void calculateCityYearTemperatures() {
		List<CSVDataLineObject> dataLineObjects = getCSVDataLineObjects();

		for(int i = 0; i < dataLineObjects.size(); i++) {
			float aggregatedTemperature = 0;
			float averageTemperature = Float.MAX_VALUE;
			float aggregatedUncertainty = 0;
			float averageUncertainty = Float.MAX_VALUE;
			int validMonths = 0;
			
			do {
				if(dataLineObjects.get(i).getTemperature() < Float.MAX_VALUE
						&& dataLineObjects.get(i).getUncertainty() <= MAX_VALID_UNCERTAINTY) {
					aggregatedTemperature += dataLineObjects.get(i).getTemperature();
					aggregatedUncertainty += dataLineObjects.get(i).getUncertainty();
					validMonths++;
				}
				i++;
					
			} while(i < dataLineObjects.size() && dataLineObjects.get(i).getYear() == dataLineObjects.get(i-1).getYear());
			
			// Set only valid averages for a year if no month exceeds MAX_VALID_UNCERTAINTY. 
			if(validMonths == 11) {
				averageTemperature = aggregatedTemperature / validMonths;
				averageUncertainty = aggregatedUncertainty / validMonths;
			}
			
			addCityYearTemperature(dataLineObjects.get(i-1).getCity(), dataLineObjects.get(i-1).getLatitude(),
					dataLineObjects.get(i-1).getLongitude(), dataLineObjects.get(i-1).getYear(), averageTemperature,
					averageUncertainty);	
		}
	}

	private void addCityYearTemperature(String city, float latitude, float longitude, int year,
			float temperature, float uncertainty) {
		CityYearTemperature cityYearTemperature = new CityYearTemperature();
		cityYearTemperature.setCity(city);
		cityYearTemperature.setLatitude(latitude);
		cityYearTemperature.setLongitude(longitude);
		cityYearTemperature.setYear(year);
		cityYearTemperature.setTemperature(temperature);
		cityYearTemperature.setUncertainty(uncertainty);
		cityYearTemperatures.add(cityYearTemperature);
	}
	
	private List<CSVDataLineObject> getCSVDataLineObjects() {	
		List<CSVDataLineObject> dataLineObjects = new ArrayList<CSVDataLineObject>();
		
		BufferedReader br = null;
	    String line = "";
	    String cvsSplitBy = ",";
	    String dateSplitBy = "-";
	
	    try {
	        br = new BufferedReader(new FileReader(CSV_FILE_LOCATION));
	        int lineCounter = 0;
	        
	        while ((line = br.readLine()) != null) {
	        	lineCounter++;
	        	
	        	// Ignore first line of the file.
	        	if(lineCounter > 1) {
	        		
	        		// Use comma as separator for the values in each line.
	        		String[] values = line.split(cvsSplitBy);
	
	        		// Use hyphen as separator for the date values
	        		String[] date = values[0].split(dateSplitBy);		
	        		int year = Integer.parseInt(date[0]);
	        		int month = Integer.parseInt(date[1]);
	        		String city = values[3];
	        		String country = values[4];
	        		float latidue = parseLatitude(values[5]);
	        		float longitude = parseLongitude(values[6]);
	        		float temperature = Float.parseFloat(values[1]);
	        		float uncertainty = Float.parseFloat(values[2]);
	        		
	        		//Read the CSV and creates Objects
	        		addCSVDataLineObject(dataLineObjects, year, month, temperature, uncertainty, city, country,
	        				latidue, longitude);
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
		return dataLineObjects;
	}
	
	private void addCSVDataLineObject(List<CSVDataLineObject> data, int year, int month, float temperature,
			float uncertainty, String city, String country, float latitude, float longitude) {
		CSVDataLineObject dataLineObject = new CSVDataLineObject();
		dataLineObject.setMonth(month);
		dataLineObject.setYear(year);
		dataLineObject.setTemperature(temperature);
		dataLineObject.setUncertainty(uncertainty);
		dataLineObject.setCity(city);
		dataLineObject.setCountry(country);
		dataLineObject.setLatitude(latitude);
		dataLineObject.setLongitude(longitude);
		
		data.add(dataLineObject);
	}
	
	private float parseLatitude(String latitudeString) {
		String latitudeDigitsString;
		float latitude = Float.MAX_VALUE;
		
		if(latitudeString.toUpperCase().indexOf('N') >= 0) {
			latitudeDigitsString = latitudeString.substring(0, latitudeString.toUpperCase().indexOf('N'));
			latitude = Float.parseFloat(latitudeDigitsString);
		} else {
			latitudeDigitsString = latitudeString.substring(0, latitudeString.toUpperCase().indexOf('S'));
			latitude = Float.parseFloat(latitudeDigitsString);
			latitude = latitude * (-1);
		}
		return latitude;
	}
	
	private float parseLongitude(String longitudeString) {
		String longitudeDigitsString;
		float longitude = Float.MAX_VALUE;
		
		if(longitudeString.toUpperCase().indexOf('E') >= 0) {
			longitudeDigitsString = longitudeString.substring(0, longitudeString.toUpperCase().indexOf('E'));
			longitude = Float.parseFloat(longitudeDigitsString);
		} else {
			longitudeDigitsString = longitudeString.substring(0, longitudeString.toUpperCase().indexOf('W'));
			longitude = Float.parseFloat(longitudeDigitsString);
			longitude = longitude * (-1);
		}
		return longitude;
	}
	
	public boolean isDataFileCorrupted() {
		if(!dataFileChecked) {
			checkDataFile();
		}
		if(dataFileCorrupted) {
			return true;
		} else {
			return false;
		}
	}
		
	private void checkDataFile() {
		QueryServiceValidityChecker checker = new QueryServiceValidityChecker();
		String csvFile = CSV_FILE_LOCATION;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
		try {
			br = new BufferedReader(new FileReader(csvFile));
			int lineCounter = 0;
			while ((line = br.readLine()) != null) {
				lineCounter++;

				// Ignore first line of the file.
				if(lineCounter > 1) {
					// Use comma as separator for the values in each line.
					String[] values = line.split(cvsSplitBy);
					
					// Check if all values of the line are valid
					if(!checker.checkDateString(values[0])) {
						dataFileCorrupted = true;
					}
					if(!checker.checkTemperatureString(values[1])) {
						dataFileCorrupted = true;
					}
					if(!checker.checkUncertaintyString(values[2])) {
						dataFileCorrupted = true;
					}
					if(!checker.checkNameString(values[3])) {
						dataFileCorrupted = true;
					}
					if(!checker.checkNameString(values[4])) {
						dataFileCorrupted = true;
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
	}
	
}
