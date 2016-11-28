package ch.uzh.softwareengineering.climatechangeviewer.server;

public class CoordinatesParser {
	
	public static float parseLatitude(String latitudeString) {
		String latitudeDigitsString;
		float latitude = Float.MAX_VALUE;
		
		if(latitudeString.toUpperCase().indexOf('N') >= 0) {
			latitudeDigitsString = latitudeString.substring(0, latitudeString.toUpperCase().indexOf('N'));
			latitude = Float.parseFloat(latitudeDigitsString);
		} else if(latitudeString.toUpperCase().indexOf('S') >= 0)  {
			latitudeDigitsString = latitudeString.substring(0, latitudeString.toUpperCase().indexOf('S'));
			latitude = Float.parseFloat(latitudeDigitsString);
			latitude = latitude * (-1);
		}
		return latitude;
	}
	
	public static float parseLongitude(String longitudeString) {
		String longitudeDigitsString;
		float longitude = Float.MAX_VALUE;
		
		if(longitudeString.toUpperCase().indexOf('E') >= 0) {
			longitudeDigitsString = longitudeString.substring(0, longitudeString.toUpperCase().indexOf('E'));
			longitude = Float.parseFloat(longitudeDigitsString);
		} else if (longitudeString.toUpperCase().indexOf('W') >= 0) {
			longitudeDigitsString = longitudeString.substring(0, longitudeString.toUpperCase().indexOf('W'));
			longitude = Float.parseFloat(longitudeDigitsString);
			longitude = longitude * (-1);
		}
		return longitude;
	}
}
