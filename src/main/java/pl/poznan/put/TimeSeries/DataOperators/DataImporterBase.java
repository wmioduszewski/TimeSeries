package pl.poznan.put.TimeSeries.DataOperators;

import java.util.List;

public abstract class DataImporterBase {

	protected String _filepath;
	
	public  DataImporterBase(String filePath) {
		this._filepath = filePath;
	}
	
	protected abstract void readData();
	
	public abstract List<Object> ImportData();
	
}
