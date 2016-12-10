package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import ch.uzh.softwareengineering.climatechangeviewer.shared.InvalidInputException;

public class TableFilterTestCase extends GWTTestCase {

	@Test
	public void testSetFilterValues() {
		TableView tableView = new TableView();
		TableFilter filter = new TableFilter(tableView);
		
		// If nothing is entered into the input boxes no exception should be thrown.
		filter.getCityQueryInputBox().setText("");
		filter.getCountryQueryInputBox().setText("");
		filter.getYear1QueryInputBox().setText("");
		filter.getYear2QueryInputBox().setText("");
		filter.getMonthQueryInputBox().setSelectedIndex(0);
		filter.getMinTemperatureQueryInputBox().setText("");
		filter.getMaxTemperatureQueryInputBox().setText("");
		filter.getUncertaintyQueryInputBox().setText("");
		
		try {
			filter.setFilterValues();
		} catch (InvalidInputException e) {
			// Since no exception should be thrown, JUnit should show an error if an exception is thrown nonetheless.
			fail();
		}
		
		// Check if the values were set up correctly.
		assertEquals("", filter.getCityQuery());
		assertEquals("", filter.getCountryQuery());
		assertEquals(Integer.MIN_VALUE, filter.getYear1Query());
		assertEquals(Integer.MIN_VALUE, filter.getYear2Query());	
		assertEquals(0, filter.getMonthQuery());
		assertEquals(Double.MAX_VALUE, filter.getMinTemperatureQuery(), 0.001f);
		assertEquals(Double.MAX_VALUE, filter.getMaxTemperatureQuery(), 0.001f);
		assertEquals(Double.MAX_VALUE, filter.getUncertaintyQuery(), 0.001f);

		
		// The next test should throw an exception due to the entered year1 exceeds the entered year2.
		filter.getCityQueryInputBox().setText("");
		filter.getCountryQueryInputBox().setText("");
		filter.getYear1QueryInputBox().setText("2000");
		filter.getYear2QueryInputBox().setText("1999");
		filter.getMonthQueryInputBox().setSelectedIndex(0);
		filter.getMinTemperatureQueryInputBox().setText("");
		filter.getMaxTemperatureQueryInputBox().setText("");
		filter.getUncertaintyQueryInputBox().setText("");
		
		try {
			filter.setFilterValues();
			// To ensure that no false positive occurs the try statement must always fail.
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (InvalidInputException e) {
			assertTrue(e instanceof InvalidInputException);
		}
	}
	
	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
	
}
