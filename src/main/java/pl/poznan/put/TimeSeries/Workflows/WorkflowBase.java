package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.ExperimentResult;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.ArffExporterBase;
import pl.poznan.put.TimeSeries.DataImporters.Importer;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Util.Config;
import weka.core.Instances;

public abstract class WorkflowBase {

	private static final String NEWLINE = System.getProperty("line.separator");

	private static int repetitions = Config.getInstance()
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
	protected int divisionPartsAmount = Config.getInstance()
			.getDivisionPartsAmount();
	protected ArffExporterBase exporter;
	protected boolean isGlaucoma;

	protected List<? extends IRecord> records;
	protected int windowLen = Config.getInstance().getNgramSize();
	protected List<Pair<String, ? extends Object>> concerningParameters = new ArrayList<Pair<String, ? extends Object>>();

	public WorkflowBase(DivisionOptions divisionOption, boolean isGlaucoma) {
		super();
		this.divisionOption = divisionOption;
		this.isGlaucoma = isGlaucoma;
	}

	public String executeArff(ExperimentBase experiment, String arffPath)
			throws Exception {
		ExperimentResult result = experiment.runFileExperimentRepeatedly(
				arffPath, repetitions);
		return constructResult(result, arffPath);
	}

	public String runExperiment(ExperimentBase experiment) throws Exception {
		importData();
		processData();
		Instances instances = buildInstances();
		ExperimentResult result = experiment.runExperimentRepeatedly(instances,
				repetitions);
		reportStatistics();
		return constructResult(result);
	}

	public String saveArff() throws Exception {
		setArffPath();
		importData();
		processData();
		buildInstances();
		exportArff();
		reportStatistics();
		return arffPath;
	}

	private String constructResult(ExperimentResult experimentResult,
			String filePath) {
		StringBuilder sb = new StringBuilder();
		String experiment = Config.getInstance().getCurrentExperiment().name();
		sb.append(experiment + " for " + filePath + NEWLINE);
		sb.append(experimentResult);
		return sb.toString();
	}

	private String constructResult(ExperimentResult experimentResult) {
		String dataSource = Config.getInstance().getCurrentDataset().name();
		String experiment = Config.getInstance().getCurrentExperiment().name();
		setConcerningParams();
		StringBuilder sb = new StringBuilder();
		sb.append(experiment + " for " + dataSource + " with params:" + NEWLINE);
		for (Pair<String, ? extends Object> pair : concerningParameters) {
			sb.append(pair.getLeft() + ": " + pair.getRight() + NEWLINE);
		}
		sb.append(NEWLINE);
		if (experimentResult != null)
			sb.append(experimentResult);
		else
			sb.append("Experiment result is null!" + NEWLINE + NEWLINE);
		return sb.toString();
	}

	protected abstract Instances buildInstances();

	protected void exportArff() throws Exception {
		exporter.saveArff(arffPath);
	}

	protected void importData() throws Exception {
		records = Importer.importData(isGlaucoma);
	}

	protected abstract void processData() throws Exception;

	protected abstract void setConcerningParams();

	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(records);
	}

	private void setArffPath() {
		setConcerningParams();
		String dataSource = Config.getInstance().getCurrentDataset().name();
		String experiment = Config.getInstance().getCurrentExperiment().name();

		arffPath = String.format("output/arffOutput/%s/%s for %s with",
				experiment, experiment, dataSource);

		for (Pair<String, ? extends Object> pair : concerningParameters) {
			arffPath += " " + pair.getLeft() + "-" + pair.getRight();
		}

		arffPath += ".arff";
	}
}
