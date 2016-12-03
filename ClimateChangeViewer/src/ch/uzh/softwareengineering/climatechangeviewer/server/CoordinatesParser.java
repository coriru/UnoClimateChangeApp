package ch.uzh.softwareengineering.climatechangeviewer.server;

public class CoordinatesParser {
	
	public static double parseLatitude(String latitudeString) {
		String latitudeDigitsString;
		double latitude = Double.MAX_VALUE;
		
		if(latitudeString.toUpperCase().indexOf('N') >= 0) {
			latitudeDigitsString = latitudeString.substring(0, latitudeString.toUpperCase().indexOf('N'));
			latitude = Double.parseDouble(latitudeDigitsString);
		} else if(latitudeString.toUpperCase().indexOf('S') >= 0)  {
			latitudeDigitsString = latitudeString.substring(0, latitudeString.toUpperCase().indexOf('S'));
			latitude = Double.parseDouble(latitudeDigitsString);
			latitude = latitude * (-1);
		}
		return latitude;
	}
	
	public static double parseLongitude(String longitudeString) {
		String longitudeDigitsString;
		double longitude = Double.MAX_VALUE;
		
		if(longitudeString.toUpperCase().indexOf('E') >= 0) {
			longitudeDigitsString = longitudeString.substring(0, longitudeString.toUpperCase().indexOf('E'));
			longitude = Double.parseDouble(longitudeDigitsString);
		} else if (longitudeString.toUpperCase().indexOf('W') >= 0) {
			longitudeDigitsString = longitudeString.substring(0, longitudeString.toUpperCase().indexOf('W'));
			longitude = Double.parseDouble(longitudeDigitsString);
			longitude = longitude * (-1);
		}
		return longitude;
	}
}
