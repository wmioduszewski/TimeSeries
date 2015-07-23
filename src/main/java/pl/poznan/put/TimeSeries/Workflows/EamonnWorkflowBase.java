package pl.poznan.put.TimeSeries.Workflows;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterEamonn;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public abstract class EamonnWorkflowBase extends WorkflowBase {

	public EamonnWorkflowBase(DivisionOptions divisionOption) {
		super(divisionOption);
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
