package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.BasicNgramClassifier;
import pl.poznan.put.TimeSeries.Classifying.RegularExperiment;
import pl.poznan.put.TimeSeries.Classifying.StringNNsearch;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Workflows.EamonnBasicNgramWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.JRip;

public class Main {

	public static void main(String[] args) throws Exception {

		DivisionOptions divisionOption = DivisionOptions.Regular;
		WorkflowBase workflow;
		ExperimentBase experiment;

		//TODO: add enum list of available classifiers

//		Classifier classifier = new BasicNgramClassifier();
		IBk ibk = new IBk();
		ibk.setNearestNeighbourSearchAlgorithm(new StringNNsearch());
		Classifier classifier = ibk;
		
		experiment = new CrossValidationExperiment(classifier);

		// workflow = new
		// PatientRegressionWorkflow(divisionOption,false);
		// workflow = new
		// EamonnRegressionWorkflow(divisionOption,false);
		// workflow = new PatientSaxWorkflow(divisionOption,false);
		
//		workflow = new EamonnRegressionWorkflow(divisionOption, false);
		workflow = new EamonnBasicNgramWorkflow(divisionOption, false);
		

		workflow.runExperiment(experiment);
//		workflow.runFileBasedExperiment(experiment);

		System.out.println("End.");
	}
}
