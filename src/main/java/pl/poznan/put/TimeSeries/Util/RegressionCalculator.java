package pl.poznan.put.TimeSeries.Util;

import pl.poznan.put.TimeSeries.Model.RegressionResult;

import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class RegressionCalculator {

	public static RegressionResult ComputeRegression(List<Float> xArray, List<Float> yArray){
		
		SimpleRegression regression = new SimpleRegression();
		regression.addData(14, 10);
		regression.addData(15, 9);
		regression.addData(16, 18);
		regression.addData(17, 25);
		//5,4x - 68.2
		
		RegressionResult res = new RegressionResult(regression.getSlope(), regression.getIntercept());
		
		return res;
	}
	
}
