package ch.uzh.softwareengineering.climatechangeviewer.server;

/* This class is used to temporarily store all entries of the csv data file (transformed). 
 */

public class DataFileLine {
	private String city = "";
	private String country = "";
	private int month = Integer.MIN_VALUE;
	private int year = Integer.MIN_VALUE;
	private float uncertainty = Float.MAX_VALUE;
	private float temperature = Float.MAX_VALUE;
	private float longitude = Float.MAX_VALUE;
	private float latitude = Float.MAX_VALUE;
	
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
	
	public float getUncertainty() {
		return uncertainty;
	}
	
	public void setUncertainty(float uncertainty) {
		this.uncertainty = uncertainty;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float langitude) {
		this.longitude = langitude;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

}
