package pl.poznan.put.TimeSeries;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.Classifying.NgramClassifier;
import pl.poznan.put.TimeSeries.DataExporters.DataExporterCsv;
import pl.poznan.put.TimeSeries.DataExporters.DeprecatedDataExporterEamonn;
import pl.poznan.put.TimeSeries.DataExporters.PatientToArffTranslator;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterEamonn;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Renderers.ChartBase;
import pl.poznan.put.TimeSeries.Renderers.ChartWindow;
import pl.poznan.put.TimeSeries.Renderers.CleanChart;
import pl.poznan.put.TimeSeries.Renderers.SaxChart;
import pl.poznan.put.TimeSeries.Renderers.SmoothChart;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import edu.hawaii.jmotif.lib.ts.TSException;

public class Main {

	static int smoothingSize = 5;
	static float omega = 0.1f;
	static int ngramSize = 1;

	public static void main(String[] args) throws Exception {
		
//		WorkflowBase workflow = new PatientRegressionWorkflow(); 
//		WorkflowBase workflow = new EamonnRegressionWorkflow(); 
		WorkflowBase workflow = new PatientSaxWorkflow();
//		WorkflowBase workflow = new EamonnSaxWorkflow();
		
		workflow.runWorkflow();
		
		System.out.println("End.");
	}
	
	private static void runOldFlow() throws Exception{
		FlowHandler flow = new FlowHandler();
		flow.startProcessing();
	}

	private static void CsvToEamonn() throws IOException {
		String path = Configuration.getProperty("csvDataSet");
		
		DataImporterCsv csvImport = new DataImporterCsv(path);

		List<Patient> patients = csvImport.ImportData();

		DataExporterCsv exp = new DataExporterCsv(patients);
		exp.exportCsvToEamonnFormat(path);
	}
}
