package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.BasicSaxArffBuilder;

public class NgramWorkflow extends WorkflowBase {

	public NgramWorkflow(DivisionOptions divisionOption,
			boolean glaucoma) {
		super(divisionOption, glaucoma);
		exporter = new BasicSaxArffBuilder(records);
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}
}
