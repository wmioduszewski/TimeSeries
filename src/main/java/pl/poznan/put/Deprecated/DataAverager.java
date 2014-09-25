package pl.poznan.put.Deprecated;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class DataAverager {

	public static List<Float> averageData(PatientGroup patientGroup)
			throws Exception {

		List<MyPair> summary = new ArrayList<MyPair>();

		for (Patient patient : patientGroup.getPatients()) {
			List<Characteristic> chars = patient.getCharacteristics();
			for (int j = 0; j < chars.size(); j++) {

				if (summary.size() == j) {
					MyPair pair;
					pair = new MyPair();// {1,2};
					pair.count = 1;
					pair.sum = chars.get(j).getTfadj();
					summary.add(pair);
				} else {
					MyPair pair = summary.get(j);
					pair.count++;
					pair.sum += chars.get(j).getTfadj();
				}
			}
		}
		List<Float> result = new ArrayList<Float>();
		for (int i = 0; i < summary.size(); i++) {
			MyPair elem = summary.get(i);
			Float res = elem.sum / elem.count;
			result.add(res);
		}

		return result;
	}

	static class MyPair {
		float sum;
		int count;
	}
}
