package pl.poznan.put.Deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Constants.TimeLimit;
import pl.poznan.put.TimeSeries.DataImporters.PatientDataImporterCsv;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Configuration;

public class Misc {
	
	@SuppressWarnings("unused")
	private static void runOldFlow() throws Exception {
		FlowHandler flow = new FlowHandler();
		flow.startProcessing();
	}

	@SuppressWarnings("unused")
	private static void CsvToEamonn() throws IOException {
		String path = Configuration.getProperty("csvDataSet");

		PatientDataImporterCsv csvImport = new PatientDataImporterCsv(path);

		List<Patient> patients = csvImport.ImportData();

		DataExporterCsv exp = new DataExporterCsv(patients);
		exp.exportCsvToEamonnFormat(path);
	}
	
	public static List<PatientGroup> divideData(List<Patient> patients,
			boolean isSick) throws Exception {
		List<PatientGroup> patientGroups = new ArrayList<PatientGroup>();
		for (AgeLimit ageLimit : Limits.AgeLimits) {
			Stream<Patient> agedPatientsStream = patients
					.stream()
					.filter(x -> x.isSick() == isSick)
					.filter(x -> x.getAge() >= ageLimit.getLowerBound()
							&& x.getAge() <= ageLimit.getUpperBound());

			List<Patient> agedPatients = agedPatientsStream.collect(Collectors
					.toList());
			for (TimeLimit timeLimit : Limits.TimeLimits) {

				PatientGroup patientGroup = new PatientGroup(timeLimit,
						ageLimit);

				for (Patient patient : agedPatients) {
					DateTime lowerBound = DataDivider.ComputeTimeBound(timeLimit, patient,
							true);
					DateTime upperBound = DataDivider.ComputeTimeBound(timeLimit, patient,
							false);

					Patient newPatient = (Patient) patient.clone();
					newPatient
							.setCharacteristics(new ArrayList<Characteristic>());

					for (Characteristic characteristic : patient
							.getCharacteristics()) {
						DateTime time = characteristic.getExaminationTime();
						if (time.isAfter(lowerBound)
								&& time.isBefore(upperBound)) {
							newPatient.addCharacteristic(characteristic);
						}
					}
					patientGroup.addPatient(newPatient);
				}
				patientGroups.add(patientGroup);
			}
		}
		return patientGroups;
	}
	
	public static List<EamonnRecord> castPatients(List<Patient> patients) {
	List<EamonnRecord> res = new ArrayList<EamonnRecord>();
	for (Patient patient : patients) {
		EamonnRecord obj = new EamonnRecord(
				patient.getDestinationClass(), patient.getValues());
		obj.setSaxString(patient.getSaxString());
		res.add(obj);
	}
	return res;
}

}
