package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataFileValidityCheckerTestCase {

	@Test
	public void testCheckNameString() {
		String name1 = "TEST";
		String name2 = "TEST TEST";
		String name3 = "TeSt";
		String name4 = "test test";
		String name5 = " ";
		String name6 = "6";
		String name7 = " 7 ";
		String name8 = "TEST2";
		String name9 = "";
		String name10 = "@";
		String name11 = "TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTS"; // 41 chars
		String name12 = "wéÈ Üö";
		String name13 = null;
		
		assertTrue(DataFileValidityChecker.checkNameString(name1));
		assertTrue(DataFileValidityChecker.checkNameString(name2));
		assertTrue(DataFileValidityChecker.checkNameString(name3));
		assertTrue(DataFileValidityChecker.checkNameString(name4));
		assertTrue(DataFileValidityChecker.checkNameString(name5));
		assertFalse(DataFileValidityChecker.checkNameString(name6));
		assertFalse(DataFileValidityChecker.checkNameString(name7));
		assertFalse(DataFileValidityChecker.checkNameString(name8));
		assertFalse(DataFileValidityChecker.checkNameString(name9));
		assertFalse(DataFileValidityChecker.checkNameString(name10));
		assertFalse(DataFileValidityChecker.checkNameString(name11));
		assertTrue(DataFileValidityChecker.checkNameString(name12));
		assertFalse(DataFileValidityChecker.checkNameString(name13));
	}
	
	@Test
	public void testCheckDateString() {
		String date1 = "2000-01-01";
		String date2 = "1";
		String date3 = "2-01-01";
		String date4 = "3333-2-01";
		String date5 = "1000-12-01";
		String date6 = "2000-10-10";
		String date7 = "-01-01";
		String date8 = "2 000-01-01";
		String date9 = "--01";
		String date10 = "@";
		String date11 = " ";
		String date12 = "";
		String date13 = null;
		
		assertTrue(DataFileValidityChecker.checkDateString(date1));
		assertFalse(DataFileValidityChecker.checkDateString(date2));
		assertFalse(DataFileValidityChecker.checkDateString(date3));
		assertFalse(DataFileValidityChecker.checkDateString(date4));
		assertTrue(DataFileValidityChecker.checkDateString(date5));
		assertFalse(DataFileValidityChecker.checkDateString(date6));
		assertFalse(DataFileValidityChecker.checkDateString(date7));
		assertFalse(DataFileValidityChecker.checkDateString(date8));
		assertFalse(DataFileValidityChecker.checkDateString(date9));
		assertFalse(DataFileValidityChecker.checkDateString(date10));
		assertFalse(DataFileValidityChecker.checkDateString(date11));
		assertFalse(DataFileValidityChecker.checkDateString(date12));
		assertFalse(DataFileValidityChecker.checkDateString(date13));
	}
	
	@Test
	public void testCheckYearString() {
		String year1 = "";
		String year2 = " ";
		String year3 = "0";
		String year4 = "1";
		String year5 = "1";
		String year6 = "22";
		String year7 = "333";
		String year8 = "4444";
		String year9 = "9999";
		String year10 = "0199";
		String year11 = "01990";
		String year12 = "2000";
		String year13 = null;
			
		assertFalse(DataFileValidityChecker.checkYearString(year1));
		assertFalse(DataFileValidityChecker.checkYearString(year2));
		assertFalse(DataFileValidityChecker.checkYearString(year3));
		assertFalse(DataFileValidityChecker.checkYearString(year4));
		assertFalse(DataFileValidityChecker.checkYearString(year5));
		assertFalse(DataFileValidityChecker.checkYearString(year6));
		assertFalse(DataFileValidityChecker.checkYearString(year7));
		assertTrue(DataFileValidityChecker.checkYearString(year8));
		assertTrue(DataFileValidityChecker.checkYearString(year9));
		assertFalse(DataFileValidityChecker.checkYearString(year10));
		assertFalse(DataFileValidityChecker.checkYearString(year11));
		assertTrue(DataFileValidityChecker.checkYearString(year12));
		assertFalse(DataFileValidityChecker.checkYearString(year13));
	}
	
	@Test
	public void testCheckTemperatureString() {
		String temperature1 = "";
		String temperature2 = " ";
		String temperature3 = "0";
		String temperature4 = "-1";
		String temperature5 = "0.1";
		String temperature6 = "-0.1";
		String temperature7 = ".1";
		String temperature8 = "-.1";
		String temperature9 = "22.312";
		String temperature10 = "-22.312";
		String temperature11 = "22.3124";
		String temperature12 = "122.312";
		String temperature13 = "1225";
		String temperature14 = ".1225";
		String temperature15 = "0,1";
		String temperature16 = "a.1";
		String temperature17 = "test";
		String temperature18 = "test.1";
		String temperature19 = "t.t";
		String temperature20 = "@";
		String temperature21 = null;
			
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature1));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature2));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature3));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature4));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature5));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature6));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature7));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature8));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature9));
		assertTrue(DataFileValidityChecker.checkTemperatureString(temperature10));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature11));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature12));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature13));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature14));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature15));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature16));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature17));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature18));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature19));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature20));
		assertFalse(DataFileValidityChecker.checkTemperatureString(temperature21));
	}
	
	@Test
	public void testCheckUncertaintyString() {
		String uncertainty1 = "";
		String uncertainty2 = " ";
		String uncertainty3 = "0";
		String uncertainty4 = "-1";
		String uncertainty5 = "0.1";
		String uncertainty6 = "-0.1";
		String uncertainty7 = ".1";
		String uncertainty8 = "-.1";
		String uncertainty9 = "22.312";
		String uncertainty10 = "-22.312";
		String uncertainty11 = "22.3124";
		String uncertainty12 = "122.312";
		String uncertainty13 = "1225";
		String uncertainty14 = ".1225";
		String uncertainty15 = "0,1";
		String uncertainty16 = "a.1";
		String uncertainty17 = "test";
		String uncertainty18 = "test.1";
		String uncertainty19 = "t.t";
		String uncertainty20 = "@";
		String uncertainty21 = null;

		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty1));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty2));
		assertTrue(DataFileValidityChecker.checkUncertaintyString(uncertainty3));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty4));
		assertTrue(DataFileValidityChecker.checkUncertaintyString(uncertainty5));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty6));
		assertTrue(DataFileValidityChecker.checkUncertaintyString(uncertainty7));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty8));
		assertTrue(DataFileValidityChecker.checkUncertaintyString(uncertainty9));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty10));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty11));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty12));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty13));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty14));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty15));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty16));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty17));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty18));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty19));
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty20));	
		assertFalse(DataFileValidityChecker.checkUncertaintyString(uncertainty21));	
	}
	
	@Test
	public void testCheckLatitudeString() {
		String latitude1 = "";
		String latitude2 = " ";
		String latitude3 = "0";
		String latitude4 = "0.0";
		String latitude5 = ".35S";
		String latitude6 = ".35N";
		String latitude7 = "093.035N";
		String latitude8 = "1.035S";
		String latitude9 = "12.035N";
		String latitude10 = "1240";
		String latitude11 = "1240S";
		String latitude12 = "124S";
		String latitude13 = "0N";
		String latitude14 = null;

		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude1));
		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude2));
		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude3));
		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude4));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude5));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude6));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude7));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude8));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude9));
		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude10));
		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude11));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude12));
		assertTrue(DataFileValidityChecker.checkLatitudeString(latitude13));
		assertFalse(DataFileValidityChecker.checkLatitudeString(latitude14));
	}
	
	@Test
	public void testCheckLongitudeString() {
		String longitude1 = "";
		String longitude2 = " ";
		String longitude3 = "0";
		String longitude4 = "0.0";
		String longitude5 = ".35W";
		String longitude6 = ".35E";
		String longitude7 = "093.035E";
		String longitude8 = "1.035W";
		String longitude9 = "12.035E";
		String longitude10 = "1240";
		String longitude11 = "1240W";
		String longitude12 = "124W";
		String longitude13 = "0E";
		String longitude14 = null;

		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude1));
		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude2));
		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude3));
		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude4));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude5));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude6));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude7));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude8));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude9));
		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude10));
		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude11));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude12));
		assertTrue(DataFileValidityChecker.checkLongitudeString(longitude13));
		assertFalse(DataFileValidityChecker.checkLongitudeString(longitude14));
	}

}
