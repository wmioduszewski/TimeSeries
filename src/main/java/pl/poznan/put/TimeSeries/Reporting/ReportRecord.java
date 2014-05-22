package pl.poznan.put.TimeSeries.Reporting;

import java.util.LinkedList;

public class ReportRecord {

	private String datasetName;
	private LinkedList<Double> accuracies;

	public ReportRecord(String datasetName) {
		super();
		this.datasetName = datasetName;
		accuracies = new LinkedList<Double>();
	}

	public void AddAccuracy(Double accuracy) {
		accuracies.add(accuracy);
	}

	public String getDatasetName() {
		return datasetName;
	}

	public LinkedList<Double> getAccuracies() {
		return accuracies;
	}

}
