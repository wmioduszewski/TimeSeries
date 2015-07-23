package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Utils;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;

public class PatientSaxWorkflowTest extends PatientCountedWorkflow {

	public PatientSaxWorkflowTest(DivisionOptions divisionOption,
			boolean isDominant) {
		super(divisionOption);
	}
	
	@Override
	protected void importData() {
		records = Utils.getTestPatients();
	}
}
