package pl.poznan.put.TimeSeries.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	private int attrLength;
	private int recordLength;
	private boolean isNominal;

	public RecordToArffTranslator(String relationTitle) {
		arffFileContent = new StringBuilder();
		attributes = new LinkedHashMap<String, String>();
		this.relationTitle = relationTitle;
	}

	public void saveUnifiedRecordsToArffData(List<UnifiedRecordType> records,
			String destinationPath, int attrLength, boolean isNominal) throws FileNotFoundException {
		this.records = records;
		this.attrLength = attrLength;
		this.isNominal = isNominal;
		buildFileContent();
		PrintWriter writer = new PrintWriter(new File(destinationPath));
		writer.write(arffFileContent.toString());
		writer.flush();
		writer.close();
	}

	private void buildFileContent() {
		determineRecordLength();
		insertRelationName();
		assignAttributes();		
		insertAttributesNames();
		insertData();
	}

	private void insertData() {
		arffFileContent.append("@DATA\n");

		for (UnifiedRecordType record : records) {
			String diagnosis = String.valueOf(record.getDestClass());
			String sax = record.getSaxString();
			for (int i = 0; i < sax.length(); i+=attrLength) {
				for(int j =0;j<attrLength;j++){
					if(i+j>=sax.length()) 
						break;
					arffFileContent.append(sax.charAt(i+j));
				}
				arffFileContent.append(",");
			}
			arffFileContent.append(diagnosis + "\n");
		}
	}
	
	private void determineRecordLength()
	{
		int max = 0;
		for (UnifiedRecordType record : records) {
			int currLength = record.getSaxString().length();
			if(currLength>max)
				max =currLength;
		}
		this.recordLength = max;
	}

	private void assignAttributes() {
		
		String attrType="string";
		if(isNominal){
			//attrType 
		}
		
		for(int i=0;i<Math.ceil(recordLength/(float)attrLength);i++){
			attributes.put(String.format("saxString%d,", i+1), "string");
		}
		
		List<Object> classList = records.stream().map(x -> x.getDestClass())
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
	
	//private void get

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
