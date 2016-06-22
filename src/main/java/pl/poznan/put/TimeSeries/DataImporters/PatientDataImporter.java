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

public class PatientDataImporter extends PatientDataImporterBase {

	private String folderPath;
	private static final String filePrefix = "r_";
	private static final String fileExtension = "csv";

	public PatientDataImporter(String filePath) {
		super(filePath);
		folderPath = filePath;
	}

	@Override
	public List<Patient> importData() throws IOException {
		patients = new ArrayList<Patient>();

		String[] files = FileLister.getDirectoryFiles(folderPath, filePrefix, fileExtension);
		for (String file : files) {
			importSinglePatient(file);
		}
		setDiagnosis("diagnosis_id.csv");
		computeSaxForPatients();
		return patients;
	}
	
	private void importSinglePatient(String file){
		try {
			Patient currPatient;
			currPatient = readData(file);
			patients.add(currPatient);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Missed file: " + file);
		}
	}

	private Patient readData(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(folderPath + filePath));
		// skip first line
		String currLine = br.readLine();

		currLine = br.readLine();
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		String patientId = fileName.substring(0, fileName.lastIndexOf("."));
		Patient currentPatient = new Patient(patientId);

		while (currLine != null) {
			String[] fields = currLine.split(",");
			DateTime currCharacteristicTime = getDateTimeByStringClock(fields[3]);
			Characteristic c = new Characteristic(currCharacteristicTime);
			float tf = Float.parseFloat(fields[8]);
			float tfadj = Float.parseFloat(fields[10]);
			c.setTf(tf);
			c.setTfadj(tfadj);
			currentPatient.addCharacteristic(c);
			currLine = br.readLine();
		}
		br.close();
		return currentPatient;
	}

	private void setDiagnosis(String diagnosisFilePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(folderPath + diagnosisFilePath));
		// skip first line
		String currLine = br.readLine();

		currLine = br.readLine();
		while (currLine != null) {
			String fields[] = currLine.split(",");
			String currId = fields[0];
			boolean isSick = Integer.parseInt(fields[1]) == 1;
			patients.stream().filter(x -> x.getId().equals(currId)).findFirst().get().setSick(isSick);
			currLine = br.readLine();
		}
		br.close();
	}

}
