package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CityYearTemperatureCalculatorTestCase {

	@Test
	public void testCalculateCityYearTemperatures() {
		List<DataFileLine> dataFileLines = new ArrayList<DataFileLine>();
		List<CityYearTemperature> result;
				
		// Insert test data of 12 valid months of a single year into dataFileLines.
		float expectedTemperatureResult1 = 0;
		float expectedUncertaintyResult1 = 0;
		for(int i = 0; i < 12; i++) {
			DataFileLine dataFileLine = new DataFileLine();
			dataFileLine.setYear(2000);
			dataFileLine.setMonth(i+1);
			dataFileLine.setCity("TestCity");
			dataFileLine.setCountry("TestCountry");
			dataFileLine.setLatitude(0);
			dataFileLine.setLongitude(0);
			dataFileLine.setTemperature(i);
			dataFileLine.setUncertainty(0);
			dataFileLines.add(dataFileLine);
			expectedTemperatureResult1 += i;
		}
		expectedTemperatureResult1 = expectedTemperatureResult1 / 12;
		
		// Insert test data of 12 invalid months of a single year into dataFileLines (exceeding MAX_VALID_UNCERTAINTY).
		for(int i = 0; i < 12; i++) {
			DataFileLine dataFileLine = new DataFileLine();
			dataFileLine.setYear(2001);
			dataFileLine.setMonth(i+1);
			dataFileLine.setCity("TestCity2");
			dataFileLine.setCountry("TestCountry2");
			dataFileLine.setLatitude(10);
			dataFileLine.setLongitude(10);
			dataFileLine.setTemperature(i + 10);
			dataFileLine.setUncertainty(1.01f);
			dataFileLines.add(dataFileLine);
		}
		float expectedTemperatureResult2 = Float.MAX_VALUE;
		float expectedUncertaintyResult2 = Float.MAX_VALUE;
		
		// Insert test data of only 11 valid months of a single year into dataFileLines.
		for(int i = 0; i < 11; i++) {
			DataFileLine dataFileLine = new DataFileLine();
			dataFileLine.setYear(2002);
			dataFileLine.setMonth(i+1);
			dataFileLine.setCity("TestCity3");
			dataFileLine.setCountry("TestCountry3");
			dataFileLine.setLatitude(20);
			dataFileLine.setLongitude(20);
			dataFileLine.setTemperature(i + 20);
			dataFileLine.setUncertainty(0);
			dataFileLines.add(dataFileLine);
		}
		float expectedTemperatureResult3 = Float.MAX_VALUE;
		float expectedUncertaintyResult3 = Float.MAX_VALUE;
		
		// Calculate the actual result based on the created test data.
		result = CityYearTemperatureCalculator.calculateCityYearTemperatures(dataFileLines,
				QueryServiceImpl.CSV_FILE_LOCATION, 1.0f);
		
		assertEquals(expectedTemperatureResult1, result.get(0).getTemperature(), 0.001f);
		assertEquals(expectedUncertaintyResult1, result.get(0).getUncertainty(), 0.001f);
		assertEquals(expectedTemperatureResult2, result.get(1).getTemperature(), 0.001f);
		assertEquals(expectedUncertaintyResult2, result.get(1).getUncertainty(), 0.001f);
		assertEquals(expectedTemperatureResult3, result.get(2).getTemperature(), 0.001f);
		assertEquals(expectedUncertaintyResult3, result.get(2).getUncertainty(), 0.001f);		
	}
}
