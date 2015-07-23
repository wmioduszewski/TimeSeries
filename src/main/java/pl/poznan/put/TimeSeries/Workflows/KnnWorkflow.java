package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.OriginalSignalNormalizedArffBuilder;

public class KnnWorkflow extends WorkflowBase {

	public KnnWorkflow(
			DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
		exporter = new OriginalSignalNormalizedArffBuilder(records);
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}
}
