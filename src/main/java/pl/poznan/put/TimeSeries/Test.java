package pl.poznan.put.TimeSeries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;

public class Test {

	private static List<EamonnRecord> records;
	private static List<EamonnRecord> hashedRecords;

	private static void readData(String pathToFile) throws IOException {
		records = new ArrayList<EamonnRecord>();
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
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
			EamonnRecord currRecord = new EamonnRecord(destClass, vals);
			records.add(currRecord);
			currLine = br.readLine();
		}
		br.close();
	}

	private static void hashData() {
		hashedRecords = new ArrayList<EamonnRecord>();
		for (EamonnRecord eamonnRecord : records) {
			List<Float> newVals = new ArrayList<Float>();
			for (Float value : eamonnRecord.getValues()) {
				newVals.add(value * 7 / 2);
			}
			EamonnRecord newRecord = new EamonnRecord(
					eamonnRecord.getDestinationClass(), newVals);
			hashedRecords.add(newRecord);
		}
	}

	private static void exportRecords(String path) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		for (EamonnRecord record : hashedRecords) {
			writer.write(String.format("%f", record.getDestinationClass()));
			for (Float value : record.getValues()) {
				writer.write(String.format(" %f", value));
			}
			writer.write(System.getProperty("line.separator"));
		}
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		readData("C:/Users/womi/Documents/prv/stationary data/dataset1/ECG200/ECG200_TRAIN");
		hashData();
		exportRecords("SAMPLEDATASET_TRAIN");
		System.out.println("end.");
	}

}
