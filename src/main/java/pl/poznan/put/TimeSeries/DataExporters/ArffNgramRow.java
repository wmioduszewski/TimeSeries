package pl.poznan.put.TimeSeries.DataExporters;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ArffNgramRow {
	
	private String name;
	
	private HashMap<String, AtomicInteger> amounts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, AtomicInteger> getAmounts() {
		return amounts;
	}

	public void setAmounts(HashMap<String, AtomicInteger> amounts) {
		this.amounts = amounts;
	}

	
	

}
