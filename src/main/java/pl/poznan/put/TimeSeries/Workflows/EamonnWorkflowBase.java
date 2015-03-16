package pl.poznan.put.TimeSeries.Workflows;

import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterEamonn;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.SpecificConfig;

public abstract class EamonnWorkflowBase extends WorkflowBase {

	public EamonnWorkflowBase(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption, isDominant);
	}

	protected List<EamonnRecord> records;

	@Override
	protected void importData() {
		DataImporterEamonn importer = new DataImporterEamonn(
				SpecificConfig.getProperty("singleDataPath"));
		try {
			records = importer.importEamonnData();
		} catch (Exception e) {
			System.out.println("Eamonn data import failed.");
			e.printStackTrace();
		}
	}
}
