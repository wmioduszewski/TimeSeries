package pl.poznan.put.TimeSeries.Workflows;

import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterEamonn;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public abstract class EamonnWorkflowBase extends WorkflowBase {

	protected List<EamonnRecord> records;

	public EamonnWorkflowBase(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption, isDominant);
	}

	@Override
	protected void importData() {
		DataImporterEamonn importer = new DataImporterEamonn(CommonConfig
				.getInstance().getSingleDataPath());
		try {
			records = importer.importEamonnData();
		} catch (Exception e) {
			System.out.println("Eamonn data import failed.");
			e.printStackTrace();
		}
	}
}
