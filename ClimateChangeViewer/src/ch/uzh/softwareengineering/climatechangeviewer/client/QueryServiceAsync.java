package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QueryServiceAsync {

	void getData(int month, int year1, int year2, String country, String city,
			float minTemperature, float maxTemperature, float maxTemperatureUncertainty, AsyncCallback<List<DataElement>> callback);
}
