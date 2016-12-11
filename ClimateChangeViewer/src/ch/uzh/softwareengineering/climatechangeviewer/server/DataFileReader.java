package ch.uzh.softwareengineering.climatechangeviewer.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataFileReader {
	
	public static List<DataFileLine> getDataLines(String csvFileLocation) {	
		List<DataFileLine> dataFileLines = new ArrayList<DataFileLine>();
		
		BufferedReader br = null;
	    String line = "";
	    String csvSplitBy = ",";
	    String dateSplitBy = "-";
	
	    try {
	        br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFileLocation), "UTF8"));
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
	        		double temperature = Double.parseDouble(values[1]);
	        		double uncertainty = Double.parseDouble(values[2]);
	        		String city = values[3];
	        		String country = values[4];
	        		double latidue = CoordinatesParser.parseLatitude(values[5]);
	        		double longitude = CoordinatesParser.parseLongitude(values[6]);
	        		
	        		//Read the CSV and creates Objects
	        		addDataLine(dataFileLines, year, month, temperature, uncertainty, city, country, latidue, longitude);
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
		return dataFileLines;
	}
	
	private static void addDataLine(List<DataFileLine> dataFileLines, int year, int month, double temperature,
			double uncertainty, String city, String country, double latitude, double longitude) {
		DataFileLine dataFileLine = new DataFileLine();
		dataFileLine.setMonth(month);
		dataFileLine.setYear(year);
		dataFileLine.setTemperature(temperature);
		dataFileLine.setUncertainty(uncertainty);
		dataFileLine.setCity(city);
		dataFileLine.setCountry(country);
		dataFileLine.setLatitude(latitude);
		dataFileLine.setLongitude(longitude);
		
		dataFileLines.add(dataFileLine);
	}
}
