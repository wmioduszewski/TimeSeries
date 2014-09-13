package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;

public class EamonnDataDivider {//extends DataDividerBase {

	//@Override
	protected List<Double[]> DivideRecord(Object record) {
		UnifiedRecordType eamonnRecord = (UnifiedRecordType) record;
		
		return DataDivider.divideEamonnRecord(eamonnRecord);
	}

}
