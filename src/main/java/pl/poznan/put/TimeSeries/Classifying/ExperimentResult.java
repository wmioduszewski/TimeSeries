package pl.poznan.put.TimeSeries.Classifying;

public class ExperimentResult {

	private double accuracy;
	private double f1;
	private double gMean;
	private double pcc;
	private double sensitivity;
	private double specificity;

	public ExperimentResult(double accuracy, double sensitivity,
			double specificity, double gMean, double f1, double pcc) {
		super();
		this.accuracy = accuracy;
		this.sensitivity = sensitivity;
		this.specificity = specificity;
		this.gMean = gMean;
		this.f1 = f1;
		this.pcc = pcc;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getF1() {
		return f1;
	}

	public double getgMean() {
		return gMean;
	}

	public double getPcc() {
		return pcc;
	}

	public double getSensitivity() {
		return sensitivity;
	}

	public double getSpecificity() {
		return specificity;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Accuracy: \t%f\n", accuracy));
		sb.append(String.format("F1 score: \t%f\n", f1));
		sb.append(String.format("Sensitivity: \t%f\n", sensitivity));
		sb.append(String.format("Specificity: \t%f\n", specificity));
		sb.append(String.format("G-Mean: \t%f\n", gMean));
		sb.append(String.format("PCC: \t\t%f\n", pcc));
		return sb.toString();
	}

	public void add(ExperimentResult result) {
		this.accuracy += result.accuracy;
		this.sensitivity += result.sensitivity;
		this.specificity += result.specificity;
		this.f1 += result.f1;
		this.gMean += result.gMean;
		this.pcc += result.pcc;
	}

	public void divideBy(double divisor) {
		this.accuracy /= divisor;
		this.sensitivity /= divisor;
		this.specificity /= divisor;
		this.f1 /= divisor;
		this.gMean /= divisor;
		this.pcc /= divisor;

	}

}
