package pl.poznan.put.TimeSeries.Model;

public class RegressionResult {

	private double slope;
	private double intercept;

	public RegressionResult(double d, double e) {
		super();
		this.slope = d;
		this.intercept = e;
	}

	public double getSlope() {
		return slope;
	}

	public double getIntercept() {
		return intercept;
	}

}
