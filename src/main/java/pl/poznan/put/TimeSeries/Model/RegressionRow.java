package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public class RegressionRow implements IAssignedClass {
	private double destinationClass;
	private List<RegressionResult> regressionResults;

	public RegressionRow(List<RegressionResult> regressionResults,
			double destinationClass) {
		super();
		this.regressionResults = regressionResults;
		this.destinationClass = destinationClass;
	}

	public double getDestinationClass() {
		return destinationClass;
	}

	public List<RegressionResult> getRegressionResults() {
		return regressionResults;
	}
}
