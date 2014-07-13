package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public abstract class DataDividerBase {
	
	public abstract List<Double[]> DivideRecord(IRecord record);
	
	public UnifiedArffRow ComputeRegression(IRecord record){
		
		List<Double[]> parts = DivideRecord(record);
		List<RegressionResult> regResults = new ArrayList<RegressionResult>();
		for (Double[] part : parts) {
			RegressionResult regRes = RegressionCalculator.ComputeRegression(part);
			regResults.add(regRes);
		}
		
		UnifiedArffRow arffRow = new UnifiedArffRow(regResults,record.getDestinationClass());
		return arffRow;
	}
	
}
