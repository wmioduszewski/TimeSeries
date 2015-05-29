package pl.poznan.put.TimeSeries.Classifying;

import weka.classifiers.Classifier;
import weka.core.Instances;

public abstract class ExperimentBase {

	protected Classifier classifier;

	public ExperimentBase(Classifier classifier) {
		this.classifier = classifier;
	}

	public abstract ExperimentResult runExperiment(Instances instances,
			double trainToTestRatio, long seed) throws Exception;

	public abstract ExperimentResult runFileExperiment(String pathToArff,
			double trainToTestRatio, long seed) throws Exception;

}
