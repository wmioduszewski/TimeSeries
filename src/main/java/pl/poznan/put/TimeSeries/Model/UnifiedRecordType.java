package pl.poznan.put.TimeSeries.Model;

public class UnifiedRecordType implements IRecord {

	private double destClass;
	private double[] vals;
	private String saxString;

	public UnifiedRecordType(double destClass, double[] vals) {
		super();
		this.destClass = destClass;
		this.vals = vals;
	}

	public String getSaxString() {
		return saxString;
	}

	public void setSaxString(String saxString) {
		this.saxString = saxString;
	}

	public double getDestinationClass() {
		return destClass;
	}

	public double[] getValues() {
		return vals;
	}

}
