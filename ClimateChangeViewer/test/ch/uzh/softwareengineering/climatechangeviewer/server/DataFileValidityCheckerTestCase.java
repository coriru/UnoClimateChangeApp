package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataFileValidityCheckerTestCase {

	@Test
	public void testCheckNameString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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
		
		assertTrue(checker.checkNameString(name1));
		assertTrue(checker.checkNameString(name2));
		assertTrue(checker.checkNameString(name3));
		assertTrue(checker.checkNameString(name4));
		assertTrue(checker.checkNameString(name5));
		assertFalse(checker.checkNameString(name6));
		assertFalse(checker.checkNameString(name7));
		assertFalse(checker.checkNameString(name8));
		assertFalse(checker.checkNameString(name9));
		assertFalse(checker.checkNameString(name10));
		assertFalse(checker.checkNameString(name11));
		assertTrue(checker.checkNameString(name12));
		assertFalse(checker.checkNameString(name13));
	}
	
	@Test
	public void testCheckDateString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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
		
		assertTrue(checker.checkDateString(date1));
		assertFalse(checker.checkDateString(date2));
		assertFalse(checker.checkDateString(date3));
		assertFalse(checker.checkDateString(date4));
		assertTrue(checker.checkDateString(date5));
		assertFalse(checker.checkDateString(date6));
		assertFalse(checker.checkDateString(date7));
		assertFalse(checker.checkDateString(date8));
		assertFalse(checker.checkDateString(date9));
		assertFalse(checker.checkDateString(date10));
		assertFalse(checker.checkDateString(date11));
		assertFalse(checker.checkDateString(date12));
		assertFalse(checker.checkDateString(date13));
	}
	
	@Test
	public void testCheckYearString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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
			
		assertFalse(checker.checkYearString(year1));
		assertFalse(checker.checkYearString(year2));
		assertFalse(checker.checkYearString(year3));
		assertFalse(checker.checkYearString(year4));
		assertFalse(checker.checkYearString(year5));
		assertFalse(checker.checkYearString(year6));
		assertFalse(checker.checkYearString(year7));
		assertTrue(checker.checkYearString(year8));
		assertTrue(checker.checkYearString(year9));
		assertFalse(checker.checkYearString(year10));
		assertFalse(checker.checkYearString(year11));
		assertTrue(checker.checkYearString(year12));
		assertFalse(checker.checkYearString(year13));
	}
	
	@Test
	public void testCheckTemperatureString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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
			
		assertFalse(checker.checkTemperatureString(temperature1));
		assertFalse(checker.checkTemperatureString(temperature2));
		assertTrue(checker.checkTemperatureString(temperature3));
		assertTrue(checker.checkTemperatureString(temperature4));
		assertTrue(checker.checkTemperatureString(temperature5));
		assertTrue(checker.checkTemperatureString(temperature6));
		assertTrue(checker.checkTemperatureString(temperature7));
		assertTrue(checker.checkTemperatureString(temperature8));
		assertTrue(checker.checkTemperatureString(temperature9));
		assertTrue(checker.checkTemperatureString(temperature10));
		assertFalse(checker.checkTemperatureString(temperature11));
		assertFalse(checker.checkTemperatureString(temperature12));
		assertFalse(checker.checkTemperatureString(temperature13));
		assertFalse(checker.checkTemperatureString(temperature14));
		assertFalse(checker.checkTemperatureString(temperature15));
		assertFalse(checker.checkTemperatureString(temperature16));
		assertFalse(checker.checkTemperatureString(temperature17));
		assertFalse(checker.checkTemperatureString(temperature18));
		assertFalse(checker.checkTemperatureString(temperature19));
		assertFalse(checker.checkTemperatureString(temperature20));
		assertFalse(checker.checkTemperatureString(temperature21));
	}
	
	@Test
	public void testCheckUncertaintyString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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

		assertFalse(checker.checkUncertaintyString(uncertainty1));
		assertFalse(checker.checkUncertaintyString(uncertainty2));
		assertTrue(checker.checkUncertaintyString(uncertainty3));
		assertFalse(checker.checkUncertaintyString(uncertainty4));
		assertTrue(checker.checkUncertaintyString(uncertainty5));
		assertFalse(checker.checkUncertaintyString(uncertainty6));
		assertTrue(checker.checkUncertaintyString(uncertainty7));
		assertFalse(checker.checkUncertaintyString(uncertainty8));
		assertTrue(checker.checkUncertaintyString(uncertainty9));
		assertFalse(checker.checkUncertaintyString(uncertainty10));
		assertFalse(checker.checkUncertaintyString(uncertainty11));
		assertFalse(checker.checkUncertaintyString(uncertainty12));
		assertFalse(checker.checkUncertaintyString(uncertainty13));
		assertFalse(checker.checkUncertaintyString(uncertainty14));
		assertFalse(checker.checkUncertaintyString(uncertainty15));
		assertFalse(checker.checkUncertaintyString(uncertainty16));
		assertFalse(checker.checkUncertaintyString(uncertainty17));
		assertFalse(checker.checkUncertaintyString(uncertainty18));
		assertFalse(checker.checkUncertaintyString(uncertainty19));
		assertFalse(checker.checkUncertaintyString(uncertainty20));	
		assertFalse(checker.checkUncertaintyString(uncertainty21));	
	}
	
	@Test
	public void testCheckLatitudeString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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

		assertFalse(checker.checkLatitudeString(latitude1));
		assertFalse(checker.checkLatitudeString(latitude2));
		assertFalse(checker.checkLatitudeString(latitude3));
		assertFalse(checker.checkLatitudeString(latitude4));
		assertTrue(checker.checkLatitudeString(latitude5));
		assertTrue(checker.checkLatitudeString(latitude6));
		assertTrue(checker.checkLatitudeString(latitude7));
		assertTrue(checker.checkLatitudeString(latitude8));
		assertTrue(checker.checkLatitudeString(latitude9));
		assertFalse(checker.checkLatitudeString(latitude10));
		assertFalse(checker.checkLatitudeString(latitude11));
		assertTrue(checker.checkLatitudeString(latitude12));
		assertTrue(checker.checkLatitudeString(latitude13));
		assertFalse(checker.checkLatitudeString(latitude14));
	}
	
	@Test
	public void testCheckLongitudeString() {
		DataFileValidityChecker checker = new DataFileValidityChecker();
		
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

		assertFalse(checker.checkLongitudeString(longitude1));
		assertFalse(checker.checkLongitudeString(longitude2));
		assertFalse(checker.checkLongitudeString(longitude3));
		assertFalse(checker.checkLongitudeString(longitude4));
		assertTrue(checker.checkLongitudeString(longitude5));
		assertTrue(checker.checkLongitudeString(longitude6));
		assertTrue(checker.checkLongitudeString(longitude7));
		assertTrue(checker.checkLongitudeString(longitude8));
		assertTrue(checker.checkLongitudeString(longitude9));
		assertFalse(checker.checkLongitudeString(longitude10));
		assertFalse(checker.checkLongitudeString(longitude11));
		assertTrue(checker.checkLongitudeString(longitude12));
		assertTrue(checker.checkLongitudeString(longitude13));
		assertFalse(checker.checkLongitudeString(longitude14));
	}

}
