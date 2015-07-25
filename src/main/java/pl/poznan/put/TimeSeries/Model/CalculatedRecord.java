package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class CalculatedRecord implements IAssignedClass{

	private ArrayList<HashMap<String, Integer>> periodicNgrams;
	private double destClass;

	public CalculatedRecord(ArrayList<HashMap<String, Integer>> periodicNgrams,
			double destClass) {
		super();
		this.periodicNgrams = periodicNgrams;
		this.destClass = destClass;
	}

	public ArrayList<HashMap<String, Integer>> getPeriodicNgrams() {
		return periodicNgrams;
	}

	public double getDestinationClass() {
		return destClass;
	}

}
