package pl.poznan.put.Deprecated;

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

public class DataExporterCsv {

	private List<Patient> patients;

	public DataExporterCsv(List<Patient> patients) {
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
				System.out.println("Saving charts to file failed.");
				e.printStackTrace();
			}
		}
	}

	public void ConstructArff(String destinationPath) {
		PatientToArffTranslator arffTranslator = new PatientToArffTranslator();
		try {
			arffTranslator.savePatientsToArffData(patients, destinationPath);
		} catch (FileNotFoundException e) {
			System.out.println("Error during arff construction.");
			e.printStackTrace();
		}
	}

	public void exportCsvToEamonnFormat(String destPath) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
		for (Patient patient : patients) {
			float patClass = patient.isSick() ? 1 : 0;
			writer.write(String.format("%15.7e", patClass));

			for (int i = 0; i < patient.getCharacteristics().size(); i++) {
				writer.write(String.format(" %15.7e", patient
						.getCharacteristics().get(i).getTfadj()));
			}

			writer.write(System.getProperty("line.separator"));
		}

		writer.flush();
		writer.close();
	}

}
