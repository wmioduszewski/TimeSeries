package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;

public interface IDataImporter {
	
	List<? extends IRecord> importData() throws IOException;

}
