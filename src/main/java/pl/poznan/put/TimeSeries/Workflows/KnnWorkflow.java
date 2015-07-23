package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.BasicSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataExporters.OriginalSignalNormalizedArffBuilder;
import weka.core.Instances;

public class KnnWorkflow extends WorkflowBase {

	public KnnWorkflow(DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
	}

	@Override
	protected Instances buildInstances() {
		exporter = new OriginalSignalNormalizedArffBuilder(records);
		return exporter.buildInstances();
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}
}
