package pl.poznan.put.TimeSeries.DataOperators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.joda.time.DateTime;

import edu.hawaii.jmotif.lib.ts.TSException;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.FileLister;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public class PureDataImporter {

	private String folderPath;
	private float firstBurstMedian;
	private float adjMedian;
	private final int minutePeriod = 5;

	public PureDataImporter(String inputFilePath) {
		this.folderPath = inputFilePath;
	}

	private Patient readCsvData(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(folderPath
				+ filePath));
		boolean diagnosis = false;
		// skip first line
		String currLine = br.readLine();
		currLine = br.readLine();

		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		int patientId = Integer.parseInt(fileName.substring(0,
				fileName.lastIndexOf(".")));
		Patient currentPatient = new Patient(patientId);

		boolean isFirstLine = true;
		DateTime startExaminationTime = null;
		DateTime asleepTime;
		DateTime awakeTime;

		while (currLine != null) {
			String[] fields = currLine.split(";");

			if (isFirstLine) {
				startExaminationTime = getDateTimeByStringClock(fields[0]);
				firstBurstMedian = Float.parseFloat(fields[1]);
				asleepTime = getDateTimeByStringClock(fields[5]);
				awakeTime = getDateTimeByStringClock(fields[6]);
				adjMedian = fields[7].isEmpty() ? 0 : Float.parseFloat(fields[7]);
				int age = Integer.parseInt(fields[14]);
				isFirstLine = false;

				currentPatient.setSick(diagnosis);
				currentPatient.setAge(age);
				currentPatient.setAsleep(asleepTime);
				currentPatient.setAwake(awakeTime);
			}

			else {
				int burstId = Integer.parseInt(fields[2]);
				DateTime currCharacteristicTime = startExaminationTime
						.plusMinutes(minutePeriod * (burstId - 1));
				Characteristic c = new Characteristic(currCharacteristicTime);
				float tf = Float.parseFloat(fields[1]) - firstBurstMedian;
				float tfadj = tf + adjMedian;
				c.setTf(tf);
				c.setTfadj(tfadj);
				currentPatient.addCharacteristic(c);
			}
			currLine = br.readLine();
		}
		br.close();

		return currentPatient;
	}

	private DateTime getDateTimeByStringClock(String clock) {

		int minutes = 0;
		int hours = 0;
		if (clock.contains(":")) {
			String[] minuteElems = clock.trim().split(":");

			minutes = Integer.parseInt(minuteElems[1]);
			hours = Integer.parseInt(minuteElems[0]);
		}
		int days = 2;
		if (hours >= 12)
			days = 1;
		hours = hours <24 ? hours : 0;
		DateTime dt = new DateTime(2014, 4, days, hours, minutes, 0);
		return dt;
	}

	public List<Patient> ImportData() throws IOException {
		String[] patientFiles = FileLister.getFolderFiles(folderPath);
		List<Patient> patients = new ArrayList<Patient>();
		for (String file : patientFiles) {
			Patient currPatient = readCsvData(file);
			patients.add(currPatient);
		}

		// computeSaxForPatients();

		return patients;
	}

	// public List<Patient> ImportData(int fromIndex, int toIndex)
	// throws IOException {
	// return ImportData().subList(fromIndex, toIndex);
	// }

}
