package pl.poznan.put.TimeSeries.DataImporters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class PatientDataImporterCsv extends PatientDataImporterBase {

	public PatientDataImporterCsv(String inputFilePath) {
		super(inputFilePath);
	}

	// ID,D,TIME,TIMEFF,INTERVAL,TIMEFBI,SAP,DAP,HRBP,MAP,TF,TFADJ,TFGAT,DPP,BODYPOSITION,ACOMMODATION
	protected void readData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(_filepath));

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

			int diagnosis = Integer.parseInt(fields[1].trim());
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
			Characteristic c = new Characteristic(dt);

			try {
				tfadj = Float.parseFloat(fields[11]);
				if (lastPatient.getAsleep() == null) {
					int interval = Integer.parseInt(fields[4]);
					if (interval == 2) {
						lastPatient.setAsleep(dt);
					}
				}
				if (lastPatient.getAwake() == null) {
					int interval = Integer.parseInt(fields[4]);
					if (interval == 4) {
						lastPatient.setAwake(dt);
					}
				}
				c.setTfadj(tfadj);
				lastPatient.addCharacteristic(c);
			} catch (NumberFormatException e) {
				System.out.println("Missing tfadj value for patient ID "
						+ lastPatient.getId() + " at " + dt);
			}
			lastId = currentId;
			currLine = br.readLine();
		}
		br.close();

		patients = patientList;
	}

	public List<Patient> importData() throws IOException {
		readData();
		computeSaxForPatients();
		return patients;
	}

}
