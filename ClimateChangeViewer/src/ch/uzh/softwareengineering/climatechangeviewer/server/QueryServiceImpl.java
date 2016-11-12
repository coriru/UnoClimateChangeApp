package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.City;
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
	private static final int MAX_DATA_LINES_TO_SEND = 1000;	
	
	public List<City> getData(int month, int year1, int year2, String country, String city, 
			float minTemperature, float maxTemperature, float maxTemperatureUncertainty) throws FilterOverflowException, NoEntriesFoundException {
		List<City> data = new ArrayList<City>();
		
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
            		int yearInLine = Integer.parseInt(date[0]);
            		int monthInLine = Integer.parseInt(date[1]);
            		float temperatureInLine = Float.parseFloat(values[1]);
            		float temperatureUncertaintyInLine = Float.parseFloat(values[2]);
                    
                    //TODO Create function to check filter values.
            		if(
            				((month == 0 || month == monthInLine)
            				&& ((year1 == Integer.MAX_VALUE || yearInLine >= year1)
            				&& (year2 == Integer.MAX_VALUE || yearInLine <= year2)))
            				&& (city.equals("") || city.equalsIgnoreCase(values[3].toUpperCase()))
            				&& (country.equals("") || country.equalsIgnoreCase(values[4].toUpperCase()))
            				&& (maxTemperatureUncertainty >= Float.MAX_VALUE  || temperatureUncertaintyInLine <= maxTemperatureUncertainty)
            				&& ((minTemperature >= Float.MAX_VALUE || temperatureInLine >= minTemperature) && (maxTemperature >= Float.MAX_VALUE || temperatureInLine <= maxTemperature))
            		  )
            		{
            			City dataElement = new City();
                    	dataElement.setDate(values[0]);
                    	dataElement.setAverageTemperature(values[1]);
                    	dataElement.setAverageTemperatureUncertainty(values[2]);
                    	dataElement.setCityName(values[3]);
                    	dataElement.setCountry(values[4]);
                    	dataElement.setLatitude(values[5]);
                    	dataElement.setLongitude(values[6]);
                    	
                    	data.add(dataElement);
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
}


