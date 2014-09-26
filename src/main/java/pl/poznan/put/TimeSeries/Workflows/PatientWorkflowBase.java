package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Configuration;

public abstract class PatientWorkflowBase extends WorkflowBase {

	protected List<Patient> patients;

	public PatientWorkflowBase() {
		super();
		patients = new ArrayList<Patient>();
	}

	@Override
	protected void importData() {
		String pureDataPath = Configuration.getProperty("pureDataSet");
		List<Patient> purePatients = null;
		DataImporterBase importer;
		try {
			importer = new PureDataImporter(pureDataPath);
			purePatients = importer.ImportData();
		} catch (IOException e) {
			System.out.println("Patients import failed.");
			e.printStackTrace();
		}
		patients.addAll(purePatients);
	}
}
