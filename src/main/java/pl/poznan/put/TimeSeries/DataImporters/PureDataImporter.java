package pl.poznan.put.TimeSeries.DataImporters;

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

public class PureDataImporter extends DataImporterBase {

	private String folderPath;
	private float firstBurstMedian;
	private float adjMedian;
	private final int minutePeriod = 5;

	public PureDataImporter(String inputFilePath) {
		super(inputFilePath);
		this.folderPath = inputFilePath;
	}

	private Patient readData(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(folderPath
				+ filePath));
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
		int patientId = Integer.parseInt(fileName.substring(0,
				fileName.lastIndexOf(".")));
		Patient currentPatient = new Patient(patientId);

		boolean isFirstLine = true;
		DateTime startExaminationTime = null;

		while (currLine != null) {
			String[] fields = currLine.split(";");

			if (isFirstLine) {
				// if(Integer.parseInt(fields[14])==24){
				// Integer.parseInt(fields[14]);
				// }
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
					adjMedian = fields[7].isEmpty() ? 0 : Float
							.parseFloat(fields[7]);
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

	private DateTime getDateTimeByStringClock(String clock) throws Exception {

		int minutes = 0;
		int hours = 0;
		if (clock.contains(":")) {
			String[] minuteElems = clock.trim().split(":");

			minutes = Integer.parseInt(minuteElems[1]);
			hours = Integer.parseInt(minuteElems[0]);
		} else {
			throw new Exception("Data is in wrong format");
		}
		int days = 2;
		hours = hours < 24 ? hours : 0;
		if (hours >= 12)
			days = 1;
		DateTime dt = new DateTime(2014, 4, days, hours, minutes, 0);
		return dt;
	}

	public List<Patient> ImportData() throws IOException {
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
		// computeSaxForPatients();

		System.out.println();

		long healtCount = patients.stream()
				.filter(x -> x.getDestinationClass() == 0).count();
		long sickCount = patients.stream()
				.filter(x -> x.getDestinationClass() == 1).count();

		System.out.println(String.format(
				"Read %d patients.\n%d health and %d sick", patients.size(),
				healtCount, sickCount));

		return patients;
	}
}
