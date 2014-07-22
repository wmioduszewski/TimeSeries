package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Configuration;

public abstract class PatientWorkflowBase extends WorkflowBase{

	protected List<Patient> patients;
	
	public PatientWorkflowBase() {
		super();
	}

	@Override
	protected void importData() {
		String csvDataPath = Configuration.getProperty("csvDataSet");
		String pureDataPath = Configuration.getProperty("pureDataSet");
		List<Patient> csvPatients = null;
		List<Patient> purePatients = null;
		DataImporterBase importer = new DataImporterCsv(csvDataPath);
		try {
			csvPatients = importer.ImportData();
			importer = new PureDataImporter(pureDataPath);
			purePatients = importer.ImportData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		patients.addAll(csvPatients);
		patients.addAll(purePatients);
	}
}
