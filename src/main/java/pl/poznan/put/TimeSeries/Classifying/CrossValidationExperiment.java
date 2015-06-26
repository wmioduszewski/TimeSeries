package pl.poznan.put.TimeSeries.Classifying;

import java.util.Random;

import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class CrossValidationExperiment extends ExperimentBase {

	private int folds = CommonConfig.getInstance().getCrossValidationFolds();

	public CrossValidationExperiment(Classifier classifier) {
		super(classifier);
	}

	public int getFolds() {
		return folds;
	}

	@Override
	public ExperimentResult runExperiment(Instances baseDataSet,
			double partOfDataSet) throws Exception {
		// from
		// https://weka.wikispaces.com/Generating+classifier+evaluation+output+manually
		Evaluation eval = new Evaluation(baseDataSet);
		eval.crossValidateModel(classifier, baseDataSet, folds, new Random());
		return fillResult(eval, baseDataSet.classIndex());
	}

	@Override
	public ExperimentResult runFileExperiment(String pathToArff,
			double trainToTestRatio) throws Exception {
		Instances dataSet = Utils.readInstances(pathToArff);
		return runExperiment(dataSet, trainToTestRatio);
	}
	
	public void setFolds(int folds) {
		this.folds = folds;
	}

	private ExperimentResult fillResult(Evaluation eval, int classIndex) {
		double accuracy = eval.weightedPrecision();
		double sensitivity = eval.weightedTruePositiveRate();
		double specificity = eval.weightedTrueNegativeRate();
		double gMean = Math.sqrt(specificity * sensitivity);
		double pr = eval.weightedPrecision();
		double rec = eval.weightedRecall();
		double f1 = 2 * ((pr * rec) / (pr + rec));
		double pcc = eval.correct() / eval.numInstances();
		ExperimentResult result = new ExperimentResult(accuracy, sensitivity,
				specificity, gMean, f1, pcc);
		return result;
	}
}
