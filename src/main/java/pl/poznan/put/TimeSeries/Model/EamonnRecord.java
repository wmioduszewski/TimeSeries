package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public class EamonnRecord implements IRecord {

	private double destClass;
	private List<Float> vals;
	private String saxString;

	public EamonnRecord(double destClass, List<Float> vals) {
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
	
	public void addValueAtTheEnd(Float value){
		vals.add(value);
	}
	
	public void addValueAtTheBeginning(Float value){
		vals.add(0,value);
	}

}
