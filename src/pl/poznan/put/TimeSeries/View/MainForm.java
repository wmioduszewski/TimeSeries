package pl.poznan.put.TimeSeries.View;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.*;

import edu.hawaii.jmotif.lib.ts.TSException;
import pl.poznan.put.TimeSeries.DataReaders.CsvReader;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Renderers.ChartWindow;
import pl.poznan.put.TimeSeries.Renderers.LineChart;
import pl.poznan.put.TimeSeries.Renderers.SaxChart;
import pl.poznan.put.TimeSeries.Sax.SaxPerformer;
import pl.poznan.put.TimeSeries.Util.Smoother;

public class MainForm {

	private static List<Patient> ReadData(String path) {
		List<Patient> patients = null;
		try {
			List<Patient> readData = CsvReader.ReadData(path);
			// od indeksu = 10 => pacjenci chorzy
			
			return readData;
			
//			patients = new ArrayList<Patient>(Arrays.asList(readData.get(13)
//			// readData.get(1), readData.get(2),
//			// readData.get(3),
//			// readData.get(4),
//
//					// readData.get(12), readData.get(13)
//					));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return patients;
	}

	static int smoothingSize = 5;
	static float omega = 0.1f;

	public static void main(String[] args) {
		Dimension prefferedSize = new Dimension(1800, 1100);
		List<Patient> patients = ReadData("doc/dane/gTimeData.7.5.20130123a_sub.csv");
		// List<Patient> patientsToChart = new ArrayList<Patient>();

		// if (smoothingSize > 1) {
		// for (Patient patient : patients) {
		// Patient p = (Patient)patient.clone();
		// p.setCharacteristics(Smoother.SmoothCharacteristics(
		// p.getCharacteristics(), smoothingSize));
		// p.setChartCaption("Smooth " + p.getId());
		// patientsToChart.add(p);
		// }
		// }

		// available values for TFADJ are from -20,6 to 48,9.
		// Ideally I would have about one symbol for single unit number.
		// JMotif gives me opportunity only to set 20 symbols

		int alphabeatSize = 20;
		// one symbol for each half hour
		int outputLength = 48;

		for (Patient patient : patients) {
			String sax = null;
			try {
				sax = SaxPerformer.TranslateTimeSeriesToString(patient,
						outputLength, alphabeatSize);
			} catch (CloneNotSupportedException | TSException e) {
				e.printStackTrace();
			}
			patient.AddSaxString(new SaxString(sax, outputLength, alphabeatSize));
		}

		// if (omega > 0.0f) {
		// for (Patient patient : patients) {
		// Patient p = (Patient) patient.clone();
		// //p.setCharacteristics(Smoother.SmoothEWMACharacteristics(
		// // p.getCharacteristics(), omega));
		// p.setChartCaption("Sax " + p.getId());
		// patientsToChart.add(p);
		// }
		// }

		String path = "C:/Users/Wojciech/Documents/studia/mgr/praca mgr/TimeSeries/output/charts/clean/";

		for (Patient patient : patients) {
			String d = patient.isSick() ? "D1" : "D0";
			String name = "Clean " + patient.getId() +" " + d;
			patient.setChartCaption(name);
			//patient.setCharacteristics(Smoother.SmoothEWMACharacteristics(patient.getCharacteristics(), 0.3f));
			JFreeChart jchart = LineChart.getPatientChart(Arrays.asList(patient));
			try {
				ChartUtilities.saveChartAsJPEG(new File(path + name +".jpg"),
						jchart, 1600, 1000);
				System.out.println("Narysowałem "+ name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Koniec");
		// ChartWindow window = new ChartWindow("Time Series", new
		// Dimension(1600,1000), jchart);
		// window.pack();
		// RefineryUtilities.centerFrameOnScreen(window);
		// window.setVisible(true);
	}
}
