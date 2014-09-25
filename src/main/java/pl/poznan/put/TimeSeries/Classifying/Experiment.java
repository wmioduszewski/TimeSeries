package pl.poznan.put.TimeSeries.Classifying;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class Experiment {

	public static double runExperiment(Classifier classifier,
			String trainSetPath, String testSetPath) throws Exception {
		BufferedReader readerTrain = new BufferedReader(new FileReader(
				trainSetPath));
		Instances trainSet = new Instances(readerTrain);
		readerTrain.close();

		BufferedReader readerTest = new BufferedReader(new FileReader(
				testSetPath));
		Instances testSet = new Instances(readerTest);
		readerTest.close();

		if (trainSet.classIndex() == -1)
			trainSet.setClassIndex(trainSet.numAttributes() - 1);
		if (testSet.classIndex() == -1)
			testSet.setClassIndex(testSet.numAttributes() - 1);

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
		return accuracy;
	}
}
