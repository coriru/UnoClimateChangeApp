package ch.uzh.softwareengineering.climatechangeviewer.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.uzh.softwareengineering.climatechangeviewer.client.DataFileCorruptedException;
import ch.uzh.softwareengineering.climatechangeviewer.client.FilterOverflowException;
import ch.uzh.softwareengineering.climatechangeviewer.client.TableExport;
import ch.uzh.softwareengineering.climatechangeviewer.client.TableView;
import ch.uzh.softwareengineering.climatechangeviewer.shared.NoEntriesFoundException;
import ch.uzh.softwareengineering.climatechangeviewer.shared.TableDataElement;

public class TableExportService extends HttpServlet {
	
	private static final long serialVersionUID = -7675047717773794447L;
	
	private QueryServiceImpl queryService = new QueryServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String parameters = req.getParameter("parameters");
        int BUFFER = 1024 * 100;
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=ClimateChangeDataExport.csv");
        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setBufferSize(BUFFER);
        outputStream = setOutputStreamByParameters(outputStream, parameters);
    }   
    
    private ServletOutputStream setOutputStreamByParameters(ServletOutputStream outputStream, String parameters)
    		throws IOException {
    	List<TableDataElement> tableData = new ArrayList<TableDataElement>();
    	
    	String[] splitParameters = parameters.split(TableExport.PARAMETER_DELIMITER);
    	
    	// Parse all the string parameters in order to get the data with the help of QueryServiceImpl.
    	String cityQuery = ParametersParser.parseStringParameter(splitParameters[0]);
    	String countryQuery = ParametersParser.parseStringParameter(splitParameters[1]);
    	int year1Query = ParametersParser.parseIntegerParameter(splitParameters[2]);
    	int year2Query = ParametersParser.parseIntegerParameter(splitParameters[3]);
    	int monthQuery = ParametersParser.parseIntegerParameter(splitParameters[4]);
    	double minTemperatureQuery = ParametersParser.parseDoubleParameter(splitParameters[5]);
    	double maxTemperatureQuery = ParametersParser.parseDoubleParameter(splitParameters[6]);
    	double uncertaintyQuery = ParametersParser.parseDoubleParameter(splitParameters[7]);
    	
    	// Write data source to the file.
    	outputStream.write(("--------------------------------------------------------------------------\n").getBytes());
    	outputStream.write(("Data Source: Berkeley Earth, www.berkeleyearth.org\n").getBytes());
    	outputStream.write(("--------------------------------------------------------------------------\n").getBytes());
    	
    	// Write the filter parameters to the file.
    	StringBuilder sb = new StringBuilder();
    	sb.append("The data was filtered according to the following parameters:\n");
    	
    	
    	if(!splitParameters[0].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("City = \"");
    		sb.append(splitParameters[0]);    		
    		sb.append("\"\n");
    	}
    	
    	if(!splitParameters[1].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("Country = \"");
    		sb.append(splitParameters[1]);    		
    		sb.append("\"\n");
    	}
    	
    	if(!splitParameters[2].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("First Year = \"");
    		sb.append(splitParameters[2]);    		
    		sb.append("\"\n");
    	}

    	if(!splitParameters[3].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("Last Year = \"");
    		sb.append(splitParameters[3]);    		
    		sb.append("\"");
    	}
    	
    	if(!splitParameters[4].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("Month = \"");
    		switch (monthQuery) {
    		case 0:  sb.append("All Months");
    		break;
    		case 1:  sb.append("January");
    		break;
    		case 2:  sb.append("February");
    		break;
    		case 3:  sb.append("March");
    		break;
    		case 4:  sb.append("April");
    		break;
    		case 5:  sb.append("May");
    		break;
    		case 6:  sb.append("June");
    		break;
    		case 7:  sb.append("July");
    		break;
    		case 8:  sb.append("August");
    		break;
    		case 9:  sb.append("September");
    		break;
    		case 10: sb.append("October");
    		break;
    		case 11: sb.append("November");
    		break;
    		case 12: sb.append("December");
    		break;
    		default: sb.append(TableDataElement.VALUE_NOT_RESOLVABLE_ENTRY);
    		break;
    		}
    		sb.append("\"\n");
    	}
    	
    	if(!splitParameters[5].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("Minimum Temperature = \"");
    		sb.append(splitParameters[5]);    		
    		sb.append("\"\n");
    	}
    	
    	if(!splitParameters[6].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("Maximum Temperature = \"");
    		sb.append(splitParameters[6]);    		
    		sb.append("\"\n");
    	}
    	
    	if(!splitParameters[7].equals(TableExport.PARAMETER_NOT_SET_INDICATOR)) {
    		sb.append("Maximum Uncertainty = \"");
    		sb.append(splitParameters[7]);    		
    		sb.append("\"\n");
    	}
    	
        outputStream.write((sb.toString()).getBytes());
        
        // Get the data to be exported from the queryService.
    	try {
    		tableData = queryService.getTableData(cityQuery, countryQuery, year1Query, year2Query, monthQuery,
    				minTemperatureQuery, maxTemperatureQuery, uncertaintyQuery);
		} catch (FilterOverflowException | NoEntriesFoundException | DataFileCorruptedException e) {
			if(e instanceof FilterOverflowException) {
				outputStream.write(("ERROR: More than " + Integer.toString(TableView.MAX_DATA_LINES_TO_SEND)
						+ " entries found. Please set more precise filter criterias.").getBytes());
			} else if(e instanceof NoEntriesFoundException) {
				outputStream.write(("ERROR:  No Entries found. Please adjust the filter criterias.").getBytes());
			} else if(e instanceof DataFileCorruptedException) {
				outputStream.write(("ERROR: The data service is corrupted. The service is unavailable at the moment.")
						.getBytes());	
			} else {
				outputStream.write(("ERROR: The service is unavailable at the moment.").getBytes());
			}
		}
    	
    	// Write structure of the data to the file.
    	outputStream.write(("--------------------------------------------------------------------------\n").getBytes());
    	outputStream.write(("City,Country,Date,Temperature,Uncertainty\n").getBytes());
    	outputStream.write(("--------------------------------------------------------------------------\n").getBytes());
    	outputStream.flush();
    	
    	// Write the data to be exported line by line.
    	for(TableDataElement tableDataElement : tableData) {
    		outputStream.write((tableDataElement.getJoinedString()).getBytes());
    		outputStream.flush();
    	}
    	
    	return outputStream;
    }

}
