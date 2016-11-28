package ch.uzh.softwareengineering.climatechangeviewer.server;

public class DataFileValidityChecker {

	public static boolean checkDateString(String s) {
		if(s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		// Check if date is in form of: 'YYYY-MM-DD'
		} else if(s.toUpperCase().matches("^[1-9]{1}[0-9]{3}[-]{1}[0-9]{2}[-]{1}[0]{1}[1]{1}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkNameString(String s) {
		if(s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		} else if(s.toUpperCase().matches("^[A-Z ÄÖÜÔÉÈÀÁÃÎÊĪÍÌ'\\(\\)]{0,40}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkYearString(String s) {
		if(s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		} else if (s.toUpperCase().matches("^[1-9]{1}[0-9]{3}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkTemperatureString(String s) {
		if (s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		} else if (s.toUpperCase().matches("^([-]{0,1}[0-9]{0,2}[.]{1}[0-9]{0,3})|([-]{0,1}[0-9]{0,2})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkUncertaintyString(String s) {
		if (s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		} else if (s.toUpperCase().matches("^([0-9]{0,2}[.]{1}[0-9]{0,3})|([0-9]{0,2})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkLatitudeString(String s) {
		if (s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		} else if (s.toUpperCase().matches("^([0-9]{0,3}[.]{1}[0-9]{0,3}[SN]{1})|([0-9]{1,3}[SN]{1})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkLongitudeString(String s) {
		if (s == null) {
			return false;
		} else if(s.equalsIgnoreCase("")){
			return false;
		} else if (s.toUpperCase().matches("^([0-9]{0,3}[.]{1}[0-9]{0,3}[WE]{1})|([0-9]{1,3}[WE]{1})$")) {
			return true;
		} else {
			return false;
		}
	}
}
