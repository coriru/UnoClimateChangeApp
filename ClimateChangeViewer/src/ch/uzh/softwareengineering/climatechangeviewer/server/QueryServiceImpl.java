package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.City;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterException;
import ch.uzh.softwareengineering.climatechangeviewer.client.QueryService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class QueryServiceImpl extends RemoteServiceServlet implements QueryService {
		
		public List<City> getData(int month, int year1, int year2, String country, String city,
				float minTemperature, float maxTemperature, float maxTemperatureUncertainty) throws FilterException {

		List<City> data = new ArrayList<City>();
		int maxDataLinesToSend = 1000;
		String csvFile = "data/GlobalLandTemperaturesByMajorCity_v1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String dateSplitBy = "-";
       
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // Use comma as separator.
                String[] values = line.split(cvsSplitBy);
                String[] date = values[0].split(dateSplitBy);
                
//                if(((month == -1 || (Integer.parseInt(date[1]) == month)) && ((year1 == -1 || (Integer.parseInt(date[0]) >= year1)) && (year2 == -1 || (Integer.parseInt(date[0]) <= year2))))
//                		&& ((minTemperature == (float) 0.0 || (Float.parseFloat(values[1]) >= minTemperature)) && (maxTemperature == (float) 0.0 || (Float.parseFloat(values[1]) <= maxTemperature)))
//                		&& (maxTemperatureUncertainty == (float) 0.0 || (Float.parseFloat(values[2]) >= maxTemperatureUncertainty))
//                		&& (city.equals("") || values[3].toUpperCase().equals(city.toUpperCase()))
//                		&& (country.equals("") || values[4].toUpperCase().equals(country.toUpperCase()))) {
                
              if(values[0].equals("2000-01-01")) {
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
        
        // Only send the data to client if maxDataLinesToSend is not exceeded.
		if(data.size() > maxDataLinesToSend) {
			throw new FilterException();
		} else {
			return data;
		}

	}		
}


