package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryCheckerTestCase {

	@Test
	public void testCheckCityQuery() {
		String city1 = "Test";
		String cityQuery1 = "Test";
		String city2 = "Test";
		String cityQuery2 = "es";
		String city3 = "Test";
		String cityQuery3 = "Tests";
		String city4 = "Test";
		String cityQuery4 = "";
		String city5 = null;
		String cityQuery5 = "Test";
		String city6 = "Test";
		String cityQuery6 = null;
		String city7 = null;
		String cityQuery7 = null;
		
		assertTrue(QueryChecker.checkCityQuery(city1, cityQuery1));
		assertTrue(QueryChecker.checkCityQuery(city2, cityQuery2));
		assertFalse(QueryChecker.checkCityQuery(city3, cityQuery3));
		assertTrue(QueryChecker.checkCityQuery(city4, cityQuery4));
		assertFalse(QueryChecker.checkCityQuery(city5, cityQuery5));
		assertFalse(QueryChecker.checkCityQuery(city6, cityQuery6));
		assertFalse(QueryChecker.checkCityQuery(city7, cityQuery7));	
	}
	
	@Test
	public void testCheckCountryQuery() {
		String country1 = "Test";
		String countryQuery1 = "Test";
		String country2 = "Test";
		String countryQuery2 = "es";
		String country3 = "Test";
		String countryQuery3 = "Tests";
		String country4 = "Test";
		String countryQuery4 = "";
		String country5 = null;
		String countryQuery5 = "Test";
		String country6 = "Test";
		String countryQuery6 = null;
		String country7 = null;
		String countryQuery7 = null;
		
		assertTrue(QueryChecker.checkCountryQuery(country1, countryQuery1));
		assertTrue(QueryChecker.checkCountryQuery(country2, countryQuery2));
		assertFalse(QueryChecker.checkCountryQuery(country3, countryQuery3));
		assertTrue(QueryChecker.checkCountryQuery(country4, countryQuery4));
		assertFalse(QueryChecker.checkCountryQuery(country5, countryQuery5));
		assertFalse(QueryChecker.checkCountryQuery(country6, countryQuery6));
		assertFalse(QueryChecker.checkCountryQuery(country7, countryQuery7));	
	}
	
	@Test
	public void testCheckDateQuery() {
		int year1 = 2000;
		int month1 = 12;
		int year1Query1 = 2000;
		int year2Query1 = 2000;
		int monthQuery1 = 12;
		
		int year2 = 2000;
		int month2 = 12;
		int year1Query2 = Integer.MIN_VALUE;
		int year2Query2 = Integer.MIN_VALUE;
		int monthQuery2 = 0;
		
		int year3 = 2000;
		int month3 = 12;
		int year1Query3 = Integer.MIN_VALUE;
		int year2Query3 = 2001;
		int monthQuery3 = 0;
		
		int year4 = 2000;
		int month4 = 12;
		int year1Query4 = 1999;
		int year2Query4 = Integer.MIN_VALUE;
		int monthQuery4 = 12;
		
		int year5 = 2000;
		int month5 = 12;
		int year1Query5 = Integer.MIN_VALUE;
		int year2Query5 = Integer.MIN_VALUE;
		int monthQuery5 = 11;
		
		int year6 = 2000;
		int month6 = 12;
		int year1Query6 = 1900;
		int year2Query6 = 1999;
		int monthQuery6 = 12;
		
		assertTrue(QueryChecker.checkDateQuery(year1, month1, year1Query1, year2Query1, monthQuery1));
		assertTrue(QueryChecker.checkDateQuery(year2, month2, year1Query2, year2Query2, monthQuery2));
		assertTrue(QueryChecker.checkDateQuery(year3, month3, year1Query3, year2Query3, monthQuery3));
		assertTrue(QueryChecker.checkDateQuery(year4, month4, year1Query4, year2Query4, monthQuery4));
		assertFalse(QueryChecker.checkDateQuery(year5, month5, year1Query5, year2Query5, monthQuery5));
		assertFalse(QueryChecker.checkDateQuery(year6, month6, year1Query6, year2Query6, monthQuery6));
	}
	
	@Test
	public void testCheckTemperatureQuery() {
		float temperature1 = 10f;
		float minTemperatureQuery1 = 10f;
		float maxTemperatureQuery1 = 10f;
		
		float temperature2 = 10f;
		float minTemperatureQuery2 = Float.MAX_VALUE;
		float maxTemperatureQuery2 = Float.MAX_VALUE;
		
		float temperature3 = 10f;
		float minTemperatureQuery3 = 10f;
		float maxTemperatureQuery3 = Float.MAX_VALUE;
		
		float temperature4 = 10f;
		float minTemperatureQuery4 = Float.MAX_VALUE;
		float maxTemperatureQuery4 = 10f;
		
		float temperature5 = 10f;
		float minTemperatureQuery5 = 10.001f;
		float maxTemperatureQuery5 = 10.001f;
		
		float temperature6 = 10f;
		float minTemperatureQuery6 = 9.999f;
		float maxTemperatureQuery6 = 9.999f;
		
		assertTrue(QueryChecker.checkTemperatureQuery(temperature1, minTemperatureQuery1, maxTemperatureQuery1));
		assertTrue(QueryChecker.checkTemperatureQuery(temperature2, minTemperatureQuery2, maxTemperatureQuery2));
		assertTrue(QueryChecker.checkTemperatureQuery(temperature3, minTemperatureQuery3, maxTemperatureQuery3));
		assertTrue(QueryChecker.checkTemperatureQuery(temperature4, minTemperatureQuery4, maxTemperatureQuery4));
		assertFalse(QueryChecker.checkTemperatureQuery(temperature5, minTemperatureQuery5, maxTemperatureQuery5));
		assertFalse(QueryChecker.checkTemperatureQuery(temperature6, minTemperatureQuery6, maxTemperatureQuery6));
	}
	
	@Test
	public void testCheckUncertaintyQuery() {
		float uncertainty1 = 10f;
		float maxUncertaintyQuery1 = 10f;
		
		float uncertainty2 = 10f;
		float maxUncertaintyQuery2 = Float.MAX_VALUE;
		
		float uncertainty3 = 10f;
		float maxUncertaintyQuery3 = 10.001f;
		
		float uncertainty4 = 10f;
		float maxUncertaintyQuery4 = 9.999f;
		
		assertTrue(QueryChecker.checkUncertaintyQuery(uncertainty1, maxUncertaintyQuery1));
		assertTrue(QueryChecker.checkUncertaintyQuery(uncertainty2, maxUncertaintyQuery2));
		assertTrue(QueryChecker.checkUncertaintyQuery(uncertainty3, maxUncertaintyQuery3));
		assertFalse(QueryChecker.checkUncertaintyQuery(uncertainty4, maxUncertaintyQuery4));
	}
	
}
