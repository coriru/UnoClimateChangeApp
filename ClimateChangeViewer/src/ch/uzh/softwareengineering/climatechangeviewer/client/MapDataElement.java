package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.io.Serializable;

import com.google.gwt.i18n.client.NumberFormat;

//TODO: Create parent class DataElement from which TableDataElement and MapDataElement inherit shared variables and 
//      methods.		
//TODO: We might want to move this class to the shared package.

public class MapDataElement implements Serializable {

	private static final long serialVersionUID = -9020939023258703323L;
	
	private String city = "";
	private double latitude = Double.MAX_VALUE;
	private double longitude = Double.MAX_VALUE;
	private int comparisonPeriod1Start = Integer.MIN_VALUE;
	private int comparisonPeriod2Start = Integer.MIN_VALUE;
	private double temperaturePeriod1 = Double.MAX_VALUE;
	private double temperaturePeriod2 = Double.MAX_VALUE;
	private double uncertaintyPeriod1 = Double.MAX_VALUE;
	private double uncertaintyPeriod2 = Double.MAX_VALUE;
	private int validYearsPeriod1 = Integer.MIN_VALUE;
	private int validYearsPeriod2 = Integer.MIN_VALUE;
	
	public double getTemperatureDifference() {
		if(temperaturePeriod1 >= Double.MAX_VALUE || temperaturePeriod2 >= Double.MAX_VALUE) {
			return Double.MAX_VALUE;
		}
		return temperaturePeriod2 - temperaturePeriod1;
	}
	public String getTemperaturePeriod1String() {
		if(temperaturePeriod1 >= Double.MAX_VALUE) {
			return "invalid";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(temperaturePeriod1);
	}
	
	public String getTemperaturePeriod2String() {
		if(temperaturePeriod2 >= Double.MAX_VALUE) {
			return "invalid";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(temperaturePeriod2);
	}
	
	public String getTemperatureDifferenceString() {
		if(temperaturePeriod1 >= Double.MAX_VALUE || temperaturePeriod2 >= Double.MAX_VALUE) {
			return "invalid";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(temperaturePeriod2 - temperaturePeriod1);
	}
	
	public String getUncertaintyPeriod1String() {
		if(uncertaintyPeriod1 >= Double.MAX_VALUE) {
			return "invalid";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(uncertaintyPeriod1);
	}
	
	public String getUncertaintyPeriod2String() {
		if(uncertaintyPeriod2 >= Double.MAX_VALUE) {
			return "invalid";
		}
		NumberFormat nf = NumberFormat.getFormat("0.000");
		return nf.format(uncertaintyPeriod2);
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
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getComparisonPeriod1Start() {
		return comparisonPeriod1Start;
	}
	
	public void setComparisonPeriod1Start(int comparisonPeriod1Start) {
		this.comparisonPeriod1Start = comparisonPeriod1Start;
	}
	
	public int getComparisonPeriod2Start() {
		return comparisonPeriod2Start;
	}
	
	public void setComparisonPeriod2Start(int comparisonPeriod2Start) {
		this.comparisonPeriod2Start = comparisonPeriod2Start;
	}
	
	public double getTemperaturePeriod1() {
		return temperaturePeriod1;
	}
	
	public void setTemperaturePeriod1(double temperaturePeriod1) {
		this.temperaturePeriod1 = temperaturePeriod1;
	}
	
	public double getTemperaturePeriod2() {
		return temperaturePeriod2;
	}
	
	public void setTemperaturePeriod2(double temperaturePeriod2) {
		this.temperaturePeriod2 = temperaturePeriod2;
	}
	
	public double getUncertaintyPeriod1() {
		return uncertaintyPeriod1;
	}
	
	public void setUncertaintyPeriod1(double uncertaintyPeriod1) {
		this.uncertaintyPeriod1 = uncertaintyPeriod1;
	}
	
	public double getUncertaintyPeriod2() {
		return uncertaintyPeriod2;
	}
	
	public void setUncertaintyPeriod2(double uncertaintyPeriod2) {
		this.uncertaintyPeriod2 = uncertaintyPeriod2;
	}
	
	public int getValidYearsPeriod1() {
		return validYearsPeriod1;
	}
	
	public void setValidYearsPeriod1(int validYearsPeriod1) {
		this.validYearsPeriod1 = validYearsPeriod1;
	}
	
	public int getValidYearsPeriod2() {
		return validYearsPeriod2;
	}
	
	public void setValidYearsPeriod2(int validYearsPeriod2) {
		this.validYearsPeriod2 = validYearsPeriod2;
	}

}
