package pl.poznan.put.TimeSeries.Reporting;

import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.TimeSeries.Util.Config;

public class EntireReport {

	private List<ReportRecord> records;
	private List<String> classifiersNames;
	private int alphabeatUsed;
	private int outputLengthUsed;

	public EntireReport() {
		records = new LinkedList<ReportRecord>();
		classifiersNames = new LinkedList<String>();

		Config config = Config.getInstance();
		alphabeatUsed = config.getSaxAlphabeatSize();
		outputLengthUsed = config.getSaxOutputLength();
	}

	public List<ReportRecord> getRecords() {
		return records;
	}

	public List<String> getClassifiersNames() {
		return classifiersNames;
	}

	public int getAlphabeatUsed() {
		return alphabeatUsed;
	}

	public int getOutputLengthUsed() {
		return outputLengthUsed;
	}

	public void AddClassifier(String classifier) {
		classifiersNames.add(classifier);
	}

	public void AddReportRecord(ReportRecord record) {
		records.add(record);
	}

}
