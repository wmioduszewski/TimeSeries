package pl.poznan.put.TimeSeries.DataOperators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Renderers.ChartBase;
import pl.poznan.put.TimeSeries.Util.PatientsToArffTranslator;

public class DataExporter {

	private List<Patient> patients;

	public DataExporter(List<Patient> patients) {
		this.patients = patients;
	}

	public void SaveSaxStringsToFile(String path) throws IOException {
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

	public void SaveChartsToFile(ChartBase chart, String folder) {
		for (Patient patient : patients) {
			String d = patient.isSick() ? "D1" : "D0";
			String name = chart.getChartPrefix() + " " + patient.getId() + " "
					+ d;
			patient.setChartCaption(name);
			JFreeChart jchart = chart.getPatientChart(Arrays.asList(patient));
			try {
				ChartUtilities.saveChartAsJPEG(
						new File(folder + name + ".jpg"), jchart, 1600, 1000);
				System.out.println(name + " successfully painted.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void ConstructArff(String destinationPath) {
		PatientsToArffTranslator arffTranslator = new PatientsToArffTranslator();
		try {
			arffTranslator.savePatientsToArffData(patients, destinationPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
