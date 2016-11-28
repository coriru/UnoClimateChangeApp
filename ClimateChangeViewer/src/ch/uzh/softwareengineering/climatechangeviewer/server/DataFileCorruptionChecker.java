package ch.uzh.softwareengineering.climatechangeviewer.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataFileCorruptionChecker {
	
	public static boolean checkDataFileCorruption(String csvFileLocation) {
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        
		try {
			br = new BufferedReader(new FileReader(csvFileLocation));
			int lineCounter = 0;
			while ((line = br.readLine()) != null) {
				lineCounter++;

				// Ignore first line of the file.
				if(lineCounter > 1) {
					// Use comma as separator for the values in each line.
					String[] values = line.split(csvSplitBy);
					
					// Check if all values of the line are valid
					if(!DataFileValidityChecker.checkDateString(values[0])) {
						return true;
					}
					if(!DataFileValidityChecker.checkTemperatureString(values[1])) {
						return true;
					}
					if(!DataFileValidityChecker.checkUncertaintyString(values[2])) {
						return true;
					}
					if(!DataFileValidityChecker.checkNameString(values[3])) {
						return true;
					}
					if(!DataFileValidityChecker.checkNameString(values[4])) {
						return true;
					}
					if(!DataFileValidityChecker.checkLatitudeString(values[5])) {
						return true;
					}
					if(!DataFileValidityChecker.checkLongitudeString(values[6])) {
						return true;
					}
				}         
			}
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}