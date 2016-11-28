package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class DataFileReaderTestCase {
	
	@Test
	public void testGet() {
		// The used data file must be in a valid format. The test file contains 3 valid data lines.
		List<DataFileLine> dataFileLines = DataFileReader.getDataLines("www-test/testData/DataFileReaderTest.csv");
		
		assertEquals(3, dataFileLines.size());
		assertTrue("TestCity1".equals(dataFileLines.get(0).getCity()));
		assertTrue("TestCountry1".equals(dataFileLines.get(0).getCountry()));
		assertEquals(2000, dataFileLines.get(0).getYear());
		assertEquals(1, dataFileLines.get(0).getMonth());
		assertEquals(26.704f, dataFileLines.get(0).getTemperature(), 0.001f);
		assertEquals(1.435f, dataFileLines.get(0).getUncertainty(), 0.001f);
		assertEquals(5.63f, dataFileLines.get(0).getLatitude(), 0.001f);
		assertEquals(-3.23f, dataFileLines.get(0).getLongitude(), 0.001f);	
	}
	
}