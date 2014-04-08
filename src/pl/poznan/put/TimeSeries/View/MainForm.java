package pl.poznan.put.TimeSeries.View;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfree.ui.*;

import pl.poznan.put.TimeSeries.DataReaders.CsvReader;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Renderers.LineChart;
import pl.poznan.put.TimeSeries.Util.Smoother;

public class MainForm {

	private static List<Patient> ReadData(String path) {
		List<Patient> patients = null;
		try {
			List<Patient> readData = CsvReader.ReadData(path);
			// od indeksu = 10 => pacjenci chorzy

			patients = new ArrayList<Patient>(Arrays.asList(readData.get(0),
					readData.get(1), readData.get(2),
					// readData.get(3),
					// readData.get(4),

					readData.get(12), readData.get(13)));
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
		List<Patient> patientsToChart = new ArrayList<Patient>();

//		if (smoothingSize > 1) {
//			for (Patient patient : patients) {
//				Patient p = (Patient)patient.clone();
//				p.setCharacteristics(Smoother.SmoothCharacteristics(
//						p.getCharacteristics(), smoothingSize));
//				p.setChartCaption("Smooth " + p.getId());
//				patientsToChart.add(p);
//			}
//		}

		if (omega > 0.0f) {
			for (Patient patient : patients) {
				Patient p = (Patient)patient.clone();
				p.setCharacteristics(Smoother.SmoothEWMACharacteristics(
						p.getCharacteristics(), omega));
				p.setChartCaption("EWMA " + p.getId());
				patientsToChart.add(p);
			}
		}

		final LineChart lineChart = new LineChart("Time Series",
				patientsToChart, prefferedSize);
		lineChart.pack();
		RefineryUtilities.centerFrameOnScreen(lineChart);
		lineChart.setVisible(true);
	}
}
