package pl.poznan.put.TimeSeries.DataImporters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public class DataImporterEamonn {

	private String path;
	private List<UnifiedRecordType> records;

	public DataImporterEamonn(String path) {
		this.path = path;
		records = new ArrayList<UnifiedRecordType>();
	}

	private void readData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringTokenizer tokenizer;

		String currLine = br.readLine();

		while (currLine != null) {
			tokenizer = new StringTokenizer(currLine);			
			double destClass = Double.parseDouble(tokenizer.nextToken());
			int valsCount = tokenizer.countTokens();
			double[] vals = new double[valsCount];
			for (int i = 0; i < valsCount; i++) {
				vals[i] = Double.parseDouble(tokenizer.nextToken());
			}
			UnifiedRecordType currRecord = new UnifiedRecordType(destClass,
					vals);
			records.add(currRecord);
			currLine = br.readLine();
		}
		br.close();
	}

	public List<UnifiedRecordType> ImportEamonnData() throws Exception {
		readData();

		int alphabeatSize = Integer.parseInt(Configuration
				.getProperty("saxAlphabeatSize"));
		int outputLength = Integer.parseInt(Configuration
				.getProperty("saxOutputLength"));
		;
		for (UnifiedRecordType record : records) {
			String sax = SaxPerformer.TranslateUnifiedRecordToString(record,
					outputLength, alphabeatSize);
			record.setSaxString(sax);
		}
		return records;
	}

}
