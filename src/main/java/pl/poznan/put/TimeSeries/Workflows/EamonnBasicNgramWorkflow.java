package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.BasicSaxArffBuilder;
import weka.core.Instances;

public class EamonnBasicNgramWorkflow extends WorkflowBase {

	public EamonnBasicNgramWorkflow(DivisionOptions divisionOption,
			boolean glaucoma) {
		super(divisionOption, glaucoma);
	}

	private BasicSaxArffBuilder exporter;

	@Override
	protected Instances buildInstances() {
		exporter = new BasicSaxArffBuilder(records);
		return exporter.buildInstances();
	}

	@Override
	protected void exportArff() throws Exception {
		exporter.saveArff(arffPath);
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}
}
