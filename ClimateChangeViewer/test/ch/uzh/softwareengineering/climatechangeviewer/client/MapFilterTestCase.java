package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MapFilterTestCase extends GWTTestCase {

	@Test
	public void testSetFilterValues() {
		MapView mapView = new MapView();
		MapFilter filter = new MapFilter(mapView);
		
		// If nothing is entered into the input boxes no exception should be thrown.
		filter.getFilterBoxPeriod1().setText("1900");
		filter.getFilterBoxPeriod2().setText("1910");
		filter.getFilterBoxUncertainty().setText("0.5");
		
		try {
			filter.setFilterValues();
		} catch (InvalidInputException e) {
			// Since no exception should be thrown, JUnit should show an error if an exception is thrown nonetheless.
			// For this reason the following assert statement is wrong on purpose.
			assertFalse(e instanceof InvalidInputException);
		}
		
		// Check if the values were set up correctly.
		assertEquals(1900, filter.getPeriod1());
		assertEquals(1910, filter.getPeriod2());
		assertEquals(0.5f, filter.getUncertainty(), 0.001f);

		// The next test should throw an exception due to the entered (year1 + COMPARISON_PERIOD_LENGTH) exceeds the
		// entered year2.
		filter.getFilterBoxPeriod1().setText("1990");
		filter.getFilterBoxPeriod2().setText("1999");
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
