package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.BasicNgramClassifier;
import pl.poznan.put.TimeSeries.Classifying.DtwSearch;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Workflows.EamonnBasicNgramWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnOriginalSignalWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

public enum Experiments {
	REGRESSION, DOMINANANT, NONDOMINANT, NGRAM, KNN, DTW;

	public WorkflowBase getWorkflow() {
		WorkflowBase workflow = null;
		DivisionOptions divisionOption = DivisionOptions.Regular;
		switch (this) {
		case REGRESSION:
			workflow = new EamonnRegressionWorkflow(divisionOption, false);
			break;
		case DOMINANANT:
			workflow = new EamonnSaxWorkflow(divisionOption, true);
			break;
		case NONDOMINANT:
			workflow = new EamonnSaxWorkflow(divisionOption, false);
			break;
		case NGRAM:
			workflow = new EamonnBasicNgramWorkflow(divisionOption, false);
			break;
		case KNN:
			workflow = new EamonnOriginalSignalWorkflow(divisionOption, false);
			break;
		case DTW:
			workflow = new EamonnOriginalSignalWorkflow(divisionOption, false);
			break;
		}
		return workflow;
	}

	public Classifier getClassifier() {
		Classifier classifier = null;
		switch (this) {
		case REGRESSION:
			classifier = new J48();
			break;
		case DOMINANANT:
			classifier = new J48();
			break;
		case NONDOMINANT:
			classifier = new J48();
			break;
		case NGRAM:
			classifier = new BasicNgramClassifier();
			break;
		case KNN:
			classifier = new IBk();
			break;
		case DTW:
			IBk ibk = new IBk();
			ibk.setNearestNeighbourSearchAlgorithm(new DtwSearch());
			classifier = ibk;
			break;
		}
		return classifier;
	}
}
