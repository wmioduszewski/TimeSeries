package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.ArrayList;
import java.util.Collection;
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
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;

public class DataDivider {

	public static List<List<Characteristic>> dividePatientDataPeriodically(Patient patient)
			throws Exception {
		List<List<Characteristic>> listlist = new ArrayList<List<Characteristic>>();
		
		for (TimeLimit timeLimit : Limits.TimeLimits) {
			
			List<Characteristic> currList = new ArrayList<Characteristic>(); 

			DateTime lowerBound = ComputeTimeBound(timeLimit, patient, true);
			DateTime upperBound = ComputeTimeBound(timeLimit, patient, false);
			//List<Double> valList = new ArrayList<Double>();
			for (Characteristic characteristic : patient.getCharacteristics()) {
				DateTime time = characteristic.getExaminationTime();
				if (time.isAfter(lowerBound) && time.isBefore(upperBound)) {
					//valList.add((double) characteristic.getTfadj());
					currList.add(characteristic);
				}
			}
			listlist.add(currList);
			//listlist.add(CastDoubleListToArray(valList));
		}
		return listlist;
	}
	
	public static List<List<Characteristic>> dividePatientPeriodicallyThenRegularly(Patient patient, Integer parts) throws Exception{
		List<List<Characteristic>> listlist = dividePatientDataPeriodically(patient);
		List<List<Characteristic>> newlistlist = new ArrayList<List<Characteristic>>(); 
		for (List<Characteristic> list : listlist) {
			newlistlist.addAll(DivideCollectionRegularly(list, parts));			
		}
		return newlistlist;
	}
	
	private static Double[] CastDoubleListToArray(List<Double> input) {
		Double[] output = new Double[input.size()];
		for (int i = 0; i < input.size(); i++) {
			output[i] = input.get(i);
		}
		return output;
	}

	public static <T> List<List<T>> DivideCollectionRegularly(List<T> list,
			Integer parts) {

		int partLen = list.size() / parts;
		List<List<T>> listlist = new ArrayList<List<T>>();

		for (int i = 0; i < parts; i++) {
			List<T> currList = new ArrayList<T>();
			for (int j = 0; j < partLen; j++) {
				currList.add(list.get(i * partLen + j));
			}
			listlist.add(currList);
		}
		if(parts*partLen<list.size())
		{
			List<T> currList = listlist.get(listlist.size()-1);
			for(int i=parts*partLen;i<list.size();i++){
				currList.add(list.get(i));
			}
		}
		return listlist;
	}

//	public static List<Double[]> divideEamonnRecord(UnifiedRecord record) {
//		int parts = 10;
//		double[] vals = record.getValues();
//		int partSize = vals.length / parts;
//		List<Double[]> res = new ArrayList<Double[]>();
//
//		for (int i = 0; i < parts; i++) {
//			List<Double> valList = new ArrayList<Double>();
//			if (i == parts - 1) {
//				for (int j = i * partSize; j < vals.length; j++) {
//					valList.add(vals[j]);
//				}
//			} else {
//				for (int j = 0; j < partSize; j++) {
//					valList.add(vals[i * partSize + j]);
//				}
//			}
//			res.add((Double[]) valList.toArray());
//		}
//		return res;
//	}

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
					DateTime lowerBound = ComputeTimeBound(timeLimit, patient,
							true);
					DateTime upperBound = ComputeTimeBound(timeLimit, patient,
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

	private static DateTime ComputeTimeBound(TimeLimit timeLimit,
			Patient patient, Boolean isLower) throws Exception {
		DateTime bound = null;

		char option = isLower ? timeLimit.getLowerOption() : timeLimit
				.getUpperOption();
		int mod = isLower ? timeLimit.getLowerMod() : timeLimit.getUpperMod();

		if (option == TimeLimit.Asleep)
			bound = patient.getAsleep();
		else if (option == TimeLimit.Awake)
			bound = patient.getAwake();

		if (bound == null) {
			throw new Exception("bound is null");
		}
		bound = bound.plusHours(mod);

		return bound;
	}
}
