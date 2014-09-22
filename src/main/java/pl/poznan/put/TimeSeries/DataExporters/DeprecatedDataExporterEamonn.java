package pl.poznan.put.TimeSeries.DataExporters;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Util.Configuration;

public class DeprecatedDataExporterEamonn {

	private List<UnifiedRecord> records;

	public DeprecatedDataExporterEamonn(List<UnifiedRecord> records) {
		this.records = records;
	}

	public void Export(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		for (UnifiedRecord record : records) {
			String text = String.format("%e %s", record.getDestinationClass(),
					record.getSaxString());
			writer.write(text);
		}

		writer.flush();
		writer.close();
	}

	public void ConstructArff(String destinationPath) {
		SaxArffExporter translator = new SaxArffExporter(
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
