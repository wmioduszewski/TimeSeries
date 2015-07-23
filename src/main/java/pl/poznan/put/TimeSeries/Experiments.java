package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.BasicNgramClassifier;
import pl.poznan.put.TimeSeries.Classifying.DtwSearch;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Workflows.CountedWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnBasicNgramWorkflow;
import pl.poznan.put.TimeSeries.Workflows.DominantWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnOriginalSignalNormalizedBySaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnOriginalSignalWorkflow;
import pl.poznan.put.TimeSeries.Workflows.RegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

public enum Experiments {
	REGRESSION, DOMINANANT, COUNTED, NGRAM, KNN, DTW;

	public WorkflowBase getWorkflow() {
		WorkflowBase workflow = null;
		DivisionOptions divisionOption = DivisionOptions.Regular;
		boolean glaucoma = false;
		switch (this) {
		case REGRESSION:
			workflow = new RegressionWorkflow(divisionOption, glaucoma);
			break;
		case DOMINANANT:
			workflow = new DominantWorkflow(divisionOption, glaucoma);
			break;
		case COUNTED:
			workflow = new CountedWorkflow(divisionOption, glaucoma);
			break;
		case NGRAM:
			workflow = new EamonnBasicNgramWorkflow(divisionOption, glaucoma);
			break;
		case KNN:
//			workflow = new EamonnOriginalSignalWorkflow(divisionOption, glaucoma);
			workflow = new EamonnOriginalSignalNormalizedBySaxWorkflow(divisionOption, glaucoma);
			break;
		case DTW:
			workflow = new EamonnOriginalSignalWorkflow(divisionOption, glaucoma);
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
		case DOMINANANT:
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
