package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class TableFilterTestCase extends GWTTestCase {

	@Test
	public void testSetFilterValues() {
		TableView tableView = new TableView();
		TableFilter filter = new TableFilter(tableView);
		
		// If nothing is entered into the input boxes no exception should be thrown.
		filter.getFilterBoxCity().setText("");
		filter.getFilterBoxCountry().setText("");
		filter.getFilterBoxYear1().setText("");
		filter.getFilterBoxYear2().setText("");
		filter.getFilterBoxMonth().setSelectedIndex(0);
		filter.getFilterBoxMinTemperature().setText("");
		filter.getFilterBoxMaxTemperature().setText("");
		filter.getFilterBoxUncertainty().setText("");
		
		try {
			filter.setFilterValues();
		} catch (InvalidInputException e) {
			// Since no exception should be thrown, JUnit should show an error if an exception is thrown nonetheless.
			// For this reason the following assert statement is wrong on purpose.
			assertFalse(e instanceof InvalidInputException);
		}
		
		// Check if the values were set up correctly.
		assertEquals("", filter.getCity());
		assertEquals("", filter.getCountry());
		assertEquals(Integer.MIN_VALUE, filter.getYear1());
		assertEquals(Integer.MIN_VALUE, filter.getYear2());	
		assertEquals(0, filter.getMonth());
		assertEquals(Float.MAX_VALUE, filter.getMinTemperature(), 0.001f);
		assertEquals(Float.MAX_VALUE, filter.getMaxTemperature(), 0.001f);
		assertEquals(Float.MAX_VALUE, filter.getUncertainty(), 0.001f);

		
		// The next test should throw an exception due to the entered year1 exceeds the entered year2.
		filter.getFilterBoxCity().setText("");
		filter.getFilterBoxCountry().setText("");
		filter.getFilterBoxYear1().setText("2000");
		filter.getFilterBoxYear2().setText("1999");
		filter.getFilterBoxMonth().setSelectedIndex(0);
		filter.getFilterBoxMinTemperature().setText("");
		filter.getFilterBoxMaxTemperature().setText("");
		filter.getFilterBoxUncertainty().setText("");
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
