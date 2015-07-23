package pl.poznan.put.TimeSeries.Workflows;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.ExperimentResult;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.ArffExporterBase;
import pl.poznan.put.TimeSeries.DataImporters.Importer;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.core.Instances;

public abstract class WorkflowBase {

	private static int repetitions = CommonConfig.getInstance()
			.getCrossValidationRepetitions();

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
	protected ArffExporterBase exporter;
	protected boolean glaucoma;

	protected boolean isAttrBag;
	protected List<? extends IRecord> records;
	protected int windowLen = CommonConfig.getInstance().getNgramSize();

	public WorkflowBase(DivisionOptions divisionOption, boolean glaucoma) {
		super();
		this.divisionOption = divisionOption;
		this.glaucoma = glaucoma;
		setTempPaths();
		isAttrBag = true;
	}

	public void executeArff(ExperimentBase experiment, String arffPath) {

		System.out.println("Workflow has started.");
		try {
			ExperimentResult result = experiment.runFileExperimentRepeatedly(
					arffPath, repetitions);
			printResult(result);
		} catch (Exception e) {
			System.out.println("Error during workflow performing:");
			e.printStackTrace();
		}
		System.out.println("Workflow has ended.");
	}

	public void runExperiment(ExperimentBase experiment) throws Exception {
		importData();
		processData();
		Instances instances = buildInstances();
		ExperimentResult result = experiment.runExperimentRepeatedly(instances,
				repetitions);
		printResult(result);
	}

	public String saveArff() throws Exception {
		System.out.println("Arff creation has started.");
		try {
			importData();
			processData();
			buildInstances();
			exportArff();
			reportStatistics();
			System.out.println("Arff has been saved.");
		} catch (Exception e) {
			System.out.println("Error during arff creation/save.");
			e.printStackTrace();
			throw e;
		}
		return arffPath;
	}

	private void printResult(ExperimentResult experimentResult) {
		System.out.println();
		if (experimentResult != null)
			System.out.println(experimentResult);
		else
			System.out.println("Experiment result is null!");
		System.out.println();
	}

	protected Instances buildInstances() {
		return exporter.buildInstances();
	}

	protected void exportArff() throws Exception {
		exporter.saveArff(arffPath);
	}

	protected void importData() throws Exception {
		records = Importer.importData(glaucoma);
	}

	protected abstract void processData() throws Exception;

	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(records);
	}

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
		arffPath = String.format("output/arffOutput/%s%s%dp%dgram%s.arff",
				className, eamonnDataSource, divisionPartsAmount, windowLen,
				divisionOption.toString());
	}
}
