package org.tester.client;

import java.io.Serializable;

// test comment

public class City implements Serializable  {
	
	private String date;
	private String cityName;
	private String country;
	private String averageTemperatureUncertainty;
	private String averageTemperature;
	private String longitude;
	private String latitude;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAverageTemperatureUncertainty() {
		return averageTemperatureUncertainty;
	}
	public void setAverageTemperatureUncertainty(String averageTemperatureUncertainty) {
		this.averageTemperatureUncertainty = averageTemperatureUncertainty;
	}
	public String getAverageTemperatute() {
		return averageTemperature;
	}
	public void setAverageTemperature(String averageTemperatute) {
		this.averageTemperature = averageTemperatute;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String langitude) {
		this.longitude = langitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}	
}
