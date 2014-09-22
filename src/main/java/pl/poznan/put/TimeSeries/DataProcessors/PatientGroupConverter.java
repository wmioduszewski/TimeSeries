package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Constants.TimeLimit;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.PatientGroup;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Util.DataAverager;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public class PatientGroupConverter {

//	public static List<UnifiedRecord> RegressionConversion(
//			List<PatientGroup> patientGroup) throws Exception {
//
//		List<UnifiedRecord> result = new ArrayList<UnifiedRecord>();
//		for (TimeLimit timeLimit : Limits.TimeLimits) {
//			List<Float> vals = new ArrayList<Float>();
//			double destClass = 0;
//			for (int i = 0; i < Limits.AgeLimits.size(); i++) {
//				AgeLimit ageLimit = Limits.AgeLimits.get(i);
//				PatientGroup currGroup = patientGroup.stream()
//						.filter(x -> x.getTimeLimit() == timeLimit)
//						.filter(x -> x.getAgeLimit() == ageLimit).findFirst()
//						.get();
//
//				List<Float> avgs = DataAverager.averageData(currGroup);
//				RegressionResult regression = RegressionCalculator
//						.ComputeRegression(avgs);
//				
//				//TODO: IS THIS METHOD HANDY ?
////				vals[2*i] = regression.getSlope();
////				vals[2*i + 1] = regression.getIntercept();
//				destClass = currGroup.isSick() ? 1 : 0;
//			}
//			UnifiedRecord record = new UnifiedRecord(destClass, vals);
//			result.add(record);
//		}
//
//		return result;
//	}
}
