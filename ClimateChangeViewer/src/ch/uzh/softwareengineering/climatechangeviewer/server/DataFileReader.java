package ch.uzh.softwareengineering.climatechangeviewer.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFileReader {
	
	public static List<DataFileLine> getCSVDataLineObjects(String csvFileLocation) {	
		List<DataFileLine> dataLineObjects = new ArrayList<DataFileLine>();
		
		BufferedReader br = null;
	    String line = "";
	    String csvSplitBy = ",";
	    String dateSplitBy = "-";
	
	    try {
	        br = new BufferedReader(new FileReader(csvFileLocation));
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
	        		float latidue = CoordinatesParser.parseLatitude(values[5]);
	        		float longitude = CoordinatesParser.parseLongitude(values[6]);
	        		
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
	
	private static void addCSVDataLineObject(List<DataFileLine> data, int year, int month, float temperature,
			float uncertainty, String city, String country, float latitude, float longitude) {
		DataFileLine dataLineObject = new DataFileLine();
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
}
