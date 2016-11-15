package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.DataElement;
import ch.uzh.softwareengineering.climatechangeviewer.client.DataFileCorruptedException;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterOverflowException;
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
	private static final int MAX_DATA_LINES_TO_SEND = 1000;
	private boolean dataFileCorrupted = false;
	private boolean dataFileAlreadyChecked = false;
	
	public boolean isDataFileCorrupted() {
		if(!dataFileAlreadyChecked) {
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
		String csvFile = "data/GlobalLandTemperaturesByMajorCity_v1.csv";
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
	
	public List<DataElement> getData(int monthQuery, int year1Query, int year2Query, String countryQuery, String cityQuery, 
			float minTemperatureQuery, float maxTemperatureQuery, float uncertaintyQuery)
					throws FilterOverflowException, NoEntriesFoundException, DataFileCorruptedException {
		List<DataElement> data = new ArrayList<DataElement>();
		
		if(isDataFileCorrupted()) {
			throw new DataFileCorruptedException();
		}
		String csvFile = "data/GlobalLandTemperaturesByMajorCity_v1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String dateSplitBy = "-";
       
        try {
            br = new BufferedReader(new FileReader(csvFile));
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
            			addDataElement(data, year, month, temperature, uncertainty, values[3], values[4], values[5], values[6]);
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
		if(data.size() > MAX_DATA_LINES_TO_SEND) {
			throw new FilterOverflowException();
		} else if(data.size() == 0) {
			throw new NoEntriesFoundException();
		} else {
			return data;
		}
	}	
	
	private boolean checkDateQuery(int year, int month, int year1Query, int year2Query, int monthQuery) {
		if(monthQuery == 0 || monthQuery == month) {
			if(year1Query == Integer.MAX_VALUE || year >= year1Query) {
				if((year2Query == Integer.MAX_VALUE || year <= year2Query)) {
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
	
	private void addDataElement(List<DataElement> data, int year, int month, float temperature,
			float uncertainty, String city, String country, String latitude, String longitude) {
		DataElement dataElement = new DataElement();
    	dataElement.setMonth(month);
    	dataElement.setYear(year);
    	dataElement.setTemperature(temperature);
    	dataElement.setTemperatureUncertainty(uncertainty);
    	dataElement.setCity(city);
    	dataElement.setCountry(country);
    	dataElement.setLatitude(latitude);
    	dataElement.setLongitude(longitude);
    	
    	data.add(dataElement);
	}	
}
