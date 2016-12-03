package ch.uzh.softwareengineering.climatechangeviewer.server;

/* This class is used to store the average temperature of a city in a specified year.
 */

public class CityYearTemperature {

	private String city = "";
	private int year = Integer.MIN_VALUE;
	private double temperature = Double.MAX_VALUE;
	private double uncertainty = Double.MAX_VALUE;
	private double latitude = Double.MAX_VALUE;
	private double longitude = Double.MAX_VALUE;

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getUncertainty() {
		return uncertainty;
	}
	
	public void setUncertainty(double uncertainty) {
		this.uncertainty = uncertainty;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
