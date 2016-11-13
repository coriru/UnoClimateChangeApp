package ch.uzh.softwareengineering.climatechangeviewer.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("data")
public interface QueryService extends RemoteService {

	List<DataElement> getData(int month, int year1, int year2, String country, String city,
			float minTemperature, float maxTemperature, float maxTemperatureUncertainty) throws FilterOverflowException, NoEntriesFoundException;
}