package pl.poznan.put.TimeSeries.DataOperators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import edu.hawaii.jmotif.lib.ts.TSException;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public class DataImporterCsv {

	List<Patient> patients;
	private String path;

	public DataImporterCsv(String inputFilePath) {
		this.path = inputFilePath;
	}

	// ID,D,TIME,TIMEFF,INTERVAL,TIMEFBI,SAP,DAP,HRBP,MAP,TF,TFADJ,TFGAT,DPP,BODYPOSITION,ACOMMODATION
	private void readCsvData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));

		List<Patient> patientList = new ArrayList<Patient>();

		// skip first line
		String currLine = br.readLine();
		currLine = br.readLine();

		int lastId = -1;// Integer.parseInt(currLine.split(",")[0]);
		Patient lastPatient = null;
		boolean isFirstLine = true;

		while (currLine != null) {
			String[] fields = currLine.split(",");
			int currentId = Integer.parseInt(fields[0]);

			if (currentId != lastId && !isFirstLine) {
				patientList.add(lastPatient);
				lastPatient = new Patient(currentId);
			}

			if (isFirstLine) {
				lastPatient = new Patient(currentId);
				isFirstLine = false;
			}

			int diagnosis = Integer.parseInt(fields[1]);
			lastPatient.setSick(diagnosis > 0);
			String[] minuteElems = fields[2].trim().split(":");

			int minutes = Integer.parseInt(minuteElems[1]);
			int hours = Integer.parseInt(minuteElems[0]);
			int days = 2;

			if (hours >= 12)
				days = 1;

			// Minute exTime = new Minute(minutes, hours,days,1,1900);
			DateTime dt = new DateTime(2014, 4, days, hours, minutes, 0);
			float tfadj;
			Characteristic c = new Characteristic(dt, 0);
			
			try {
				tfadj = Float.parseFloat(fields[11]);
				c.setTfadj(tfadj);

			} catch (NumberFormatException e) {
				System.out.println("Missing value for patient ID "
						+ lastPatient.getId() + " at " + dt);
			}

			lastPatient.AddCharacteristic(c);

			lastId = currentId;
			currLine = br.readLine();
		}
		br.close();

		patients = patientList;
	}

	private void computeSaxForPatients() {
		int alphabeatSize = Integer.parseInt(Configuration
				.getProperty("saxAlphabeatSize"));;
		int outputLength = Integer.parseInt(Configuration
				.getProperty("saxOutputLength"));;
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

	public List<Patient> ImportData() throws IOException {
		readCsvData();
		computeSaxForPatients();

		return patients;
	}

	public List<Patient> ImportData(int fromIndex, int toIndex)
			throws IOException {
		return ImportData().subList(fromIndex, toIndex);
	}
}
