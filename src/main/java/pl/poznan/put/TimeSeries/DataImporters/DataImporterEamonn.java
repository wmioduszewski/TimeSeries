package pl.poznan.put.TimeSeries.DataImporters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public class DataImporterEamonn {

	private String path;
	private List<UnifiedRecord> records;

	public DataImporterEamonn(String path) {
		this.path = path;
		records = new ArrayList<UnifiedRecord>();
	}

	private void readData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringTokenizer tokenizer;

		String currLine = br.readLine();

		while (currLine != null) {
			tokenizer = new StringTokenizer(currLine);			
			double destClass = Double.parseDouble(tokenizer.nextToken());
			int valsCount = tokenizer.countTokens();
			List<Float> vals = new ArrayList<Float>();
			for (int i = 0; i < valsCount; i++) {
				vals.add(Float.parseFloat(tokenizer.nextToken()));
			}
			UnifiedRecord currRecord = new UnifiedRecord(destClass,
					vals);
			records.add(currRecord);
			currLine = br.readLine();
		}
		br.close();
	}

	public List<UnifiedRecord> ImportEamonnData() throws Exception {
		readData();

		int alphabeatSize = Integer.parseInt(Configuration
				.getProperty("saxAlphabeatSize"));
		int outputLength = Integer.parseInt(Configuration
				.getProperty("saxOutputLength"));
		
		for (UnifiedRecord record : records) {
			String sax = SaxPerformer.TranslateUnifiedRecordToString(record,
					outputLength, alphabeatSize);
			record.setSaxString(sax);
		}
		
		System.out.println(String.format("Read %d records from file: %s",records.size(), path));
		return records;
	}

}
