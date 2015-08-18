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
	protected boolean isGlaucoma;

	protected List<? extends IRecord> records;
	protected int windowLen = CommonConfig.getInstance().getNgramSize();
	protected List<Pair<String, ? extends Object>> concerningParameters = new ArrayList<Pair<String, ? extends Object>>();

	public WorkflowBase(DivisionOptions divisionOption, boolean isGlaucoma) {
		super();
		this.divisionOption = divisionOption;
		this.isGlaucoma = isGlaucoma;
	}

	public void executeArff(ExperimentBase experiment, String arffPath)
			throws Exception {
		ExperimentResult result = experiment.runFileExperimentRepeatedly(
				arffPath, repetitions);
		printResult(result);
	}

	public void runExperiment(ExperimentBase experiment) throws Exception {
		importData();
		processData();
		Instances instances = buildInstances();
		ExperimentResult result = experiment.runExperimentRepeatedly(instances,
				repetitions);
		reportStatistics();
		printResult(result);
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

	private void printResult(ExperimentResult experimentResult) {
		System.out.println();
		if (experimentResult != null)
			System.out.println(experimentResult);
		else
			System.out.println("Experiment result is null!");
		System.out.println();
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
		String dataSource = CommonConfig.getInstance().getCurrentDataset()
				.name();
		String experiment = CommonConfig.getInstance().getCurrentExperiment()
				.name();

		arffPath = String.format("output/arffOutput/%s for %s with",
				experiment, dataSource);

		for (Pair<String, ? extends Object> pair : concerningParameters) {
			arffPath += " " + pair.getLeft() + "-" + pair.getRight();
		}

		arffPath += ".arff";
	}
}
