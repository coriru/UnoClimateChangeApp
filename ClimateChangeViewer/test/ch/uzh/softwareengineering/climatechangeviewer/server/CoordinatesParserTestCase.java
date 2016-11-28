package ch.uzh.softwareengineering.climatechangeviewer.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoordinatesParserTestCase {

	@Test
	public void testParseLatitude() {
		// The method will fail if the string parameter has another format than the used data file. For empty
		// strings Float.MAX_VALUE is assigned. 
		String latitudeString1 = "";
		String latitudeString2 = " ";
		String latitudeString3 = "0";
		String latitudeString4 = "0N";
		String latitudeString5 = "0S";
		String latitudeString6 = "124.403S";
		String latitudeString7 = "124.403N";
		String latitudeString8 = ".4S";
		String latitudeString9 = ".4N";
		String latitudeString10 = ".403S";
		String latitudeString11 = ".403N";

		assertEquals(Float.MAX_VALUE, CoordinatesParser.parseLatitude(latitudeString1), 0.001f);
		assertEquals(Float.MAX_VALUE, CoordinatesParser.parseLatitude(latitudeString2), 0.001f);
		assertEquals(Float.MAX_VALUE, CoordinatesParser.parseLatitude(latitudeString3), 0.001f);
		assertEquals(0f, CoordinatesParser.parseLatitude(latitudeString4), 0.001f);
		assertEquals(0f, CoordinatesParser.parseLatitude(latitudeString5), 0.001f);
		assertEquals(-124.403, CoordinatesParser.parseLatitude(latitudeString6), 0.001f);
		assertEquals(124.403, CoordinatesParser.parseLatitude(latitudeString7), 0.001f);
		assertEquals(-0.4f, CoordinatesParser.parseLatitude(latitudeString8), 0.001f);
		assertEquals(0.4f, CoordinatesParser.parseLatitude(latitudeString9), 0.001f);
		assertEquals(-0.403f, CoordinatesParser.parseLatitude(latitudeString10), 0.001f);
		assertEquals(0.403f, CoordinatesParser.parseLatitude(latitudeString11), 0.001f);
	}
	
	@Test
	public void testParseLongitude() {
		// The method will fail if the string parameter has another format than the used data file. For empty
		// strings Float.MAX_VALUE is assigned. 
		String longitudeString1 = "";
		String longitudeString2 = " ";
		String longitudeString3 = "0";
		String longitudeString4 = "0E";
		String longitudeString5 = "0W";
		String longitudeString6 = "124.403W";
		String longitudeString7 = "124.403E";
		String longitudeString8 = ".4W";
		String longitudeString9 = ".4E";
		String longitudeString10 = ".403W";
		String longitudeString11 = ".403E";

		assertEquals(Float.MAX_VALUE, CoordinatesParser.parseLongitude(longitudeString1), 0.001f);
		assertEquals(Float.MAX_VALUE, CoordinatesParser.parseLongitude(longitudeString2), 0.001f);
		assertEquals(Float.MAX_VALUE, CoordinatesParser.parseLongitude(longitudeString3), 0.001f);
		assertEquals(0f, CoordinatesParser.parseLongitude(longitudeString4), 0.001f);
		assertEquals(0f, CoordinatesParser.parseLongitude(longitudeString5), 0.001f);
		assertEquals(-124.403, CoordinatesParser.parseLongitude(longitudeString6), 0.001f);
		assertEquals(124.403, CoordinatesParser.parseLongitude(longitudeString7), 0.001f);
		assertEquals(-0.4f, CoordinatesParser.parseLongitude(longitudeString8), 0.001f);
		assertEquals(0.4f, CoordinatesParser.parseLongitude(longitudeString9), 0.001f);
		assertEquals(-0.403f, CoordinatesParser.parseLongitude(longitudeString10), 0.001f);
		assertEquals(0.403f, CoordinatesParser.parseLongitude(longitudeString11), 0.001f);
	}
	
}