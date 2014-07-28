package pl.poznan.put.TimeSeries.Workflows;

import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;

public abstract class WorkflowBase {

	protected Classifier classifier = new Logistic();

	protected String tempTrainPath;
	protected String tempTestPath;

	protected abstract void importData();

	protected abstract void processData();

	protected abstract void runExperiment();

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
