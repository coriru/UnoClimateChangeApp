package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class InputCheckerValidityCheckTestCase extends GWTTestCase {

	@Test
	public void testCheckNameString() {
		InputValidityChecker checker = new InputValidityChecker();
		
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
		assertTrue(checker.checkNameString(name9));
		assertFalse(checker.checkNameString(name10));
		assertFalse(checker.checkNameString(name11));
		assertTrue(checker.checkNameString(name12));
		assertTrue(checker.checkNameString(name13));
		assertTrue(checker.checkNameString(name13));
	}
	
	@Test
	public void testCheckYearString() {
		InputValidityChecker checker = new InputValidityChecker();
		
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
		
		assertTrue(checker.checkYearString(year1));
		assertFalse(checker.checkYearString(year2));
		assertFalse(checker.checkYearString(year3));
		assertFalse(checker.checkYearString(year4));
		assertTrue(checker.checkYearString(year5));
		assertFalse(checker.checkYearString(year6));
		assertFalse(checker.checkYearString(year7));
		assertFalse(checker.checkYearString(year8));
		assertFalse(checker.checkYearString(year9));
		assertFalse(checker.checkYearString(year10));
		assertFalse(checker.checkYearString(year11));
		assertTrue(checker.checkYearString(year12));
		assertTrue(checker.checkYearString(year13));
	}
	
	@Test
	public void testCheckTemperatureString() {
		InputValidityChecker checker = new InputValidityChecker();
		
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
			
		assertTrue(checker.checkTemperatureString(temperature1));
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
		assertTrue(checker.checkTemperatureString(temperature21));
	}
	
	@Test
	public void testCheckUncertaintyString() {
		InputValidityChecker checker = new InputValidityChecker();
		
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

		assertTrue(checker.checkUncertaintyString(uncertainty1));
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
		assertTrue(checker.checkUncertaintyString(uncertainty21));	
	}
	
	@Test
	public void testIsEmpty() {
		InputValidityChecker checker = new InputValidityChecker();
		
		String string1 = "";
		String string2 = " ";
		String string3 = "test";
		String string4 = null;
		
		assertTrue(checker.isEmpty(string1));	
		assertFalse(checker.isEmpty(string2));	
		assertFalse(checker.isEmpty(string3));	
		assertTrue(checker.isEmpty(string4));	
	}

	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
}
