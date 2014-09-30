package pl.poznan.put.TimeSeries.Workflows;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.Classifying.NgramClassifier;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;

public abstract class WorkflowBase {

	public static <T extends IRecord> void reportInputStatistics(List<T> records) {
		List<Double> distinctClasses = records.stream()
				.map(x -> x.getDestinationClass()).distinct()
				.collect(Collectors.toList());
		System.out.println(String.format("Data contains %d classes:",
				distinctClasses.size()));
		for (Double distClass : distinctClasses) {
			long count = records.stream()
					.filter(x -> x.getDestinationClass() == distClass).count();
			System.out.println(String.format("Class '%d' contains %d records",
					distClass.intValue(), count));
		}
	}

	protected Classifier classifier = new JRip();
	protected int divisionPartsAmount = Integer.parseInt(Configuration
			.getProperty("divisionPartsAmount"));

	protected int windowLen = Integer.parseInt(Configuration
			.getProperty("ngramSize"));
	protected String arffTrainPath;
	protected String arffTestPath;
	protected String arffCVpath;
	protected List<IRecord> recs;
	protected DivisionOptions divisionOption;
	protected boolean isDominant;
	protected boolean isAttrBag;
	
	protected List <? extends IRecord> recordy;

	public WorkflowBase(DivisionOptions divisionOption, boolean isDominant) {
		super();
		this.divisionOption = divisionOption;
		this.isDominant = isDominant;
		setTempPaths();
		isAttrBag = true;
	}

	protected abstract void exportArff() throws Exception;

	protected abstract void importData();

	protected abstract void processData() throws Exception;

	protected abstract void reportStatistics();

	protected void runCrossValidationExperiment() {
		try {
			int folds = 10;
			double partOfDataSet = 1;
			long seed = 1000;
			CrossValidationExperiment.runCVExperiment(classifier, arffCVpath,
					folds, partOfDataSet, seed);
		} catch (Exception e) {
			System.out.println("Experiment failed.");
			e.printStackTrace();
		}
	}

	protected void runExperiment() {
		try {
			double res = Experiment.runExperiment(classifier, arffTrainPath,
					arffTestPath);
			System.out.println("The result for "
					+ this.getClass().getSimpleName() + " is: " + res);
		} catch (Exception e) {
			System.out.println("Experiment failed.");
			e.printStackTrace();
		}
	}

	public void runWorkflow() {

		System.out.println("Workflow has started.");
		try {
			importData();
			processData();
			exportArff();
			reportStatistics();
			runCrossValidationExperiment();
		} catch (Exception e) {
			System.out.println("Error during workflow performing:");
			e.printStackTrace();
		}
		System.out.println("Workflow has ended.");
	}

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
					eamonnDataSource.length());
		}
		String dominant = isDominant ? "dominant" : "non-dominant";
		arffCVpath = String.format("output/arffOutput/%s%s%dp%dgram%s %s.arff",
				className, eamonnDataSource, divisionPartsAmount, windowLen,
				divisionOption.toString(),dominant);
	}

}
