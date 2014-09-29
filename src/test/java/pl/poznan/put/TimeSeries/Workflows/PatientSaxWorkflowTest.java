package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Utils;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;

public class PatientSaxWorkflowTest extends PatientSaxWorkflow {

	public PatientSaxWorkflowTest(DivisionOptions divisionOption,
			boolean isDominant) {
		super(divisionOption, isDominant);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void importData() {
		patients = Utils.getTestPatients();
	}
	

}
