package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataFileCorruptionCheckerTestCase {
	
	@Test
	public void testCheckDataFileCorruption() {
		
		// The used dataFile for the next test is missing a letter in latitude on line 3.
		assertTrue(DataFileCorruptionChecker.checkDataFileCorruption("www-test/testData/DataFileCorruptionTest1.csv"));
		
		// The used dataFile for the next test uses the correct format.
		assertFalse(DataFileCorruptionChecker.checkDataFileCorruption("war/data/GlobalLandTemperaturesByMajorCity_v1.csv"));
	
		// The used dataFile for the next test uses the correct format.
		assertFalse(DataFileCorruptionChecker.checkDataFileCorruption("www-test/testData/DataFileCorruptionTest2.csv"));
	}
	
}