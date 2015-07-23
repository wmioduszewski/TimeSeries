package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.OriginalSignalArffBuilder;

public class DtwWorkflow extends WorkflowBase {

	public DtwWorkflow(DivisionOptions divisionOption,
			boolean glaucoma) {
		super(divisionOption, glaucoma);
		exporter = new OriginalSignalArffBuilder(records);
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}
}
