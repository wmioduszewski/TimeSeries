package pl.poznan.put.TimeSeries.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;

public class PatientToArffTranslator {

	private String relationTitle = "Glaucoma";
	private StringBuilder arffFileContent;
	private Map<String, String> attributes;
	private List<Patient> patients;

	public PatientToArffTranslator() {
		arffFileContent = new StringBuilder();
		attributes = new HashMap<String, String>();
	}

	public void savePatientsToArffData(List<Patient> patients,
			String destinationPath) throws FileNotFoundException {
		this.patients = patients;
		buildFileContent();
		PrintWriter writer = new PrintWriter(new File(destinationPath));
		writer.write(arffFileContent.toString());
		writer.flush();
		writer.close();
	}

	private void buildFileContent() {
		assignAttributes();
		insertRelationName();
		insertAttributesNames();
		insertData();
	}

	private void insertData() {
		arffFileContent.append("@DATA\n");

		for (Patient patient : patients) {
			String diagnosis = patient.isSick() ? PatientStates.Sick
					: PatientStates.Health;
			arffFileContent.append(patient.getSaxString().getContent() + ","
					+ diagnosis + "\n");
		}
	}

	private void assignAttributes() {
		attributes.put("saxString", "string");
		attributes.put("class", "{" + PatientStates.Health + ","
				+ PatientStates.Sick + "}");
	}

	private void insertRelationName() {
		arffFileContent.append("@RELATION " + relationTitle + "\n");
	}

	private void insertAttributesNames() {
		for (Entry<String, String> attr : attributes.entrySet()) {
			arffFileContent.append("@ATTRIBUTE " + attr.getKey() + " "
					+ attr.getValue() + "\n");
		}
	}

	private class PatientStates {
		public static final String Health = "health";
		public static final String Sick = "sick";
	}

}
