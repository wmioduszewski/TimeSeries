package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Util.Configuration;

public abstract class PatientWorkflowBase extends WorkflowBase {

	protected List<Patient> patients;

	public PatientWorkflowBase() {
		super();
		patients = new ArrayList<Patient>();
	}

	
	//TODO: move to workflow base and make classes count independent and type independent
	
	protected void ReportInputStatistics(List<UnifiedArffRow> train,
			List<UnifiedArffRow> test) {
		long trainHealtCount = train.stream()
				.filter(x -> x.getDestinationClass() == 0).count();
		long trainSickCount = train.stream()
				.filter(x -> x.getDestinationClass() == 1).count();

		long testHealtCount = test.stream()
				.filter(x -> x.getDestinationClass() == 0).count();
		long testSickCount = test.stream()
				.filter(x -> x.getDestinationClass() == 1).count();

		System.out.println(String.format(
				"Train set has %d patients health and %d patients sick.",
				trainHealtCount, trainSickCount));
		System.out.println(String.format(
				"Test set has %d patients health and %d patients sick.",
				testHealtCount, testSickCount));
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
