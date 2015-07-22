package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.OriginalSignalArffBuilder;
import weka.core.Instances;

public class EamonnOriginalSignalWorkflow extends EamonnWorkflowBase{

	private OriginalSignalArffBuilder exporter;
	
	public EamonnOriginalSignalWorkflow(DivisionOptions divisionOption,
			boolean isDominant) {
		super(divisionOption, isDominant);
	}

	@Override
	protected Instances buildInstances() {
		exporter = new OriginalSignalArffBuilder(records);
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
