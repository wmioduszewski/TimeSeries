package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public class RegressionArffRow {

	private List<RegressionResult> regressionResults;
	private double destinationClass;

	public RegressionArffRow(List<RegressionResult> regressionResults,
			double destinationClass) {
		super();
		this.regressionResults = regressionResults;
		this.destinationClass = destinationClass;
	}

	public List<RegressionResult> getRegressionResults() {
		return regressionResults;
	}

	public double getDestinationClass() {
		return destinationClass;
	}

}
