package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryServiceValidityCheckerTestCase {

	@Test
	public void testCheckNameString() {
		QueryServiceValidityChecker checker = new QueryServiceValidityChecker();
		
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
		QueryServiceValidityChecker checker = new QueryServiceValidityChecker();
		
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
	public void testCheckTemperatureString() {
		QueryServiceValidityChecker checker = new QueryServiceValidityChecker();
		
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
		QueryServiceValidityChecker checker = new QueryServiceValidityChecker();
		
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

}
