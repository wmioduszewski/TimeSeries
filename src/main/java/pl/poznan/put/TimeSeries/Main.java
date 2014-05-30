package pl.poznan.put.TimeSeries;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.Classifying.NgramClassifier;
import pl.poznan.put.TimeSeries.DataOperators.DataExporterCsv;
import pl.poznan.put.TimeSeries.DataOperators.DataExporterEamonn;
import pl.poznan.put.TimeSeries.DataOperators.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataOperators.DataImporterEamonn;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Renderers.ChartBase;
import pl.poznan.put.TimeSeries.Renderers.ChartWindow;
import pl.poznan.put.TimeSeries.Renderers.CleanChart;
import pl.poznan.put.TimeSeries.Renderers.SaxChart;
import pl.poznan.put.TimeSeries.Renderers.SmoothChart;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.PatientToArffTranslator;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;
import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import edu.hawaii.jmotif.lib.ts.TSException;

public class Main {

	static int smoothingSize = 5;
	static float omega = 0.1f;
	static int ngramSize = 1;

	public static void main(String[] args) throws Exception {
		
		FlowHandler flow = new FlowHandler();
		flow.startProcessing();
		System.out.println("End.");
	}
	
	private static void importData(){
		DataImporterCsv importer = new DataImporterCsv("path");
		List<Patient> patients = null;
		
		//patients = importer.ImportData();
		DataExporterCsv exporter = new DataExporterCsv(patients);
	}
	
	private static void CsvToEamonn() throws IOException {
		String path = "C:/Users/Wojciech/Documents/studia/mgr/praca mgr/stationary data/dataset3/gTimeData.7.5.20130123a_sub.csv";
		DataImporterCsv csvImport = new DataImporterCsv(path);
		
		List<Patient> patients = csvImport.ImportData();
		
		DataExporterCsv exp = new DataExporterCsv(patients);
		exp.exportCsvToEamonnFormat("C:/Users/Wojciech/Documents/studia/mgr/praca mgr/stationary data/dataset3/Glaucoma_TEST");	
	}
}
