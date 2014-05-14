package pl.poznan.put.TimeSeries.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;

public class RecordToArffTranslator {
	private String relationTitle;
	private StringBuilder arffFileContent;
	private Map<String, String> attributes;
	private List<UnifiedRecordType> records;

	public RecordToArffTranslator(String relationTitle) {
		arffFileContent = new StringBuilder();
		attributes = new HashMap<String, String>();
		this.relationTitle = relationTitle;
	}
	
	public void saveUnifiedRecordsToArffData(List<UnifiedRecordType> records,
			String destinationPath) throws FileNotFoundException {
		this.records = records;
		buildFileContent();
		PrintWriter writer = new PrintWriter(new File(destinationPath));
		writer.write(arffFileContent.toString());
		writer.flush();
		writer.close();
	}

	private void buildFileContent() {
		assignAttributes();
		insertRelationName();
		insertAttributesNames();
		insertData();
	}

	private void insertData() {
		arffFileContent.append("@DATA\n");

		for (UnifiedRecordType record: records) {
			String diagnosis = String.valueOf(record.getDestClass());
			arffFileContent.append(record.getSaxString()+ "," + diagnosis
					+ "\n");
		}
	}

	private void assignAttributes() {
		attributes.put("saxString", "string");		
		List<Object> classList = records.stream().map(x->x.getDestClass()).collect(Collectors.toList());
		List<Object> distinct = classList.stream().map(x->x).distinct().collect(Collectors.toList());
		
		String classes = "";
		for (Object problemClass : distinct) {
			if(classes.length()>0)
				classes+=",";
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
