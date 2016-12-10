package ch.uzh.softwareengineering.climatechangeviewer.server;

/* This class is used to temporarily store all entries of the csv data file (transformed). 
 */

public class DataFileLine {
	
	private String city = "";
	private String country = "";
	private int month = Integer.MIN_VALUE;
	private int year = Integer.MIN_VALUE;
	private double uncertainty = Double.MAX_VALUE;
	private double temperature = Double.MAX_VALUE;
	private double longitude = Double.MAX_VALUE;
	private double latitude = Double.MAX_VALUE;
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public double getUncertainty() {
		return uncertainty;
	}
	
	public void setUncertainty(double uncertainty) {
		this.uncertainty = uncertainty;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double langitude) {
		this.longitude = langitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
