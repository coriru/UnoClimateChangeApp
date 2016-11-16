package ch.uzh.softwareengineering.climatechangeviewer.client;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.user.cellview.client.Header;

public class CustomDataGridFooter extends Header<Number> {

	private Number rowCounter;
	
	public CustomDataGridFooter(Number rowCounter) {
        super(new NumberCell());
        this.rowCounter = rowCounter;
	}

    public void setCounter(Number rowCounter) {
        this.rowCounter = rowCounter;
    }

    @Override
    public Number getValue() {
        return rowCounter;
    }
}
