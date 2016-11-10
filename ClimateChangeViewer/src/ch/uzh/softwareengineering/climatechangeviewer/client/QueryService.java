package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("data")
public interface QueryService extends RemoteService {

//	List<City> getData(String valueDate, String valueCountry, String valueCity, float valueTemp, float valueTempUnc);
	
	List<City> getData(String valueDate, String valueCountry, String valueCity);
}