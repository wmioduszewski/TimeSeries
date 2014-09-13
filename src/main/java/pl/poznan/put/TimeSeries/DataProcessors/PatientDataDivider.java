package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.Patient;

public class PatientDataDivider extends DataDividerBase{
	
	@Override
	protected List<List<Characteristic>> DivideRecord(Object record) throws Exception {
		Patient patient = (Patient) record;
		return DataDivider.dividePatientDataPeriodically(patient);
	}
}
