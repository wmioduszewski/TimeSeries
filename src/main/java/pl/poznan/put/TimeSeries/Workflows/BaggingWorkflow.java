package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;

public class BaggingWorkflow extends CountedWorkflow {

	public BaggingWorkflow(DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
	}
}
