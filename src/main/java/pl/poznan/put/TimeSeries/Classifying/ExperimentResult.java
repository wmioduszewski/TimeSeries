package pl.poznan.put.TimeSeries.Classifying;

public class ExperimentResult {

	private double accuracy;
	private double squaredError;

	public ExperimentResult(double accuracy, double squaredError) {
		super();
		this.accuracy = accuracy;
		this.squaredError = squaredError;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getSquaredError() {
		return squaredError;
	}
	
	@Override
	public String toString() {
		return String.format("Accuracy:\t%f\nSquared err:\t%f\n", accuracy,squaredError);
	}

}
