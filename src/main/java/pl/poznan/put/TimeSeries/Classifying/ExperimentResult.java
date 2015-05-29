package pl.poznan.put.TimeSeries.Classifying;

public class ExperimentResult {

	private double accuracy;
	private double loss01;
	private double squaredError;

	public ExperimentResult(double accuracy, double loss01, double squaredError) {
		super();
		this.accuracy = accuracy;
		this.loss01 = loss01;
		this.squaredError = squaredError;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getLoss01() {
		return loss01;
	}

	public double getSquaredError() {
		return squaredError;
	}
	
	@Override
	public String toString() {
		return String.format("Accuracy:\t%f\nLoss 01:\t%f\nSquared err:\t%f\n", accuracy,loss01,squaredError);
	}

}
