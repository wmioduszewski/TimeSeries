package pl.poznan.put.TimeSeries.Reporting;

import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.TimeSeries.Util.SpecificConfig;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public class EntireReport {

	private List<ReportRecord> records;
	private List<String> classifiersNames;
	private int alphabeatUsed;
	private int outputLengthUsed;
	private int attributeLengthUsed;

	public EntireReport() {
		records = new LinkedList<ReportRecord>();
		classifiersNames = new LinkedList<String>();

		CommonConfig config = CommonConfig.getInstance();
		alphabeatUsed = config.getSaxAlphabeatSize();
		outputLengthUsed = config.getSaxOutputLength();
		attributeLengthUsed = config.getSaxAttributeLength();
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

	public int getAttributeLengthUsed() {
		return attributeLengthUsed;
	}

	public void AddClassifier(String classifier) {
		classifiersNames.add(classifier);
	}

	public void AddReportRecord(ReportRecord record) {
		records.add(record);
	}

}
