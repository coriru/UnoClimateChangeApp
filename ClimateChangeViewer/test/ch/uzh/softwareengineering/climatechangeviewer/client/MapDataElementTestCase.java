package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MapDataElementTestCase extends GWTTestCase {

	@Test
	public void testGetTemperaturePeriod1String() {
		MapDataElement dataElement = new MapDataElement();
		
		float temperature1 = 12.123f;
		dataElement.setTemperaturePeriod1(temperature1);
		assertTrue(dataElement.getTemperaturePeriod1String().equals("12.123"));
		
		float temperature2 = 1f;
		dataElement.setTemperaturePeriod1(temperature2);
		assertTrue(dataElement.getTemperaturePeriod1String().equals("1.000"));
		
		float temperature3 = -0.001f;
		dataElement.setTemperaturePeriod1(temperature3);
		assertTrue(dataElement.getTemperaturePeriod1String().equals("-0.001"));
	}
	
	@Test
	public void testGetTemperaturePeriod2String() {
		MapDataElement dataElement = new MapDataElement();
		
		float temperature1 = 12.123f;
		dataElement.setTemperaturePeriod2(temperature1);
		assertTrue(dataElement.getTemperaturePeriod2String().equals("12.123"));
		
		float temperature2 = 1f;
		dataElement.setTemperaturePeriod2(temperature2);
		assertTrue(dataElement.getTemperaturePeriod2String().equals("1.000"));
		
		float temperature3 = -0.001f;
		dataElement.setTemperaturePeriod2(temperature3);
		assertTrue(dataElement.getTemperaturePeriod2String().equals("-0.001"));
	}
	
	@Test
	public void testGetUncertaintyPeriod1String() {
		MapDataElement dataElement = new MapDataElement();
		
		float uncertainty1 = 12.123f;
		dataElement.setUncertaintyPeriod1(uncertainty1);
		assertTrue(dataElement.getUncertaintyPeriod1String().equals("12.123"));
		
		float uncertainty2 = 1f;
		dataElement.setUncertaintyPeriod1(uncertainty2);
		assertTrue(dataElement.getUncertaintyPeriod1String().equals("1.000"));
		
		float uncertainty3 = 0.001f;
		dataElement.setUncertaintyPeriod1(uncertainty3);
		assertTrue(dataElement.getUncertaintyPeriod1String().equals("0.001"));
	}
	
	@Test
	public void testGetUncertaintyPeriod2String() {
		MapDataElement dataElement = new MapDataElement();
		
		float uncertainty1 = 12.123f;
		dataElement.setUncertaintyPeriod2(uncertainty1);
		assertTrue(dataElement.getUncertaintyPeriod2String().equals("12.123"));
		
		float uncertainty2 = 1f;
		dataElement.setUncertaintyPeriod2(uncertainty2);
		assertTrue(dataElement.getUncertaintyPeriod2String().equals("1.000"));
		
		float uncertainty3 = 0.001f;
		dataElement.setUncertaintyPeriod2(uncertainty3);
		assertTrue(dataElement.getUncertaintyPeriod2String().equals("0.001"));
	}
	
	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
	
}
