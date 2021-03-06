package ch.uzh.softwareengineering.climatechangeviewer.server;

import java.util.ArrayList;
import java.util.List;

public class CityYearTemperatureCalculator {
		
	public static List<CityYearTemperature> calculateCityYearTemperatures(List<DataFileLine> dataFileLines,
			double uncertaintyQuery) {
		List<CityYearTemperature> cityYearTemperatures = new ArrayList<CityYearTemperature>();
		
		// Since dataLineObjects is ordered by date (first) and city (second) it is possible to loop through the list
		// in order to calculate the average temperature needed for cityYearTemperatures.
		for(int i = 0; i < dataFileLines.size(); i++) {
			double aggregatedTemperature = 0;
			double averageTemperature = Double.MAX_VALUE;
			double aggregatedUncertainty = 0;
			double averageUncertainty = Double.MAX_VALUE;
			int validMonths = 0;
			
			// As long as the year does not change temperature values are aggregated.
			do {
				// A month is only considered valid if a valid temperature has been assigned and if he does not exceed
				// MAX_VALID_UNCERTAINTY.
				if(dataFileLines.get(i).getTemperature() < Double.MAX_VALUE
						&& dataFileLines.get(i).getUncertainty() <= uncertaintyQuery) {
					aggregatedTemperature += dataFileLines.get(i).getTemperature();
					aggregatedUncertainty += dataFileLines.get(i).getUncertainty();
					validMonths++;
				}
				i++;
					
			} while(i < dataFileLines.size() && dataFileLines.get(i).getYear() == dataFileLines.get(i-1).getYear());
			
			// Decrease the counter again. Otherwise the first month of the next year will be left out.
			i--;
			
			// Only set valid averages for a year if no month exceeds MAX_VALID_UNCERTAINTY. 
			if(validMonths == 12) {
				averageTemperature = aggregatedTemperature / validMonths;
				averageUncertainty = aggregatedUncertainty / validMonths;
			}
			
			// Add the calculated average to cityYearTemperatures. 
			addCityYearTemperature(cityYearTemperatures, dataFileLines.get(i).getCity(),
					dataFileLines.get(i).getLatitude(), dataFileLines.get(i).getLongitude(),
					dataFileLines.get(i).getYear(), averageTemperature, averageUncertainty);	
		}
		return cityYearTemperatures;
	}
	
	private static void addCityYearTemperature(List<CityYearTemperature> cityYearTemperatures, String city,
			double latitude, double longitude, int year, double temperature, double uncertainty) {
		CityYearTemperature cityYearTemperature = new CityYearTemperature();
		
		cityYearTemperature.setCity(city);
		cityYearTemperature.setYear(year);
		cityYearTemperature.setTemperature(temperature);
		cityYearTemperature.setUncertainty(uncertainty);
		cityYearTemperature.setLatitude(latitude);
		cityYearTemperature.setLongitude(longitude);
		
		cityYearTemperatures.add(cityYearTemperature);
	}
}
