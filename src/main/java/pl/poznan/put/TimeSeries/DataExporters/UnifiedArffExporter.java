package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.Configuration;

public class UnifiedArffExporter {
	
	private String relationTitle;
	private StringBuilder arffFileContent;
	private Map<String, String> attributes;
	private List<UnifiedArffRow> records;
	
	public UnifiedArffExporter(String relationTitle) {
		super();
		arffFileContent = new StringBuilder();
		attributes = new LinkedHashMap<String, String>();
		this.relationTitle = relationTitle;
	}
	
	public void saveUnifiedRecordsToArffData(List<UnifiedArffRow> records,
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
	
	private void insertRelationName() {
		arffFileContent.append("@RELATION " + relationTitle + "\n");
	}
	
	private void insertAttributesNames() {
		for (Entry<String, String> attr : attributes.entrySet()) {
			arffFileContent.append("@ATTRIBUTE " + attr.getKey() + " "
					+ attr.getValue() + "\n");
		}
	}
	
	private void assignAttributes() {

		String attrType = "number";
		int regressionCount = records.stream().findFirst().get().getRegressionResults().size();
		for (int i = 0; i < regressionCount; i++) {
			attributes.put(String.format("slope%d,", i + 1), attrType);
			attributes.put(String.format("intercept%d,", i + 1), attrType);
		}

		attributes.put("destClass", attrType);
	}
	
	private void insertData() {
		arffFileContent.append("@DATA\n");

		for (UnifiedArffRow record : records) {
			String destClass = String.valueOf(record.getDestinationClass());
			List<RegressionResult> regResults = record.getRegressionResults();
			for(RegressionResult result : record.getRegressionResults()){
				arffFileContent.append(result.getSlope() + "," + result.getIntercept() + ",");
			}
			
			arffFileContent.append(destClass + "\n");
		}
	}
	
	
}
