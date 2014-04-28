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

import pl.poznan.put.TimeSeries.DataReaders.CsvReader;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Renderers.ChartBase;
import pl.poznan.put.TimeSeries.Renderers.ChartWindow;
import pl.poznan.put.TimeSeries.Renderers.SmoothChart;
import pl.poznan.put.TimeSeries.Sax.SaxPerformer;
import pl.poznan.put.TimeSeries.Util.PatientsToArffTranslator;
import edu.hawaii.jmotif.lib.ts.TSException;

public class MainForm {

	private static List<Patient> ReadData(String path) {
		List<Patient> patients = null;
		try {
			List<Patient> readData = CsvReader.ReadData(path);
			patients = readData;//.subList(0, 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return patients;
	}

	static int smoothingSize = 5;
	static float omega = 0.1f;

	public static void main(String[] args) {

		List<Patient> patients = ReadData("doc/dane/gTimeData.7.5.20130123a_sub.csv");
		ComputeSaxForPatients(patients);

		ChartBase currentChart;
		// currentChart = new CleanChart();

		// available values for TFADJ are from -20,6 to 48,9.
		// currentChart = new SaxChart(-20.6f, 48.9f);

		// currentChart = new SmoothChart(omega);

		currentChart = new SmoothChart(smoothingSize);
		
		ConsructArff(patients);

		//String folder = "C:/Users/Wojciech/Documents/studia/mgr/praca mgr/TimeSeries/output/charts/smooth/";
		//SaveChartsToFile(currentChart, folder, patients);

		// String path = "SaxStrings.txt";
		// try {
		// SaveSaxStringsToFile(path, patients);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		System.out.println("Koniec");
	}
	
	private static void ConsructArff(List<Patient> patients){
		String destPath = "C:/Users/Wojciech/Documents/studia/mgr/praca mgr/TimeSeries/output/patients.arff";
		PatientsToArffTranslator arffTranslator = new PatientsToArffTranslator();
		try {
			arffTranslator.savePatientsToArffData(patients, destPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void DisplayWindowChart(JFreeChart jchart) {
		ChartWindow window = new ChartWindow("Time Series", new Dimension(1600,
				1000), jchart);
		window.pack();
		RefineryUtilities.centerFrameOnScreen(window);
		window.setVisible(true);
	}

	private static void ComputeSaxForPatients(List<Patient> patients) {
		int alphabeatSize = 20;
		int outputLength = 48;
		for (Patient patient : patients) {
			String sax = null;
			try {
				sax = SaxPerformer.TranslateTimeSeriesToString(patient,
						outputLength, alphabeatSize);
			} catch (CloneNotSupportedException | TSException e) {
				e.printStackTrace();
			}
			patient.setSaxString(new SaxString(sax, outputLength, alphabeatSize));
		}
	}

	private static void SaveSaxStringsToFile(String path, List<Patient> patients)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		for (Patient patient : patients) {
			String d = patient.isSick() ? "D1" : "D2";
			writer.write(patient.getId() + " " + d + ":\t"
					+ patient.getSaxString().getContent()
					+ System.getProperty("line.separator"));
		}

		writer.flush();
		writer.close();
	}

	private static void SaveChartsToFile(ChartBase chart, String folder,
			List<Patient> patients) {
		for (Patient patient : patients) {
			String d = patient.isSick() ? "D1" : "D0";
			String name = chart.getChartPrefix() + " " + patient.getId() + " "
					+ d;
			patient.setChartCaption(name);
			JFreeChart jchart = chart.getPatientChart(Arrays.asList(patient));
			try {
				ChartUtilities.saveChartAsJPEG(
						new File(folder + name + ".jpg"), jchart, 1600, 1000);
				System.out.println("Narysowałem " + name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
