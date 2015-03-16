package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.RegressionArffRow;

public class PatientUtils {

	private static float trainTestRatio = CommonConfig.getInstance().getTrainToTestRatio();

	// TODO: MINIMIZE THIS METHODS TO SINGLE ONE

	public static Pair<List<RegressionArffRow>, List<RegressionArffRow>> divideRowsToTrainAndTest(
			List<RegressionArffRow> input) {
		List<RegressionArffRow> train = new ArrayList<RegressionArffRow>();
		List<RegressionArffRow> test = new ArrayList<RegressionArffRow>();

		List<RegressionArffRow> healthList = input.stream()
				.filter(x -> x.getDestinationClass() == 0)
				.collect(Collectors.toList());
		List<RegressionArffRow> sickList = input.stream()
				.filter(x -> x.getDestinationClass() == 1)
				.collect(Collectors.toList());

		int limit = (int) (healthList.size() * trainTestRatio);
		for (int i = 0; i < healthList.size(); i++) {
			RegressionArffRow obj = healthList.get(i);
			if (i < limit) {
				train.add(obj);
			} else {
				test.add(obj);
			}
		}

		limit = (int) (sickList.size() * trainTestRatio);
		for (int i = 0; i < sickList.size(); i++) {
			RegressionArffRow obj = sickList.get(i);
			if (i < limit) {
				train.add(obj);
			} else {
				test.add(obj);
			}
		}
		return new MutablePair<List<RegressionArffRow>, List<RegressionArffRow>>(
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
