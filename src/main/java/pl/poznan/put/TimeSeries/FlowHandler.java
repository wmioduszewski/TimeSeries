package pl.poznan.put.TimeSeries;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.DataOperators.DataExporterEamonn;
import pl.poznan.put.TimeSeries.DataOperators.DataImporterEamonn;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Reporting.EntireReport;
import pl.poznan.put.TimeSeries.Reporting.ReportRecord;
import pl.poznan.put.TimeSeries.Reporting.ResultReporter;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.FileLister;
import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;

public class FlowHandler {

	private String folderPath;
	private boolean isFolder;
	private String[] datasetNames;
	private String[] modes = { "_TRAIN", "_TEST" };
	private List<Classifier> classifiers;

	// private LinkedList<>

	public FlowHandler() {
		this.isFolder = Configuration.getProperty("isFolderDataUsed").equals(
				"1") ? true : false;
		this.folderPath = Configuration.getProperty("folderDataPath");
		
		classifiers = new LinkedList<Classifier>();
		classifiers.add(new J48());
		classifiers.add(new JRip());
	}

	public void startProcessing() throws Exception {
		determineProcessingPaths();
		
		EntireReport report = new EntireReport();
		
		for(int i=0;i<classifiers.size();i++){
			Classifier classifier = classifiers.get(i);
			report.AddClassifier(classifier.getClass().getName());
		}
		
		for (String datasetName : datasetNames) {
			for (String mode : modes) {
				DataImporterEamonn eamonnImporter = new DataImporterEamonn(
						folderPath + "/" + datasetName + "/" + datasetName
								+ mode);
				List<UnifiedRecordType> data = eamonnImporter
						.ImportEamonnData();
				DataExporterEamonn eamonnExporter = new DataExporterEamonn(data);
				eamonnExporter.ConstructArff("temp/" + datasetName + mode
						+ ".arff");
			}
			
			ReportRecord record = new ReportRecord(datasetName);
			
			for(int i=0;i<classifiers.size();i++){
				Classifier classifier = classifiers.get(i);
				
				double accuracy = Experiment.runExperiment(classifier, "temp/"
					+ datasetName + "_TRAIN.arff", "temp/" + datasetName
					+ "_TEST.arff");
				record.AddAccuracy(accuracy);
				
				System.out.println(datasetName + "\t\t\tclassyfying accuracy: "
						+ accuracy);	
			}
			report.AddReportRecord(record);
		}
		deleteTempFiles();
		ResultReporter reporter = new ResultReporter(report);
		reporter.constructReport();
	}

	private void determineProcessingPaths() {
		if (isFolder)
			datasetNames = FileLister.getDatasetPaths(folderPath);
		else {
			datasetNames = new String[1];
			datasetNames[0] = Configuration.getProperty("singleDataPath");
		}
	}
	
	private void deleteTempFiles(){
		File folder = new File("temp/");
		for (File file : folder.listFiles()) {
			file.delete();
		}
	}
	
}
