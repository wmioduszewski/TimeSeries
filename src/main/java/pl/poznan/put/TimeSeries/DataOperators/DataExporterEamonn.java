package pl.poznan.put.TimeSeries.DataOperators;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.RecordToArffTranslator;

public class DataExporterEamonn {

	private List<UnifiedRecordType> records;

	public DataExporterEamonn(List<UnifiedRecordType> records) {
		this.records = records;
	}

	public void Export(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		for (UnifiedRecordType record : records) {
			String text = String.format("%e %s", record.getDestClass(),
					record.getSaxString());
			writer.write(text);
		}

		writer.flush();
		writer.close();
	}

	public void ConstructArff(String destinationPath) {
		RecordToArffTranslator translator = new RecordToArffTranslator(
				"Lighting2");
		try {
			int attrLength = Integer.parseInt(Configuration
					.getProperty("saxAttributeLength"));
			translator.saveUnifiedRecordsToArffData(records, destinationPath,
					attrLength, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
