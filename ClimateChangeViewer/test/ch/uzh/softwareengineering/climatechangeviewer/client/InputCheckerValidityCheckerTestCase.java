package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class InputCheckerValidityCheckerTestCase extends GWTTestCase {

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
		
		assertTrue(InputValidityChecker.checkNameString(name1));
		assertTrue(InputValidityChecker.checkNameString(name2));
		assertTrue(InputValidityChecker.checkNameString(name3));
		assertTrue(InputValidityChecker.checkNameString(name4));
		assertTrue(InputValidityChecker.checkNameString(name5));
		assertFalse(InputValidityChecker.checkNameString(name6));
		assertFalse(InputValidityChecker.checkNameString(name7));
		assertFalse(InputValidityChecker.checkNameString(name8));
		assertTrue(InputValidityChecker.checkNameString(name9));
		assertFalse(InputValidityChecker.checkNameString(name10));
		assertFalse(InputValidityChecker.checkNameString(name11));
		assertTrue(InputValidityChecker.checkNameString(name12));
		assertTrue(InputValidityChecker.checkNameString(name13));
		assertTrue(InputValidityChecker.checkNameString(name13));
	}
	
	@Test
	public void testCheckYearString() {
		String year1 = "2000";
		String year2 = "1";
		String year3 = "22";
		String year4 = "333";
		String year5 = "4444";
		String year6 = "55555";
		String year7 = "-1";
		String year8 = "2 000";
		String year9 = "two";
		String year10 = "@";
		String year11 = " ";
		String year12 = "";
		String year13 = null;
		
		assertTrue(InputValidityChecker.checkYearString(year1));
		assertFalse(InputValidityChecker.checkYearString(year2));
		assertFalse(InputValidityChecker.checkYearString(year3));
		assertFalse(InputValidityChecker.checkYearString(year4));
		assertTrue(InputValidityChecker.checkYearString(year5));
		assertFalse(InputValidityChecker.checkYearString(year6));
		assertFalse(InputValidityChecker.checkYearString(year7));
		assertFalse(InputValidityChecker.checkYearString(year8));
		assertFalse(InputValidityChecker.checkYearString(year9));
		assertFalse(InputValidityChecker.checkYearString(year10));
		assertFalse(InputValidityChecker.checkYearString(year11));
		assertTrue(InputValidityChecker.checkYearString(year12));
		assertTrue(InputValidityChecker.checkYearString(year13));
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
			
		assertTrue(InputValidityChecker.checkTemperatureString(temperature1));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature2));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature3));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature4));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature5));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature6));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature7));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature8));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature9));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature10));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature11));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature12));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature13));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature14));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature15));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature16));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature17));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature18));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature19));
		assertFalse(InputValidityChecker.checkTemperatureString(temperature20));
		assertTrue(InputValidityChecker.checkTemperatureString(temperature21));
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

		assertTrue(InputValidityChecker.checkUncertaintyString(uncertainty1));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty2));
		assertTrue(InputValidityChecker.checkUncertaintyString(uncertainty3));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty4));
		assertTrue(InputValidityChecker.checkUncertaintyString(uncertainty5));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty6));
		assertTrue(InputValidityChecker.checkUncertaintyString(uncertainty7));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty8));
		assertTrue(InputValidityChecker.checkUncertaintyString(uncertainty9));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty10));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty11));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty12));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty13));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty14));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty15));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty16));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty17));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty18));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty19));
		assertFalse(InputValidityChecker.checkUncertaintyString(uncertainty20));	
		assertTrue(InputValidityChecker.checkUncertaintyString(uncertainty21));	
	}
	
	@Test
	public void testIsEmpty() {
		String string1 = "";
		String string2 = " ";
		String string3 = "test";
		String string4 = null;
		
		assertTrue(InputValidityChecker.isEmpty(string1));	
		assertFalse(InputValidityChecker.isEmpty(string2));	
		assertFalse(InputValidityChecker.isEmpty(string3));	
		assertTrue(InputValidityChecker.isEmpty(string4));	
	}

	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
}
