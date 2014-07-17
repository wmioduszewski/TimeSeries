package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.Patient;

public class PatientDataDivider extends DataDividerBase{
	
	@Override
	protected List<Double[]> DivideRecord(Object record) throws Exception {
		Patient patient = (Patient) record;
		return DataDivider.dividePatientData(patient);
	}
}
