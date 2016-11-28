package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class TableDataElementTestCase extends GWTTestCase {
	
	@Test
	public void testGetDate() {
		TableDataElement dataElement = new TableDataElement();
		int month = 1;
		int year = 2000;
		dataElement.setMonth(month);
		dataElement.setYear(year);
		
		assertTrue(dataElement.getDate().equals("2000, January"));
		
		month = -2251;
		year = 2000;
		dataElement.setMonth(month);
		dataElement.setYear(year);
		
		assertTrue(dataElement.getDate().equals("2000, Invalid month"));
	}

	@Test
	public void testGetTemperatureString() {
		TableDataElement dataElement = new TableDataElement();
		
		float temperature1 = 12.123f;
		dataElement.setTemperature(temperature1);
		assertTrue(dataElement.getTemperatureString().equals("12.123"));
		
		float temperature2 = 1f;
		dataElement.setTemperature(temperature2);
		assertTrue(dataElement.getTemperatureString().equals("1.000"));
		
		float temperature3 = 0.001f;
		dataElement.setTemperature(temperature3);
		assertTrue(dataElement.getTemperatureString().equals("0.001"));
	}
	
	@Test
	public void testGetTemperatureUncertaintyString() {
		TableDataElement dataElement = new TableDataElement();
		
		float temperatureUncertainty1 = 12.123f;
		dataElement.setUncertainty(temperatureUncertainty1);
		System.out.println(dataElement.getUncertaintyString());
		assertTrue(dataElement.getUncertaintyString().equals("12.123"));
		
		float temperatureUncertainty2 = 1f;
		dataElement.setUncertainty(temperatureUncertainty2);
		assertTrue(dataElement.getUncertaintyString().equals("1.000"));
		
		float temperatureUncertainty3 = 0.01f;
		dataElement.setUncertainty(temperatureUncertainty3);
		assertTrue(dataElement.getUncertaintyString().equals("0.010"));
	}
	
	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
	
}
