package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.BasicNgramClassifier;
import pl.poznan.put.TimeSeries.Classifying.DtwSearch;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Workflows.EamonnBasicNgramWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnDominantWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnOriginalSignalNormalizedBySaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnOriginalSignalWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnCountedWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientCountedWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

public enum Experiments {
	REGRESSION, DOMINANANT, COUNTED, NGRAM, KNN, DTW, PATIENTCOUNTED;

	public WorkflowBase getWorkflow() {
		WorkflowBase workflow = null;
		DivisionOptions divisionOption = DivisionOptions.Regular;
		switch (this) {
		case REGRESSION:
			workflow = new EamonnRegressionWorkflow(divisionOption);
			break;
		case DOMINANANT:
			workflow = new EamonnDominantWorkflow(divisionOption);
			break;
		case COUNTED:
			workflow = new EamonnCountedWorkflow(divisionOption);
			break;
		case NGRAM:
			workflow = new EamonnBasicNgramWorkflow(divisionOption);
			break;
		case KNN:
//			workflow = new EamonnOriginalSignalWorkflow(divisionOption);
			workflow = new EamonnOriginalSignalNormalizedBySaxWorkflow(divisionOption);
			break;
		case DTW:
			workflow = new EamonnOriginalSignalWorkflow(divisionOption);
			break;
		case PATIENTCOUNTED:
			workflow = new PatientCountedWorkflow(divisionOption);
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
		case PATIENTCOUNTED:
			classifier = new J48();
			break;
		}
		return classifier;
	}
}
