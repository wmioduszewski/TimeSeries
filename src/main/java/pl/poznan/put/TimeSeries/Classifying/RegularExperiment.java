package pl.poznan.put.TimeSeries.Classifying;

import org.apache.commons.lang3.tuple.Pair;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class RegularExperiment extends ExperimentBase {

	public RegularExperiment(Classifier classifier) {
		super(classifier);
	}

	@Override
	public ExperimentResult runExperiment(Instances instances,
			double trainToTestRatio) throws Exception {

		Pair<Instances, Instances> dividedInstances = Utils.divideInstaces(
				instances, trainToTestRatio);
		return runExperiment(dividedInstances.getLeft(),
				dividedInstances.getRight());
	}

	@Override
	public ExperimentResult runFileExperiment(String pathToArff,
			double trainToTestRatio) throws Exception {

		Instances dataSet = Utils.readInstances(pathToArff);
		return runExperiment(dataSet, trainToTestRatio);
	}

	private ExperimentResult runExperiment(Instances trainSet, Instances testSet)
			throws Exception {
		if (trainSet.classIndex() == -1)
			trainSet.setClassIndex(trainSet.numAttributes() - 1);
		if (testSet.classIndex() == -1)
			testSet.setClassIndex(testSet.numAttributes() - 1);

		classifier.buildClassifier(trainSet);

		double correct = 0;
		for (int i = 0; i < testSet.numInstances(); i++) {
			Instance instance = testSet.instance(i);
			int truth = (int) instance.classValue();
			int prediction = (int) classifier.classifyInstance(instance);

			if (truth == prediction)
				correct++;
		}
		double accuracy = correct / testSet.numInstances();

		ExperimentResult result = new ExperimentResult(accuracy, -1,-1,-1,-1,-1);
		return result;
	}
}
