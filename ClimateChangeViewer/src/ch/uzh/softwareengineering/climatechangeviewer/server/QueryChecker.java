package ch.uzh.softwareengineering.climatechangeviewer.server;

public class QueryChecker {
	
	public static boolean checkDateQuery(int year, int month, int year1Query, int year2Query, int monthQuery) {
		if(monthQuery == 0 || monthQuery == month) {
			if(year1Query == Integer.MIN_VALUE || year >= year1Query) {
				if((year2Query == Integer.MIN_VALUE || year <= year2Query)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean checkCityQuery(String city, String cityQuery) {
		if(city == null || cityQuery == null) {
			return false;
		}
		if(city.equalsIgnoreCase("") || city.toUpperCase().contains(cityQuery.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	public static boolean checkCountryQuery(String country, String countryQuery) {
		if(country == null || countryQuery == null) {
			return false;
		}
		if(country.equalsIgnoreCase("") || country.toUpperCase().contains(countryQuery.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	public static boolean checkUncertaintyQuery(double uncertainty, double uncertaintyQuery) {
		if(uncertaintyQuery >= Double.MAX_VALUE  || uncertainty <= uncertaintyQuery) {
			return true;
		}
		return false;
	}
	
	public static boolean checkTemperatureQuery(double temperature, double minTemperatureQuery, double maxTemperatureQuery) {
		if(minTemperatureQuery >= Double.MAX_VALUE || temperature >= minTemperatureQuery) {
			if(maxTemperatureQuery >= Double.MAX_VALUE || temperature <= maxTemperatureQuery) {
				return true;
			}
		}
		return false;
	}
	
}
