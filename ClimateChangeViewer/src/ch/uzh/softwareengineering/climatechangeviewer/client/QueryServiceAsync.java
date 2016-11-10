package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QueryServiceAsync {

	void getData(String valueDate, String valueCountry, String valueCity, AsyncCallback<List<City>> callback);

}
