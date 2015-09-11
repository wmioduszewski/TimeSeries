package pl.poznan.put.TimeSeries.DataImporters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.FileLister;

public class PatientDataImporterPure extends PatientDataImporterBase {

	private String folderPath;
	private float firstBurstMedian;
	private float adjMedian;
	private final int minutePeriod = 5;

	public PatientDataImporterPure(String inputFilePath) {
		super(inputFilePath);
		this.folderPath = inputFilePath;
	}

	private Patient readData(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(folderPath + filePath));
		boolean isSick = false;
		// skip first line
		String currLine = br.readLine();

		DateTime asleepTime = null;
		DateTime awakeTime = null;

		if (!currLine.contains("SLEEP")) {
			String[] fields = currLine.split(";");
			asleepTime = getDateTimeByStringClock(fields[5]);
			awakeTime = getDateTimeByStringClock(fields[6]);
		}

		currLine = br.readLine();

		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		String patientId = fileName.substring(0, fileName.lastIndexOf("."));
		Patient currentPatient = new Patient(patientId);

		boolean isFirstLine = true;
		DateTime startExaminationTime = null;

		while (currLine != null) {
			String[] fields = currLine.split(";");

			if (isFirstLine) {
				startExaminationTime = getDateTimeByStringClock(fields[0]);
				if (startExaminationTime.getDayOfMonth() > 1)
					startExaminationTime = startExaminationTime.minusDays(1);
				firstBurstMedian = Float.parseFloat(fields[1]);
				if (asleepTime == null) {
					asleepTime = getDateTimeByStringClock(fields[5]);
				}
				if (awakeTime == null) {
					awakeTime = getDateTimeByStringClock(fields[6]);
				}
				try {
					adjMedian = fields[7].isEmpty() ? 0 : Float.parseFloat(fields[7]);
				} catch (Exception e1) {
					adjMedian = 0;
				}
				int age;
				try {
					age = Integer.parseInt(fields[14]);
				} catch (Exception e) {
					age = -1;
				}
				isFirstLine = false;

				if (folderPath.contains("jaskra"))
					isSick = true;

				currentPatient.setSick(isSick);
				currentPatient.setAge(age);
				currentPatient.setAsleep(asleepTime);
				currentPatient.setAwake(awakeTime);
			}

			else {
				int burstId = Integer.parseInt(fields[2]);
				DateTime currCharacteristicTime = startExaminationTime.plusMinutes(minutePeriod * (burstId - 1));

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

	public List<Patient> importData() throws IOException {
		patients = new ArrayList<Patient>();
		String[] folders = FileLister.getDirectories(folderPath);
		for (String folder : folders) {
			folderPath = folder + '\\';
			String[] patientFiles = FileLister.getDirectoryFiles(folder);
			for (String file : patientFiles) {
				Patient currPatient;
				try {
					currPatient = readData(file);
					patients.add(currPatient);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Missed file: " + file);
				}
			}
		}
		System.out.println();
		computeSaxForPatients();
		return patients;
	}
}
