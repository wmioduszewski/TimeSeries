package pl.poznan.put.Deprecated;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import pl.poznan.put.TimeSeries.DataExporters.ArffExporterBase;
import pl.poznan.put.TimeSeries.Model.Patient;

public class PatientToArffTranslator extends ArffExporterBase {

	private Map<String, String> attributes;
	private List<Patient> patients;

	public PatientToArffTranslator() {
		super("Glaucoma");
	}

	public void savePatientsToArffData(List<Patient> patients,
			String destinationPath) throws FileNotFoundException {
		this.patients = patients;
		performExport(destinationPath);
	}

	@Override
	protected void insertData() {
		arffFileContent.append("@DATA\n");

		for (Patient patient : patients) {
			String diagnosis = patient.isSick() ? PatientStates.Sick
					: PatientStates.Health;
			arffFileContent.append(patient.getSaxString().getContent() + ","
					+ diagnosis + "\n");
		}
	}

	@Override
	protected void assignAttributes() {
		attributes.put("saxString", "string");
		attributes.put("class", "{" + PatientStates.Health + ","
				+ PatientStates.Sick + "}");
	}

	private class PatientStates {
		public static final String Health = "health";
		public static final String Sick = "sick";
	}
}
