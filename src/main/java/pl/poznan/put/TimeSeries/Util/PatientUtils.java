package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;

public class PatientUtils {

	private static float trainTestRatio = Float.parseFloat(Configuration
			.getProperty("trainToTestRatio"));

	public static List<UnifiedRecord> castPatients(List<Patient> patients) {
		List<UnifiedRecord> res = new ArrayList<UnifiedRecord>();
		for (Patient patient : patients) {
			UnifiedRecord obj = new UnifiedRecord(
					patient.getDestinationClass(), patient.getValues());
			obj.setSaxString(patient.getSaxString().getContent());
			res.add(obj);
		}
		return res;
	}

	public static Pair<List<UnifiedArffRow>, List<UnifiedArffRow>> divideRowsToTrainAndTest(
			List<UnifiedArffRow> input) {
		List<UnifiedArffRow> train = new ArrayList<UnifiedArffRow>();
		List<UnifiedArffRow> test = new ArrayList<UnifiedArffRow>();

		List<UnifiedArffRow> healthList = input.stream()
				.filter(x -> x.getDestinationClass() == 0)
				.collect(Collectors.toList());
		List<UnifiedArffRow> sickList = input.stream()
				.filter(x -> x.getDestinationClass() == 1)
				.collect(Collectors.toList());

		int limit = (int) (healthList.size() * trainTestRatio);
		for (int i = 0; i < healthList.size(); i++) {
			UnifiedArffRow obj = healthList.get(i);
			if (i < limit) {
				train.add(obj);
			} else {
				test.add(obj);
			}
		}

		limit = (int) (sickList.size() * trainTestRatio);
		for (int i = 0; i < sickList.size(); i++) {
			UnifiedArffRow obj = sickList.get(i);
			if (i < limit) {
				train.add(obj);
			} else {
				test.add(obj);
			}
		}
		return new MutablePair<List<UnifiedArffRow>, List<UnifiedArffRow>>(
				train, test);
	}

	public static Pair<List<Patient>, List<Patient>> dividePatientsToTrainAndTest(
			List<Patient> input) {
		List<Patient> train = new ArrayList<Patient>();
		List<Patient> test = new ArrayList<Patient>();

		List<Patient> healthList = input.stream()
				.filter(x -> x.getDestinationClass() == 0)
				.collect(Collectors.toList());
		List<Patient> sickList = input.stream()
				.filter(x -> x.getDestinationClass() == 1)
				.collect(Collectors.toList());

		int limit = (int) (healthList.size() * trainTestRatio);
		for (int i = 0; i < healthList.size(); i++) {
			Patient obj = healthList.get(i);
			if (i < limit) {
				train.add(obj);
			} else {
				test.add(obj);
			}
		}

		limit = (int) (sickList.size() * trainTestRatio);
		for (int i = 0; i < sickList.size(); i++) {
			Patient obj = sickList.get(i);
			if (i < limit) {
				train.add(obj);
			} else {
				test.add(obj);
			}
		}
		return new MutablePair<List<Patient>, List<Patient>>(train, test);
	}
}
