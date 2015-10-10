package pl.poznan.put.TimeSeries.Classifying;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Util.Config;
import pl.poznan.put.TimeSeries.Workflows.BaggingWorkflow;
import pl.poznan.put.TimeSeries.Workflows.CountedWorkflow;
import pl.poznan.put.TimeSeries.Workflows.NgramWorkflow;
import pl.poznan.put.TimeSeries.Workflows.DominantWorkflow;
import pl.poznan.put.TimeSeries.Workflows.KnnWorkflow;
import pl.poznan.put.TimeSeries.Workflows.DtwWorkflow;
import pl.poznan.put.TimeSeries.Workflows.RegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;

public enum Experiments {
	REGRESSION, DOMINANT, COUNTED, NGRAM, KNN, DTW, BAGGING;

	public WorkflowBase getWorkflow() {
		WorkflowBase workflow = null;
		DivisionOptions divisionOption = DivisionOptions.Regular;
		boolean isGlaucoma = Config.getInstance().getCurrentDataset() == Datasets.PATIENTS;
		switch (this) {
		case REGRESSION:
			workflow = new RegressionWorkflow(divisionOption, isGlaucoma);
			break;
		case DOMINANT:
			workflow = new DominantWorkflow(divisionOption, isGlaucoma);
			break;
		case COUNTED:
			workflow = new CountedWorkflow(divisionOption, isGlaucoma);
			break;
		case NGRAM:
			workflow = new NgramWorkflow(divisionOption, isGlaucoma);
			break;
		case KNN:
			workflow = new KnnWorkflow(divisionOption, isGlaucoma);
			break;
		case DTW:
			workflow = new DtwWorkflow(divisionOption, isGlaucoma);
			break;
		case BAGGING:
			workflow = new BaggingWorkflow(divisionOption, isGlaucoma);
		}
		return workflow;
	}
	
	private static Classifier getBaggingClassifier(){
		Bagging b = new Bagging();
		b.setSeed(64);
		b.setClassifier(new J48());
		J48 j48 = new J48();
		j48.setReducedErrorPruning(true);
		b.setNumIterations(50);
		b.setBagSizePercent(20);
		return b;
	}

	public Classifier getClassifier() {
		Classifier classifier = null;
		int k = Config.getInstance().getK();
		switch (this) {
		case REGRESSION:
			classifier = new J48();						
			break;
		case DOMINANT:
			classifier = new J48();
			break;
		case COUNTED:
			classifier = new J48();
			break;
		case NGRAM:
			classifier = new BasicNgramClassifier();
			break;
		case KNN:
			classifier = new IBk(k);
			break;
		case DTW:
			IBk ibk = new IBk(k);
			ibk.setNearestNeighbourSearchAlgorithm(new DtwSearch());
			classifier = ibk;
			break;		
		case BAGGING:
			classifier = getBaggingClassifier();
			break;
		}
		return classifier;
	}
}
