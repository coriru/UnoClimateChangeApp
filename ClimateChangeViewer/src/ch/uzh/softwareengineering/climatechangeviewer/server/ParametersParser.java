package ch.uzh.softwareengineering.climatechangeviewer.server;

import ch.uzh.softwareengineering.climatechangeviewer.client.TableExport;

public class ParametersParser {
	
	
	public static String parseStringParameter(String value) {
		if(value == null || value.equals("") || value.equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
			return "";
		} else {
			return value;
		}
	}
	
	public static int parseIntegerParameter(String value) {
		if(value == null || value.equals("") || value.equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
			return Integer.MIN_VALUE;
		} else {
			return Integer.parseInt(value);
		}
	}
	
	public static double parseDoubleParameter(String value) {
		if(value == null || value.equals("") || value.equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
			return Double.MAX_VALUE;
		} else {
			return Double.parseDouble(value);
		}
	}
}
