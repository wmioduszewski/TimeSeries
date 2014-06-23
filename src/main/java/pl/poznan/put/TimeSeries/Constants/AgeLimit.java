package pl.poznan.put.TimeSeries.Constants;

public class AgeLimit {
	private int lowerBound;
	private int upperBound;

	public AgeLimit(int lowerBound, int upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}
}
