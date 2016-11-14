package ch.uzh.softwareengineering.climatechangeviewer.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class FilterValidityCheckTestCase extends GWTTestCase {

	@Test
	public void testCheckNameString() {
		TableView tableView = new TableView();
		Filter filter = new Filter(tableView);
		
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
		String name11 = "TESTTESTTESTTESTTESTTESTTESTTES"; // 31 chars
		String name12 = null;
		
		assertTrue(filter.checkNameString(name1));
		assertTrue(filter.checkNameString(name2));
		assertTrue(filter.checkNameString(name3));
		assertTrue(filter.checkNameString(name4));
		assertTrue(filter.checkNameString(name5));
		assertTrue(filter.checkNameString(name9));
		assertFalse(filter.checkNameString(name12));
		assertFalse(filter.checkNameString(name6));
		assertFalse(filter.checkNameString(name7));
		assertFalse(filter.checkNameString(name8));
		assertFalse(filter.checkNameString(name10));
		assertFalse(filter.checkNameString(name11));
	}
	
	@Test
	public void testCheckYearString() {
		TableView tableView = new TableView();
		Filter filter = new Filter(tableView);
		
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
		
		assertTrue(filter.checkYearString(year1));
		assertTrue(filter.checkYearString(year2));
		assertTrue(filter.checkYearString(year3));
		assertTrue(filter.checkYearString(year4));
		assertTrue(filter.checkYearString(year5));
		assertTrue(filter.checkYearString(year11));
		assertTrue(filter.checkYearString(year12));
		assertFalse(filter.checkYearString(year13));
		assertFalse(filter.checkYearString(year6));
		assertFalse(filter.checkYearString(year7));
		assertFalse(filter.checkYearString(year8));
		assertFalse(filter.checkYearString(year9));
		assertFalse(filter.checkYearString(year10));
	}
	
	@Test
	public void testCheckTemperatureString() {
		TableView tableView = new TableView();
		Filter filter = new Filter(tableView);
		
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
			
		assertTrue(filter.checkTemperatureString(temperature1));
		assertTrue(filter.checkTemperatureString(temperature1));
		assertTrue(filter.checkTemperatureString(temperature2));
		assertTrue(filter.checkTemperatureString(temperature3));
		assertTrue(filter.checkTemperatureString(temperature4));
		assertTrue(filter.checkTemperatureString(temperature5));
		assertTrue(filter.checkTemperatureString(temperature6));
		assertTrue(filter.checkTemperatureString(temperature7));
		assertTrue(filter.checkTemperatureString(temperature8));
		assertTrue(filter.checkTemperatureString(temperature9));
		assertTrue(filter.checkTemperatureString(temperature10));
		assertFalse(filter.checkTemperatureString(temperature11));
		assertFalse(filter.checkTemperatureString(temperature12));
		assertFalse(filter.checkTemperatureString(temperature13));
		assertFalse(filter.checkTemperatureString(temperature14));
		assertFalse(filter.checkTemperatureString(temperature15));
		assertFalse(filter.checkTemperatureString(temperature16));
		assertFalse(filter.checkTemperatureString(temperature17));
		assertFalse(filter.checkTemperatureString(temperature18));
		assertFalse(filter.checkTemperatureString(temperature19));
		assertFalse(filter.checkTemperatureString(temperature20));
		assertFalse(filter.checkTemperatureString(temperature21));
	}
	
	@Test
	public void testCheckUncertaintyString() {
		TableView tableView = new TableView();
		Filter filter = new Filter(tableView);
		
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

		assertTrue(filter.checkUncertaintyString(uncertainty1));
		assertTrue(filter.checkUncertaintyString(uncertainty2));
		assertTrue(filter.checkUncertaintyString(uncertainty3));
		assertFalse(filter.checkUncertaintyString(uncertainty4));
		assertTrue(filter.checkUncertaintyString(uncertainty5));
		assertFalse(filter.checkUncertaintyString(uncertainty6));
		assertTrue(filter.checkUncertaintyString(uncertainty7));
		assertFalse(filter.checkUncertaintyString(uncertainty8));
		assertTrue(filter.checkUncertaintyString(uncertainty9));
		assertFalse(filter.checkUncertaintyString(uncertainty10));
		assertFalse(filter.checkUncertaintyString(uncertainty11));
		assertFalse(filter.checkUncertaintyString(uncertainty12));
		assertFalse(filter.checkUncertaintyString(uncertainty13));
		assertFalse(filter.checkUncertaintyString(uncertainty14));
		assertFalse(filter.checkUncertaintyString(uncertainty15));
		assertFalse(filter.checkUncertaintyString(uncertainty16));
		assertFalse(filter.checkUncertaintyString(uncertainty17));
		assertFalse(filter.checkUncertaintyString(uncertainty18));
		assertFalse(filter.checkUncertaintyString(uncertainty19));
		assertFalse(filter.checkUncertaintyString(uncertainty20));	
		assertFalse(filter.checkUncertaintyString(uncertainty21));	
	}
	
	@Test
	public void testIsEmpty() {
		TableView tableView = new TableView();
		Filter filter = new Filter(tableView);
		
		String string1 = "";
		String string2 = " ";
		String string3 = "test";
		String string4 = null;
		
		assertTrue(filter.isEmpty(string1));	
		assertFalse(filter.isEmpty(string2));	
		assertFalse(filter.isEmpty(string3));	
		assertFalse(filter.isEmpty(string4));	
	}

	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
}
