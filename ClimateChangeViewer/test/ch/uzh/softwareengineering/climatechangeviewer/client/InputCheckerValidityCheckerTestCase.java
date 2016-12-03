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
