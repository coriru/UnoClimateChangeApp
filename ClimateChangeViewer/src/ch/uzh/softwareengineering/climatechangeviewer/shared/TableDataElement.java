package ch.uzh.softwareengineering.climatechangeviewer.shared;

import java.io.Serializable;

import com.google.gwt.i18n.client.NumberFormat;

import ch.uzh.softwareengineering.climatechangeviewer.client.TableExport;

public class TableDataElement implements Serializable  {
	
	private static final long serialVersionUID = -645474813490326768L;
	public static final String VALUE_NOT_RESOLVABLE_ENTRY = "INVALID VALUE";
	
	private String city = "";
	private String country = "";
	private int month = Integer.MIN_VALUE;
	private int year = Integer.MIN_VALUE;
	private double uncertainty = Double.MAX_VALUE;
	private double temperature = Double.MAX_VALUE;
	
	public String getDateString() {		
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
        default: monthString = VALUE_NOT_RESOLVABLE_ENTRY;
                 break;
		}
        
        String date = yearString + ", " + monthString;
		
        if(year == Integer.MIN_VALUE || (month < 1 || month > 12)) {
        	return VALUE_NOT_RESOLVABLE_ENTRY;
        } else {
        	return date;
        }
	}
	
	public String getNumericalDateString() {		
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
		if(temperature >= Double.MAX_VALUE) {
			return "VALUE_NOT_RESOLVABLE_ENTRY";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(temperature);
	}
	
	public String getUncertaintyString() {
		if(uncertainty >= Double.MAX_VALUE) {
			return "VALUE_NOT_RESOLVABLE_ENTRY";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(uncertainty);
	}
	
	public String getJoinedString() {
		StringBuilder sb = new StringBuilder();
		String delimiter = ",";
		
		if(city == null || city.equals("")) {
			sb.append(VALUE_NOT_RESOLVABLE_ENTRY + delimiter);
		} else {
			sb.append(city + delimiter);
		}
		
		if(country == null || country.equals("")) {
			sb.append(VALUE_NOT_RESOLVABLE_ENTRY + delimiter);
		} else {
			sb.append(country + delimiter);
		}
		sb.append(getNumericalDateString() + delimiter);
		
		if(temperature < Double.MAX_VALUE) {
			sb.append(Double.toString(temperature) + delimiter);			
		} else {
			sb.append(VALUE_NOT_RESOLVABLE_ENTRY + delimiter);
		}
		
		if(uncertainty < Double.MAX_VALUE) {
			sb.append(Double.toString(uncertainty) + "\n");			
		} else {
			sb.append(VALUE_NOT_RESOLVABLE_ENTRY + "\n");
		}
		
		return sb.toString();
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
	
}
