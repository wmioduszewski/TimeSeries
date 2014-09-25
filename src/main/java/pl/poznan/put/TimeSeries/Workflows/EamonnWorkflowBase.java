package pl.poznan.put.TimeSeries.Workflows;

import java.util.List;

import pl.poznan.put.TimeSeries.DataImporters.DataImporterEamonn;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.Configuration;

public abstract class EamonnWorkflowBase extends WorkflowBase {

	protected List<EamonnRecord> records;

	@Override
	protected void importData() {
		DataImporterEamonn importer = new DataImporterEamonn(
				Configuration.getProperty("singleDataPath"));
		try {
			records = importer.ImportEamonnData();
		} catch (Exception e) {
			System.out.println("Eamonn data import failed.");
			e.printStackTrace();
		}
	}

}
