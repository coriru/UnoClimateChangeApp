package ch.uzh.softwareengineering.climatechangeviewer.server;

/*  The difference to the class InputValidityChecker is that the check methods in this class
 *  return 'false' if a string is empty or null.
 */ 

public class QueryServiceValidityChecker {

	public boolean checkDateString(String s) {
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
	
	public boolean checkNameString(String s) {
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
	
	public boolean checkYearString(String s) {
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
	
	public boolean checkTemperatureString(String s) {
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
	
	public boolean checkUncertaintyString(String s) {
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
	
}
