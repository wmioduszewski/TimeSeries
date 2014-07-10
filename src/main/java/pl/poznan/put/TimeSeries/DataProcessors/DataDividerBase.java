package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;

public abstract class DataDividerBase {
	
	public abstract List<Double[]> DivideRecord();
	
	public List<UnifiedRecordType> ComputeRegression(IRecord record){
		
		
		
		return null;
	}
	
}
