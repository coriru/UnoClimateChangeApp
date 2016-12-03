package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class TableDataElementTestCase extends GWTTestCase {
	
	@Test
	public void testGetDate() {
		TableDataElement dataElement = new TableDataElement();
		
		// Test output with valid data.
		int month = 1;
		int year = 2000;
		dataElement.setMonth(month);
		dataElement.setYear(year);
		assertTrue(dataElement.getDate().equals("2000, January"));
		
		// Test output with invalid month.
		month = 13;
		year = 2000;
		dataElement.setMonth(month);
		dataElement.setYear(year);
		assertTrue(dataElement.getDate().equals("2000, Invalid"));
	}
	
	@Test
	public void testGetDateForStringSorting() {
		TableDataElement dataElement = new TableDataElement();
		
		// Test output with valid data.
		int year1 = 2000;
		int month1 = 1;
		dataElement.setYear(year1);
		dataElement.setMonth(month1);
		assertTrue(dataElement.getDateForStringSorting().equals("2000-01"));
		
		// Test output with valid data.
		int year2 = 2002;
		int month2 = 12;
		dataElement.setYear(year2);
		dataElement.setMonth(month2);
		assertTrue(dataElement.getDateForStringSorting().equals("2002-12"));
		
		// Test output with invalid month.
		int year3 = 2002;
		int month3 = 13;
		dataElement.setYear(year3);
		dataElement.setMonth(month3);
		assertTrue(dataElement.getDateForStringSorting().equals("2002-00"));
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
		System.out.println(dataElement.getUncertaintyString());
		assertTrue(dataElement.getUncertaintyString().equals("12.123"));
		
		double temperatureUncertainty2 = 1f;
		dataElement.setUncertainty(temperatureUncertainty2);
		assertTrue(dataElement.getUncertaintyString().equals("1.000"));
		
		double temperatureUncertainty3 = 0.01f;
		dataElement.setUncertainty(temperatureUncertainty3);
		assertTrue(dataElement.getUncertaintyString().equals("0.010"));
	}
	
	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
	
}
