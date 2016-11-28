package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class QueryServiceImplTestCase {

	@Test
	public void testCalculateCityYearTemperatures() {
		QueryServiceImpl queryServiceImpl = new QueryServiceImpl();
		List<CSVDataLineObject> testList = new ArrayList<CSVDataLineObject>();
		List<CityYearTemperature> result;
		
		float expectedResult = 0;
		for(int i = 0; i < 12; i++) {
			CSVDataLineObject testDataLineObject = new CSVDataLineObject();
			testDataLineObject.setYear(2000);
			testDataLineObject.setMonth(i+1);
			testDataLineObject.setCity("TestCity");
			testDataLineObject.setCountry("TestCountry");
			testDataLineObject.setLatitude(0f);
			testDataLineObject.setLongitude(0f);
			testDataLineObject.setTemperature(i);
			testDataLineObject.setUncertainty(0f);
			testList.add(testDataLineObject);
			expectedResult += i;
		}
		
		expectedResult = expectedResult / 12f;
		
		for(int i = 0; i < 12; i++) {
			CSVDataLineObject testDataLineObject = new CSVDataLineObject();
			testDataLineObject.setYear(2001);
			testDataLineObject.setMonth(1);
			testDataLineObject.setCity("TestCity2");
			testDataLineObject.setCountry("TestCountry2");
			testDataLineObject.setLatitude(10f);
			testDataLineObject.setLongitude(10f);
			testDataLineObject.setTemperature(10 * i + 100);
			testDataLineObject.setUncertainty(0f);
			testList.add(testDataLineObject);
		}
		
		result = queryServiceImpl.testCalculateCityYearTemperatures(testList, 5);

		System.out.println("Result: " + result.get(0).getTemperature());
		System.out.println("Expected result: " + expectedResult);
	
	}

}
