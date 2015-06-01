package pl.poznan.put.TimeSeries.Classifying;

import org.apache.commons.lang3.tuple.Pair;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class RegularExperiment extends ExperimentBase {

	public RegularExperiment(Classifier classifier) {
		super(classifier);
	}

	public ExperimentResult runExperiment(Instances instances,
			double trainToTestRatio, long seed) throws Exception {

		Pair<Instances, Instances> dividedInstances = Utils.divideInstaces(
				instances, trainToTestRatio, seed);
		return runExperiment(dividedInstances.getLeft(),
				dividedInstances.getRight());
	}

	public ExperimentResult runExperiment(Instances trainSet, Instances testSet)
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

		ExperimentResult result = new ExperimentResult(accuracy, -1);
		return result;
	}

	public ExperimentResult runExperiment(String trainSetPath,
			String testSetPath) throws Exception {

		Instances trainSet = Utils.readInstances(trainSetPath);
		Instances testSet = Utils.readInstances(testSetPath);
		return runExperiment(trainSet, testSet);
	}

	@Override
	public ExperimentResult runFileExperiment(String pathToArff,
			double trainToTestRatio, long seed) throws Exception {

		Instances dataSet = Utils.readInstances(pathToArff);
		return runExperiment(dataSet, trainToTestRatio, seed);
	}
}
