package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public class EamonnRecord implements IRecord {

	private double destClass;
	private String saxString;
	private List<Float> vals;

	public EamonnRecord(double destClass, List<Float> vals) {
		super();
		this.destClass = destClass;
		this.vals = vals;
	}

	public void addValueAtTheBeginning(Float value) {
		vals.add(0, value);
	}

	public void addValueAtTheEnd(Float value) {
		vals.add(value);
	}

	public double getDestinationClass() {
		return destClass;
	}

	public String getSaxString() {
		return saxString;
	}

	public List<Float> getValues() {
		return vals;
	}

	public void setSaxString(String saxString) {
		this.saxString = saxString;
	}
}
