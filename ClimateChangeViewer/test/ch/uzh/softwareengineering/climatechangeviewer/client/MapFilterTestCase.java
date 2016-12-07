package ch.uzh.softwareengineering.climatechangeviewer.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

// TODO: Find possibility to set values in the rangeSlider. For some reason the setter does not work within this
//		 test case scenario. Probably because the slider is based on java script code that can't not be executed here.

public class MapFilterTestCase extends GWTTestCase {

	@Test
	public void testSetFilterValues() {
		MapView mapView = new MapView();
		MapFilter filter = new MapFilter(mapView);
		
		// If nothing is entered into the input boxes no exception should be thrown.
		//filter.setRangeSliderValues(1900, 1911);
		filter.getUncertaintyQueryInputBox().setText("0.5");
		
//		try {
//			filter.setFilterValues();
//		} catch (InvalidInputException e) {
//			// Since no exception should be thrown, JUnit should show an error if an exception is thrown nonetheless.
//			//fail();
//		}
		
//		// Check if the values were set up correctly.
//		assertEquals(1900, filter.getPeriod1Query());
//		assertEquals(1910, filter.getPeriod2Query());
//		assertEquals(0.5f, filter.getUncertaintyQuery(), 0.001f);
//
//		// The next test should throw an exception due to the entered (year1 + COMPARISON_PERIOD_LENGTH) exceeds the
//		// entered year2.
//		//filter.getRangeSlider().setValues(1990, 1999);
//		filter.getUncertaintyQueryInputBox().setText("");
//		try {
//			filter.setFilterValues();
//			// To ensure that no false positive occurs the try statement must always fail.
//			fail("Expected an IndexOutOfBoundsException to be thrown");
//		} catch (InvalidInputException e) {
//			assertTrue(e instanceof InvalidInputException);
//		}
	}
	
	@Override
	public String getModuleName() {
		return "ch.uzh.softwareengineering.climatechangeviewer.ClimateChangeViewer";
	}
	
}
