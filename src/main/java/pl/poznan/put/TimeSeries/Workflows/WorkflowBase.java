package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;

public abstract class WorkflowBase {

	protected Classifier classifier = new JRip();
	
	protected int regularPartsForDivision = Integer.parseInt(Configuration.getProperty("regularPartsForDivision"));

	protected String tempTrainPath;
	protected String tempTestPath;

	protected abstract void importData();

	protected abstract void processData();

	protected void runExperiment(){
		try {
			//double res = Experiment.runExperiment(classifier, tempTrainPath, tempTestPath);
			int folds = 10;
			double partOfDataSet = 1;
			long seed = 1000;
			CrossValidationExperiment.runCVExperiment(classifier, tempTestPath, folds, partOfDataSet, seed);
			//System.out.println("The result for " + this.getClass().getSimpleName() +" is: " + res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void reportResult();

	protected abstract void setTempPaths();

	public WorkflowBase() {
		super();
		setTempPaths();
	}

	public void runWorkflow() {
		System.out.println("Workflow has started.");
		importData();
		processData();
		runExperiment();
		reportResult();
		System.out.println("Workflow has ended.");
	}

}
