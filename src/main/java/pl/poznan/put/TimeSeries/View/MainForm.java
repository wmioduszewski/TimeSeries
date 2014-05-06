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
import pl.poznan.put.TimeSeries.DataOperators.DataExporter;
import pl.poznan.put.TimeSeries.DataOperators.DataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Renderers.ChartBase;
import pl.poznan.put.TimeSeries.Renderers.ChartWindow;
import pl.poznan.put.TimeSeries.Renderers.CleanChart;
import pl.poznan.put.TimeSeries.Renderers.SaxChart;
import pl.poznan.put.TimeSeries.Renderers.SmoothChart;
import pl.poznan.put.TimeSeries.Sax.SaxPerformer;
import pl.poznan.put.TimeSeries.Util.PatientsToArffTranslator;
import edu.hawaii.jmotif.lib.ts.TSException;

public class MainForm {

	static int smoothingSize = 5;
	static float omega = 0.1f;
	static String inputFilePath = "data/gTimeData.7.5.20130123a_sub.csv";
	static String chartsExportFolder = "output/charts/test/";
	static String arffExportPath = "output/patients alph10.arff";
	static String saxStringsExportpath = "output/SaxStrings alph 10.txt";
	static String trainSetPath ="data/patients train.arff";
	static String testSetPath = "data/patients test.arff";
	static int ngramSize = 5;

	public static void main(String[] args) {

		DataImporter importer = new DataImporter(inputFilePath);
		List<Patient> patients = null;
		try {
			patients = importer.ImportData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DataExporter exporter = new DataExporter(patients);
		ChartBase currentChart;
		// currentChart = new CleanChart();
		// currentChart = new SmoothChart(omega);
		// currentChart = new SmoothChart(smoothingSize);
		currentChart = new SaxChart(-20.6f, 48.9f);
		
		//exporter.SaveChartsToFile(currentChart, chartsExportFolder);
		
		
			exporter.ConstructArff(arffExportPath);
		
		//runExperiment();

		System.out.println("Koniec");
	}
	
	private static void runExperiment(){
		try {
			Experiment.runExperiment(new NgramClassifier(ngramSize), trainSetPath, testSetPath);
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
