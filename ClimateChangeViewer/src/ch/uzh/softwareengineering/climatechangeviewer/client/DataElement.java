package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.io.Serializable;

import com.google.gwt.i18n.client.NumberFormat;


public class DataElement implements Serializable  {
	
	private static final long serialVersionUID = -645474813490326768L;
	private String city;
	private String country;
	private int month;
	private int year;
	private float uncertainty;
	private float temperature;
	private String longitude;
	private String latitude;
	
	public String getDate() {		
		String monthString;
		String yearString = Integer.toString(year);
		
		switch (month) {
        case 1:  monthString = "January";
                 break;
        case 2:  monthString = "February";
                 break;
        case 3:  monthString = "March";
                 break;
        case 4:  monthString = "April";
                 break;
        case 5:  monthString = "May";
                 break;
        case 6:  monthString = "June";
                 break;
        case 7:  monthString = "July";
                 break;
        case 8:  monthString = "August";
                 break;
        case 9:  monthString = "September";
                 break;
        case 10: monthString = "October";
                 break;
        case 11: monthString = "November";
                 break;
        case 12: monthString = "December";
                 break;
        default: monthString = "Invalid month";
                 break;
		}
        
        String date = yearString + ", " + monthString;
		return date;
	}
	
	public String getDateForStringSorting() {		
		String monthString;
		String yearString = Integer.toString(year);
		
		switch (month) {
        case 1:  monthString = "01";
                 break;
        case 2:  monthString = "02";
                 break;
        case 3:  monthString = "03";
                 break;
        case 4:  monthString = "04";
                 break;
        case 5:  monthString = "05";
                 break;
        case 6:  monthString = "06";
                 break;
        case 7:  monthString = "07";
                 break;
        case 8:  monthString = "08";
                 break;
        case 9:  monthString = "09";
                 break;
        case 10: monthString = "10";
                 break;
        case 11: monthString = "11";
                 break;
        case 12: monthString = "12";
                 break;
        default: monthString = "00";
                 break;
		}
        
        String date = yearString + "-" + monthString;
		return date;
	}
	
	public String getTemperatureString() {
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(temperature);
	}
	
	public String getUncertaintyString() {
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(uncertainty);
	}
	
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
