package pl.poznan.put.TimeSeries.Classifying;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class CrossValidationExperiment extends ExperimentBase {

	private int folds = 10;

	public CrossValidationExperiment(Classifier classifier) {
		super(classifier);
	}

	public int getFolds() {
		return folds;
	}

	public ExperimentResult runExperiment(Instances baseDataSet,
			double partOfDataSet, long seed) throws Exception {
		baseDataSet.randomize(new Random(seed));
		Instances dataSet = new Instances(baseDataSet, 0,
				(int) ((double) baseDataSet.numInstances() * partOfDataSet));

		if (dataSet.classIndex() == -1)
			dataSet.setClassIndex(dataSet.numAttributes() - 1);

		double accuracy = 0;
		double squaredError = 0;
		
		for (int n = 0; n < folds; n++) {
			Instances train = dataSet.trainCV(folds, n);
			Instances test = dataSet.testCV(folds, n);

			Classifier classifierCopy = Classifier.makeCopy(classifier);
			classifierCopy.buildClassifier(train);

			double _squaredError = 0;
			double _accuracy=0;
			for (int i = 0; i < test.numInstances(); i++) {
				Instance instance = test.instance(i);

				int truth = (int) instance.classValue();
				double[] distribution = classifierCopy
						.distributionForInstance(instance);
				int prediction = distribution[1] >= distribution[0] ? 1 : 0;

				if (truth == prediction)
					_accuracy++;
				_squaredError += Math.pow(1.0 - distribution[truth], 2);
			}

			_accuracy /= (double) test.numInstances();
			_squaredError /= (double) test.numInstances();

			accuracy += _accuracy;
			squaredError += _squaredError;
		}

		accuracy /= (double) folds;
		squaredError /= (double) folds;
		ExperimentResult result = new ExperimentResult(accuracy, squaredError);
		return result;
	}

	public void setFolds(int folds) {
		this.folds = folds;
	}

	@Override
	public ExperimentResult runFileExperiment(String pathToArff,
			double trainToTestRatio, long seed) throws Exception {
		Instances dataSet = Utils.readInstances(pathToArff);
		return runExperiment(dataSet, trainToTestRatio, seed);
	}
}
