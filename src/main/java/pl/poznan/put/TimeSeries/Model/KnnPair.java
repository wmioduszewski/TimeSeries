package pl.poznan.put.TimeSeries.Model;

public class KnnPair implements Comparable<KnnPair> {

	private int index;
	private double value;

	public KnnPair(int index, double value) {
		super();
		this.index = index;
		this.value = value;
	}

	@Override
	public int compareTo(KnnPair o) {
		double delta = 0.0000000001;
		double res = this.value - o.value;
		if (Math.abs(res) < delta)
			return 0;

		if (res > 0)
			return 1;
		else
			return -1;
	}

	@Override
	public boolean equals(Object obj) {
		KnnPair o = (KnnPair) obj;
		return compareTo(o) == 0;
	}

	public int getIndex() {
		return index;
	}

	public double getValue() {
		return value;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%d: %f", this.index, this.value);
	}
}
