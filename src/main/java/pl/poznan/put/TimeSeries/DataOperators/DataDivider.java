package pl.poznan.put.TimeSeries.DataOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Constants.TimeLimit;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.PatientGroup;

public class DataDivider {

	public static List<PatientGroup> divideData(List<Patient> patients,
			boolean isSick) {
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
					DateTime lowerBound = ComputeTimeBound(ageLimit, timeLimit,
							patient, true);
					DateTime upperBound = ComputeTimeBound(ageLimit, timeLimit,
							patient, false);

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

	private static DateTime ComputeTimeBound(AgeLimit ageLimit,
			TimeLimit timeLimit, Patient patient, Boolean isLower) {
		DateTime bound = null;

		char option = isLower ? timeLimit.getLowerOption() : timeLimit
				.getUpperOption();
		int mod = isLower ? timeLimit.getLowerMod() : timeLimit.getUpperMod();

		if (option == TimeLimit.Asleep)
			bound = patient.getAsleep();
		else if (option == TimeLimit.Awake)
			bound = patient.getAwake();

		bound = bound.plusHours(mod);
		return bound;
	}
}
