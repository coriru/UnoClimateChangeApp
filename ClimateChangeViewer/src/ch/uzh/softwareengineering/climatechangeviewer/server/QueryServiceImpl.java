package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.City;
import ch.uzh.softwareengineering.climatechangeviewer.client.QueryService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class QueryServiceImpl extends RemoteServiceServlet implements QueryService {

	
	public List<City> getData(String valueDate, String valueCountry, String valueCity) {
		List<City> data = new ArrayList<City>();
		
		String csvFile = "data/GlobalLandTemperaturesByMajorCity_v1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
       
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                if(values[0].equals(valueDate) && (valueCity.equals("") || values[3].equals(valueCity)) && (valueCountry.equals("") || values[4].equals(valueCountry))) {
                	City city = new City();
                	city.setDate(values[0]);
                	city.setAverageTemperature(values[1]);
                	city.setAverageTemperatureUncertainty(values[2]);
                	city.setCityName(values[3]);
                	city.setCountry(values[4]);
                	city.setLatitude(values[5]);
                	city.setLongitude(values[6]);
                	data.add(city);
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
		return data;
	}
}

