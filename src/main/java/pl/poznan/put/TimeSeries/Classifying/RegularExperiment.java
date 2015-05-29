package pl.poznan.put.TimeSeries.Classifying;

import org.apache.commons.lang3.tuple.Pair;

import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;
import weka.core.Instance;
import weka.core.Instances;

public class RegularExperiment extends ExperimentBase {

	public RegularExperiment(Classifier classifier) {
		super(classifier);
		// TODO Auto-generated constructor stub
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

		classifier.buildClassifier(trainSet);

		double sum = 0;
		double correct = 0;
		for (int i = 0; i < testSet.numInstances(); i++) {
			Instance instance = testSet.instance(i);
			int truth = (int) instance.classValue();
			int prediction = (int) classifier.classifyInstance(instance);
			sum++;
			if (truth == prediction)
				correct++;
		}
		double accuracy = correct / sum;

		ExperimentResult result = new ExperimentResult(accuracy, 0, 0);

		// TODO: add 01loss and squared error

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
			double trainToTestRatio, long seed) {
		
//		runExperiment("temp/"
//				+ datasetName + "_TRAIN.arff", "temp/" + datasetName
//				+ "_TEST.arff")
		
		// TODO Auto-generated method stub
		return null;
	}
}
