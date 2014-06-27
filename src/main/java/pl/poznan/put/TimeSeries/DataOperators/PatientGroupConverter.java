package pl.poznan.put.TimeSeries.DataOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Constants.TimeLimit;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.PatientGroup;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.DataAverager;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public class PatientGroupConverter {

	public static List<UnifiedRecordType> RegressionConversion(
			List<PatientGroup> patientGroup) throws Exception {

		List<UnifiedRecordType> result = new ArrayList<UnifiedRecordType>();
		for (TimeLimit timeLimit : Limits.TimeLimits) {
			double[] vals = new double[2 * Limits.AgeLimits.size()];
			double destClass = 0;
			for (int i = 0; i < Limits.AgeLimits.size(); i++) {
				AgeLimit ageLimit = Limits.AgeLimits.get(i);
				PatientGroup currGroup = patientGroup.stream()
						.filter(x -> x.getTimeLimit() == timeLimit)
						.filter(x -> x.getAgeLimit() == ageLimit).findFirst()
						.get();

				List<Float> avgs = DataAverager.averageData(currGroup);
				RegressionResult regression = RegressionCalculator
						.ComputeRegression(avgs);
				vals[2*i] = regression.getSlope();
				vals[2*i + 1] = regression.getIntercept();
				destClass = currGroup.isSick() ? 1 : 0;
			}
			UnifiedRecordType record = new UnifiedRecordType(destClass, vals);
			result.add(record);
		}

		return result;
	}
}
