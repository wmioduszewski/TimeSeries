package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.OriginalSignalNormalizedArffBuilder;
import weka.core.Instances;

public class EamonnOriginalSignalNormalizedBySaxWorkflow extends EamonnWorkflowBase{

	public EamonnOriginalSignalNormalizedBySaxWorkflow(
			DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption, isDominant);
		// TODO Auto-generated constructor stub
	}
	
	private OriginalSignalNormalizedArffBuilder exporter;

	@Override
	protected Instances buildInstances() {
		exporter = new OriginalSignalNormalizedArffBuilder(records);
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
