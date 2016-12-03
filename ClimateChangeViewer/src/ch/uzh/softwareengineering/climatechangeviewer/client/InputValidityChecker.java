package ch.uzh.softwareengineering.climatechangeviewer.client;

public class InputValidityChecker {

	public static boolean checkNameString(String s) {
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
	
	public static boolean isEmpty(String s) {
		if(s == null) {
			return true;
		} else if(s.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
