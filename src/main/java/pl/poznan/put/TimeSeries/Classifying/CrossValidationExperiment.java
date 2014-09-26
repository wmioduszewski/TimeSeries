package pl.poznan.put.TimeSeries.Classifying;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class CrossValidationExperiment {

	public static void runCVExperiment(Classifier classifier,
			String dataSetPath, int folds, double partOfDataSet, long seed)
			throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(dataSetPath));
		Instances dataSetTmp = new Instances(reader);
		dataSetTmp.randomize(new Random(seed));
		Instances dataSet = new Instances(dataSetTmp, 0,
				(int) ((double) dataSetTmp.numInstances() * partOfDataSet));
		reader.close();

		if (dataSet.classIndex() == -1)
			dataSet.setClassIndex(dataSet.numAttributes() - 1);

//		System.out.println("Data loaded. Data set size: "
//				+ dataSet.numInstances());

		double loss01 = 0;
		double squaredError = 0;
		for (int n = 0; n < folds; n++) {
//			System.out.println("Cross-Validation: fold " + (n + 1) + "/"
//					+ folds);

			Instances train = dataSet.trainCV(folds, n);
			Instances test = dataSet.testCV(folds, n);

			Classifier classifierCopy = Classifier.makeCopy(classifier);
			classifierCopy.buildClassifier(train);

			double _loss01 = 0;
			double _squaredError = 0;
			for (int i = 0; i < test.numInstances(); i++) {
				Instance instance = test.instance(i);

				int truth = (int) instance.classValue();
				double[] distribution = classifierCopy
						.distributionForInstance(instance);
				int prediction = distribution[1] >= distribution[0] ? 1 : 0;

				_loss01 += truth == prediction ? 0 : 1;
				_squaredError += Math.pow(1.0 - distribution[truth], 2);
			}

			_loss01 /= (double) test.numInstances();
			_squaredError /= (double) test.numInstances();

			loss01 += _loss01;
			squaredError += _squaredError;
		}

		loss01 /= (double) folds;
		squaredError /= (double) folds;

		System.out.println("CV evaluation for "
				+ classifier.getClass().getSimpleName());
		System.out.println(" - 0/1 loss:           " + loss01);
		System.out.println(" - squared-error loss: " + squaredError);
		System.out.println("--------------------------------------");
	}
}
