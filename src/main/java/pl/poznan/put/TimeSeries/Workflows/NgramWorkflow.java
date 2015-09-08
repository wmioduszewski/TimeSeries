package pl.poznan.put.TimeSeries.Workflows;

import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.BasicSaxArffBuilder;
import pl.poznan.put.TimeSeries.Util.Config;
import weka.core.Instances;

public class NgramWorkflow extends WorkflowBase {

	public NgramWorkflow(DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
	}

	@Override
	protected Instances buildInstances() {
		exporter = new BasicSaxArffBuilder(records);
		return exporter.buildInstances();
	}

	@Override
	protected void processData() throws Exception {
		// nothing to do here - passing clean input further
	}

	@Override
	protected void setConcerningParams() {
		concerningParameters.add(Pair.of("ngram", windowLen));
		concerningParameters.add(Pair.of("alpha", Config.getInstance().getSaxAlphabeatSize()));
	}
}
