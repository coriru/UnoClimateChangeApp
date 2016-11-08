package org.tester.server;

import org.tester.client.City;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tester.client.QueryService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


// HALLO!!
// HALLO3!!!
public class QueryServiceImpl extends RemoteServiceServlet implements QueryService {

	
	public List<City> getData(String value) {
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
                if(values[0].equals(value)) {
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
      
        }       
		return data;
	}
}

