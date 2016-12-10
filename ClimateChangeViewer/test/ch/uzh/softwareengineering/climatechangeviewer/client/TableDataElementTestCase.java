package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import ch.uzh.softwareengineering.climatechangeviewer.shared.TableDataElement;

public class TableDataElementTestCase extends GWTTestCase {
	
	@Test
	public void testGetDate() {
		TableDataElement dataElement = new TableDataElement();
		
		// Test return value with valid data.
		int month = 1;
		int year = 2000;
		dataElement.setMonth(month);
		dataElement.setYear(year);
		assertTrue(dataElement.getDateString().equals("2000, January"));
		
		// Test return value with invalid month.
		month = 13;
		year = 2000;
		dataElement.setMonth(month);
		dataElement.setYear(year);
		assertTrue(dataElement.getDateString().equals("INVALID VALUE"));
	}
	
	@Test
	public void testNumericalDateString() {
		TableDataElement dataElement = new TableDataElement();
		
		// Test return value with valid data.
		int year1 = 2000;
		int month1 = 1;
		dataElement.setYear(year1);
		dataElement.setMonth(month1);
		assertTrue(dataElement.getNumericalDateString().equals("2000-01"));
		
		// Test return value with valid data.
		int year2 = 2002;
		int month2 = 12;
		dataElement.setYear(year2);
		dataElement.setMonth(month2);
		assertTrue(dataElement.getNumericalDateString().equals("2002-12"));
		
		// Test return value with invalid month.
		int year3 = 2002;
		int month3 = 13;
		dataElement.setYear(year3);
		dataElement.setMonth(month3);
		assertTrue(dataElement.getNumericalDateString().equals("2002-00"));
	}

	@Test
	public void testGetTemperatureString() {
		TableDataElement dataElement = new TableDataElement();
		
		double temperature1 = 12.123f;
		dataElement.setTemperature(temperature1);
		assertTrue(dataElement.getTemperatureString().equals("12.123"));
		
		double temperature2 = 1f;
		dataElement.setTemperature(temperature2);
		assertTrue(dataElement.getTemperatureString().equals("1.000"));
		
		double temperature3 = -0.001f;
		dataElement.setTemperature(temperature3);
		assertTrue(dataElement.getTemperatureString().equals("-0.001"));
	}
	
	@Test
	public void testGetTemperatureUncertaintyString() {
		TableDataElement dataElement = new TableDataElement();
		
		double temperatureUncertainty1 = 12.123f;
		dataElement.setUncertainty(temperatureUncertainty1);
		assertTrue(dataElement.getUncertaintyString().equals("12.123"));
		
		double temperatureUncertainty2 = 1f;
		dataElement.setUncertainty(temperatureUncertainty2);
		assertTrue(dataElement.getUncertaintyString().equals("1.000"));
		
		double temperatureUncertainty3 = 0.01f;
		dataElement.setUncertainty(temperatureUncertainty3);
		assertTrue(dataElement.getUncertaintyString().equals("0.010"));
	}
	
	@Test
	public void testGetJoinedString() {
		TableDataElement dataElement = new TableDataElement();
		
		// Test return value if no variable of a TableDataElement has been set.
		String expectedResult = "INVALID VALUE,INVALID VALUE," + Integer.MIN_VALUE + "-00,INVALID VALUE,INVALID VALUE\n";
		assertTrue(dataElement.getJoinedString().equals(expectedResult));
		
		// Test return value if all variables of TableDataElement have been set.
		dataElement.setCity("testCity");
		dataElement.setCountry("testCountry");
		dataElement.setMonth(1);
		dataElement.setYear(2000);
		dataElement.setTemperature(5.003d);
		dataElement.setUncertainty(0.231d);
		
		expectedResult = "testCity,testCountry,2000-01,5.003,0.231\n";
		assertTrue(dataElement.getJoinedString().equals(expectedResult));
	}
	
	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
	
}
