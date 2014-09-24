package pl.poznan.put.TimeSeries.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class SaxArffCandidateRow {

	private LinkedList<HashMap<String, AtomicInteger>> periodicNgrams;
	private double destClass;

	public SaxArffCandidateRow(
			LinkedList<HashMap<String, AtomicInteger>> periodicNgrams,
			double destClass) {
		super();
		this.periodicNgrams = periodicNgrams;
		this.destClass = destClass;
	}

	public LinkedList<HashMap<String, AtomicInteger>> getPeriodicNgrams() {
		return periodicNgrams;
	}

	public double getDestClass() {
		return destClass;
	}

}
