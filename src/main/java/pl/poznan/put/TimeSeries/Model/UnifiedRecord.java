package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public class UnifiedRecord implements IRecord {

	private double destClass;
	private List<Float> vals;
	private String saxString;

	public UnifiedRecord(double destClass, List<Float> vals) {
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

	public List<Float> getValues() {
		return vals;
	}

}