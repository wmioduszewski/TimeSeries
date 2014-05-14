package pl.poznan.put.TimeSeries.View;

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
import pl.poznan.put.TimeSeries.Sax.SaxPerformer;
import pl.poznan.put.TimeSeries.Util.PatientToArffTranslator;
import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import edu.hawaii.jmotif.lib.ts.TSException;

public class MainForm {

	static int smoothingSize = 5;
	static float omega = 0.1f;
	static String inputFilePath = "data/gTimeData.7.5.20130123a_sub.csv";
	static String chartsExportFolder = "output/charts/test/";
	static String arffExportPath = "output/patients alph10.arff";
	static String saxStringsExportpath = "output/SaxStrings alph 10.txt";
	static String trainSetPath ="data/lighting2 train.arff";
	static String testSetPath = "data/lighting2 test.arff";
	static int ngramSize = 1;

	public static void main(String[] args) throws Exception {

		DataImporterCsv importer = new DataImporterCsv(inputFilePath);
		List<Patient> patients = null;
		
		//patients = importer.ImportData();
		

		DataExporterCsv exporter = new DataExporterCsv(patients);
		ChartBase currentChart;
		// currentChart = new CleanChart();
		// currentChart = new SmoothChart(omega);
		// currentChart = new SmoothChart(smoothingSize);
		//currentChart = new SaxChart(-20.6f, 48.9f);
		
		//exporter.SaveChartsToFile(currentChart, chartsExportFolder);
		
		String p = "data/GlaucomaEamonn";
		//exporter.ConstructArff(arffExportPath);
		//exporter.exportCsvToEamonnFormat(p);
		
		DataImporterEamonn eamonnImporter = new DataImporterEamonn("C:/Users/Wojciech/Documents/studia/mgr/praca mgr/stationary contrib/dataset/Lighting2/Lighting2_TRAIN");
		List<UnifiedRecordType> data = eamonnImporter.ImportEamonnData();
		DataExporterEamonn eamonnExporter = new DataExporterEamonn(data);
		//eamonnExporter.ConstructArff("data/lighting2 train.arff");
		
		runExperiment();

		System.out.println("Koniec");
	}
	
	private static void runExperiment(){
		try {
//			Classifier classifier = new NgramClassifier(ngramSize);
			Classifier classifier = new J48();
			Experiment.runExperiment(classifier, trainSetPath, testSetPath);
		} catch (Exception e) {
			// TODO Auto-generangramsInClasses.get(classIndex)ed catch block
			e.printStackTrace();
		}
	}

	private void displayWindowChart(JFreeChart jchart) {
		ChartWindow window = new ChartWindow("Time Series", new Dimension(1600,
				1000), jchart);
		window.pack();
		RefineryUtilities.centerFrameOnScreen(window);
		window.setVisible(true);
	}

}
