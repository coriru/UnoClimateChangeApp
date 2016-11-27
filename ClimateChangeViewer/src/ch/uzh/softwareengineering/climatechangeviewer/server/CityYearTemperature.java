package ch.uzh.softwareengineering.climatechangeviewer.server;

public class CityYearTemperature {
	
	private String city = "";
	private float latitude = Float.MAX_VALUE;
	private float longitude = Float.MAX_VALUE;
	private int year = Integer.MIN_VALUE;
	private float temperature = Float.MAX_VALUE;
	private float uncertainty = Float.MAX_VALUE;

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	public float getUncertainty() {
		return uncertainty;
	}
	
	public void setUncertainty(float uncertainty) {
		this.uncertainty = uncertainty;
	}
}
