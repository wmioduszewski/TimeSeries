package pl.poznan.put.TimeSeries.Workflows;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.ExperimentResult;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.core.Instances;

public abstract class WorkflowBase {

	private static long seed = 1000;

	private static double trainToTestRatio = CommonConfig.getInstance()
			.getTrainToTestRatio();

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

	protected String arffPath;
	protected DivisionOptions divisionOption;
	protected int divisionPartsAmount = CommonConfig.getInstance()
			.getDivisionPartsAmount();
	protected boolean isAttrBag;
	protected boolean isDominant;

	protected List<IRecord> records;
	protected int windowLen = CommonConfig.getInstance().getNgramSize();

	public WorkflowBase(DivisionOptions divisionOption, boolean isDominant) {
		super();
		this.divisionOption = divisionOption;
		this.isDominant = isDominant;
		setTempPaths();
		isAttrBag = true;
	}

	public void runExperiment(ExperimentBase experiment) throws Exception {
		importData();
		processData();
		Instances instances = buildInstances();
		ExperimentResult result = experiment.runExperiment(instances,
				trainToTestRatio, seed);
		printResult(result);
	}

	public void runFileBasedExperiment(ExperimentBase experiment) {

		System.out.println("Workflow has started.");
		try {
			importData();
			processData();
			buildInstances();
			exportArff();
			reportStatistics();
			ExperimentResult result = experiment.runFileExperiment(arffPath,
					trainToTestRatio, seed);
			printResult(result);
		} catch (Exception e) {
			System.out.println("Error during workflow performing:");
			e.printStackTrace();
		}

		System.out.println("Workflow has ended.");
	}

	private void printResult(ExperimentResult experimentResult) {
		System.out.println();
		if (experimentResult != null)
			System.out.println(experimentResult);
		else
			System.out.println("Experiment result is null!");
		System.out.println();
	}

	protected abstract Instances buildInstances();

	protected abstract void exportArff() throws Exception;

	protected abstract void importData();

	protected abstract void processData() throws Exception;

	protected abstract void reportStatistics();

	protected void setTempPaths() {
		String className = this.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1,
				className.length());
		className = className.replace("Workflow", "");
		String eamonnDataSource = "";
		if (className.contains("Eamonn")) {
			eamonnDataSource = CommonConfig.getInstance().getSingleDataPath()
					+ " ";
			eamonnDataSource = eamonnDataSource.substring(
					eamonnDataSource.lastIndexOf("/") + 1,
					eamonnDataSource.length());
		}
		String dominant = isDominant ? "dominant" : "non-dominant";
		arffPath = String.format("output/arffOutput/%s%s%dp%dgram%s %s.arff",
				className, eamonnDataSource, divisionPartsAmount, windowLen,
				divisionOption.toString(), dominant);
	}
}
