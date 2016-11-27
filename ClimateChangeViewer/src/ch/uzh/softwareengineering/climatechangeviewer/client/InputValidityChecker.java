package ch.uzh.softwareengineering.climatechangeviewer.client;

public class InputValidityChecker {

	public boolean checkNameString(String s) {
		if(s == null) {
			return true;
		} else if (s.equalsIgnoreCase("")) {
			return true;
		} else if(s.toUpperCase().matches("^[A-Z ÄÖÜÔÉÈÀÁÃÎÊĪÍÌ'\\(\\)]{0,40}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkYearString(String s) {
		if(s == null) {
			return true;
		} else if (s.equalsIgnoreCase("")) {
			return true;
		} else if (s.toUpperCase().matches("^[1-9]{1}[0-9]{3}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkTemperatureString(String s) {
		if (s == null) {
			return true;
		} else if (s.equalsIgnoreCase("")) {
			return true;
		} else if (s.toUpperCase().matches("^([-]{0,1}[0-9]{0,2}[.]{1}[0-9]{0,3})|([-]{0,1}[0-9]{0,2})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkUncertaintyString(String s) {
		if (s == null) {
			return true;
		} else if (s.equalsIgnoreCase("")) {
			return true;
		} else if (s.toUpperCase().matches("^([0-9]{0,2}[.]{1}[0-9]{0,3})|([0-9]{0,2})$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmpty(String s) {
		if(s == null) {
			return true;
		} else if(s.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
