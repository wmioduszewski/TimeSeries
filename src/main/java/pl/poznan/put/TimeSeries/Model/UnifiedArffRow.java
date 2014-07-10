package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public class UnifiedArffRow {

	private List<RegressionResult> regressionResults;
	private double destinationClass;

	public UnifiedArffRow(List<RegressionResult> regressionResults,
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
