package pl.poznan.put.Deprecated;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.SpecificConfig;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public class DeprecatedDataExporterEamonn {

	private List<EamonnRecord> records;

	public DeprecatedDataExporterEamonn(List<EamonnRecord> records) {
		this.records = records;
	}

	public void Export(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		for (EamonnRecord record : records) {
			String text = String.format("%e %s", record.getDestinationClass(),
					record.getSaxString());
			writer.write(text);
		}

		writer.flush();
		writer.close();
	}

	public void ConstructArff(String destinationPath) {
		SaxArffExporter translator = new SaxArffExporter("Lighting2");
		try {
			int attrLength = CommonConfig.getInstance().getSaxAttributeLength();
			translator.saveUnifiedRecordsToArffData(records, destinationPath,
					attrLength, true);
		} catch (FileNotFoundException e) {
			System.out.println("Error during arff construction.");
			e.printStackTrace();
		}
	}

}
