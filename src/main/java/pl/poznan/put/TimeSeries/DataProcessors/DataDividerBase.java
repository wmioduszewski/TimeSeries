package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public abstract class DataDividerBase {
	
	protected abstract List<List<Characteristic>> DivideRecord(Object record) throws Exception;
	
	public UnifiedArffRow ComputeRegression(IRecord record) throws Exception{
		
		List<List<Characteristic>> parts = DivideRecord(record);
		List<RegressionResult> regResults = new ArrayList<RegressionResult>();
		for (List<Characteristic> innerList : parts) {
			RegressionResult regRes = RegressionCalculator.ComputeRegression(innerList,false);
			regResults.add(regRes);
		}
		
		UnifiedArffRow arffRow = new UnifiedArffRow(regResults,record.getDestinationClass());
		return arffRow;
	}
	
	public List<UnifiedArffRow> ComputeRegression(List<IRecord> records) throws Exception{
		List<UnifiedArffRow> res = new ArrayList<UnifiedArffRow>();
		for (IRecord record : records) {
			res.add(ComputeRegression(record));
		}
		return res;
	}
	
}
