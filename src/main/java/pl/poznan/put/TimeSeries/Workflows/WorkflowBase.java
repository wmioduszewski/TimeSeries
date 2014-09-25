package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;

public abstract class WorkflowBase {

	protected Classifier classifier = new JRip();

	protected int regularPartsForDivision = Integer.parseInt(Configuration
			.getProperty("regularPartsForDivision"));
	protected int windowLen = Integer.parseInt(Configuration
			.getProperty("ngramSize"));

	protected String tempTrainPath;
	protected String tempTestPath;
	protected String tempCVpath;

	protected abstract void importData();

	protected abstract void processData();

	protected void runExperiment() {
		try {
			double res = Experiment.runExperiment(classifier, tempTrainPath,
					tempTestPath);
			System.out.println("The result for "
					+ this.getClass().getSimpleName() + " is: " + res);
		} catch (Exception e) {
			System.out.println("Experiment failed.");
			e.printStackTrace();
		}
	}

	protected void runCrossValidationExperiment() {
		try {
			int folds = 10;
			double partOfDataSet = 1;
			long seed = 1000;
			CrossValidationExperiment.runCVExperiment(classifier, tempCVpath,
					folds, partOfDataSet, seed);
		} catch (Exception e) {
			System.out.println("Experiment failed.");
			e.printStackTrace();
		}
	}

	protected abstract void reportResult();

	protected void setTempPaths() {
		String className = this.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1,
				className.length());
		className = className.replace("Workflow", "");
		String eamonnDataSource = "";
		if (className.contains("Eamonn")) {
			eamonnDataSource = Configuration.getProperty("singleDataPath")
					+ " ";
			eamonnDataSource = eamonnDataSource.substring(
					eamonnDataSource.lastIndexOf("/") + 1,
					eamonnDataSource.lastIndexOf("_"));
		}
		tempCVpath = String
				.format("output/arffOutput/%s%s%dp%dgram.arff", className,
						eamonnDataSource, regularPartsForDivision, windowLen);
	}

	public WorkflowBase() {
		super();
		setTempPaths();
	}

	public void runWorkflow() {

		System.out.println("Workflow has started.");
		importData();
		processData();
		runCrossValidationExperiment();
		reportResult();
		System.out.println("Workflow has ended.");

	}

}
