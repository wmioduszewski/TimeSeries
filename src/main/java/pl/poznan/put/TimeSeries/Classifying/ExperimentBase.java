package pl.poznan.put.TimeSeries.Classifying;

import weka.classifiers.Classifier;
import weka.core.Instances;

public abstract class ExperimentBase {

	protected Classifier classifier;

	public ExperimentBase(Classifier classifier) {
		this.classifier = classifier;
	}

	public abstract ExperimentResult runExperiment(Instances instances) throws Exception;

	public ExperimentResult runExperimentRepeatedly(Instances baseDataSet, int times) throws Exception {
		ExperimentResult result = new ExperimentResult(0, 0, 0, 0, 0, 0);
		for (int i = 0; i < times; i++) {
			result.add(runExperiment(baseDataSet));
		}
		result.divideBy(times);
		return result;
	}

	public abstract ExperimentResult runFileExperiment(String pathToArff) throws Exception;

	public abstract ExperimentResult runFileExperimentRepeatedly(
			String pathToArff, int times)
			throws Exception;
}
