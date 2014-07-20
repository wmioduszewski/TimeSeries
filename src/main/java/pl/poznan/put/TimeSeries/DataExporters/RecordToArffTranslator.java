package pl.poznan.put.TimeSeries.DataExporters;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.Configuration;

public class RecordToArffTranslator extends ArffExporterBase {
	private List<UnifiedRecordType> records;
	private int attrLength;
	private int recordLength;
	private int saxAlphabeatSize;
	private boolean isNominal;

	public RecordToArffTranslator(String relationTitle) {
		super(relationTitle);
	}

	public void saveUnifiedRecordsToArffData(List<UnifiedRecordType> records,
			String destinationPath, int attrLength, boolean isNominal)
			throws FileNotFoundException {
		this.records = records;
		this.attrLength = attrLength;
		this.isNominal = isNominal;
		this.recordLength = Integer.parseInt(Configuration
				.getProperty("saxOutputLength"));
		this.saxAlphabeatSize = Integer.parseInt(Configuration
				.getProperty("saxAlphabeatSize"));
		performExport(destinationPath);
	}

	@Override
	protected void insertData() {
		arffFileContent.append("@DATA\n");

		for (UnifiedRecordType record : records) {
			String diagnosis = String.valueOf(record.getDestinationClass());
			String sax = record.getSaxString();
			for (int i = 0; i < sax.length(); i += attrLength) {
				for (int j = 0; j < attrLength; j++) {
					if (i + j >= sax.length())
						break;
					arffFileContent.append(sax.charAt(i + j));
				}
				arffFileContent.append(",");
			}
			arffFileContent.append(diagnosis + "\n");
		}
	}

	@Override
	protected void assignAttributes() {
		String attrType = "string";
		if (isNominal) {
			attrType = "{";
			for (String possVal : getNominals()) {
				attrType += possVal + ",";
			}
			attrType = attrType.substring(0, attrType.length() - 1);
			attrType += "}";
		}

		for (int i = 0; i < Math.ceil(recordLength / (float) attrLength); i++) {
			attributes.put(String.format("saxString%d,", i + 1), attrType);
		}

		List<Object> classList = records.stream()
				.map(x -> x.getDestinationClass()).collect(Collectors.toList());
		List<Object> distinct = classList.stream().map(x -> x).distinct()
				.collect(Collectors.toList());

		String classes = "";
		for (Object problemClass : distinct) {
			if (classes.length() > 0)
				classes += ",";
			classes += problemClass;
		}

		attributes.put("class", "{" + classes + "}");
	}

	private List<String> getNominals() {
		LinkedList<char[]> items = new LinkedList<char[]>();
		char[] item = new char[attrLength];
		char[] input = new char[saxAlphabeatSize];
		for (int i = 0; i < saxAlphabeatSize; i++) {
			input[i] = (char) ('a' + i);
		}
		permute(items, input, item, 0);
		List<String> possibleVals = new LinkedList<String>();
		for (char[] rep : items) {
			possibleVals.add(new String(rep));
		}
		return possibleVals;
	}

	private static void permute(LinkedList<char[]> reps, char[] input,
			char[] item, int count) {
		if (count < item.length) {
			for (int i = 0; i < input.length; i++) {
				item[count] = input[i];
				permute(reps, input, item, count + 1);
			}
		} else {
			reps.add(item.clone());
		}
	}

}
