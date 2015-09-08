package pl.poznan.put.TimeSeries.Workflows;

import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.OriginalSignalArffBuilder;
import pl.poznan.put.TimeSeries.Util.Config;
import weka.core.Instances;

public class DtwWorkflow extends WorkflowBase {

	public DtwWorkflow(DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
	}

	@Override
	protected Instances buildInstances() {
		exporter = new OriginalSignalArffBuilder(records);
		return exporter.buildInstances();
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}

	@Override
	protected void setConcerningParams() {
		concerningParameters.add(Pair.of("knn",Config.getInstance().getK()));
		concerningParameters.add(Pair.of("DtwWin",Config.getInstance().getDtwSearchRadius()));
	}
}
