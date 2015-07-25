package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.BasicNgramClassifier;
import pl.poznan.put.TimeSeries.Classifying.DtwSearch;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Workflows.CountedWorkflow;
import pl.poznan.put.TimeSeries.Workflows.NgramWorkflow;
import pl.poznan.put.TimeSeries.Workflows.DominantWorkflow;
import pl.poznan.put.TimeSeries.Workflows.KnnWorkflow;
import pl.poznan.put.TimeSeries.Workflows.DtwWorkflow;
import pl.poznan.put.TimeSeries.Workflows.RegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

public enum Experiments {
	REGRESSION, DOMINANT, COUNTED, NGRAM, KNN, DTW;

	public WorkflowBase getWorkflow() {
		WorkflowBase workflow = null;
		DivisionOptions divisionOption = DivisionOptions.Regular;
		boolean isGlaucoma = CommonConfig.getInstance().isGlaucoma();
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
		}
		return workflow;
	}

	public Classifier getClassifier() {
		Classifier classifier = null;
		int k = CommonConfig.getInstance().getK();
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
		}
		return classifier;
	}
}
