package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;

public class PureDataArffExporter {
	private String relationTitle;
	private StringBuilder arffFileContent;
	private Map<String, String> attributes;
	private List<UnifiedRecordType> records;

	public PureDataArffExporter(String relationTitle) {
		arffFileContent = new StringBuilder();
		attributes = new LinkedHashMap<String, String>();
		this.relationTitle = relationTitle;
	}

	public void saveUnifiedRecordsToArffData(List<UnifiedRecordType> records,
			String destinationPath)
			throws FileNotFoundException {
		this.records = records;
		buildFileContent();
		PrintWriter writer = new PrintWriter(new File(destinationPath));
		writer.write(arffFileContent.toString());
		writer.flush();
		writer.close();
	}

	private void buildFileContent() {
		insertRelationName();
		assignAttributes();
		insertAttributesNames();
		insertData();
	}

	private void insertData() {
		arffFileContent.append("@DATA\n");

		for (UnifiedRecordType record : records) {
			String diagnosis = String.valueOf(record.getDestinationClass());
			double[] vals = record.getValues();
			for(int i=0;i<vals.length;i++){
				arffFileContent.append(vals[i]+",");				
			}
			
			arffFileContent.append(diagnosis + "\n");
		}
	}

	private void assignAttributes() {

		String attrType = "NUMERIC";
		
		for (int i = 0; i < Limits.AgeLimits.size(); i++) {
			AgeLimit currLimit = Limits.AgeLimits.get(i);
			attributes.put(String.format("age%d-%dscope,", currLimit.getLowerBound(),currLimit.getUpperBound()), attrType);
			attributes.put(String.format("age%d-%dintercept,", currLimit.getLowerBound(),currLimit.getUpperBound()), attrType);
		}

		List<Object> classList = records.stream().map(x -> x.getDestinationClass())
				.collect(Collectors.toList());
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

	private void insertRelationName() {
		arffFileContent.append("@RELATION " + relationTitle + "\n");
	}

	private void insertAttributesNames() {
		for (Entry<String, String> attr : attributes.entrySet()) {
			arffFileContent.append("@ATTRIBUTE " + attr.getKey() + " "
					+ attr.getValue() + "\n");
		}
	}
}
