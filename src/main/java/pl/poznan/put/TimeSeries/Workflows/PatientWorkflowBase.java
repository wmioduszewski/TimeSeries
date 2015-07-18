package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataImporters.PatientDataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.PatientDataImporterPure;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Util.DataDivider;

public abstract class PatientWorkflowBase extends WorkflowBase {

	protected List<Patient> patients;

	public PatientWorkflowBase(DivisionOptions divisionOption,
			boolean isDominant) {
		super(divisionOption, isDominant);
		patients = new ArrayList<Patient>();
	}

	@Override
	protected void importData() {
		String pureDataPath = CommonConfig.getInstance().getGlaucomaDataSet();
		List<Patient> purePatients = null;
		PatientDataImporterBase importer;
		try {
			importer = new PatientDataImporterPure(pureDataPath);
			purePatients = importer.importData();
		} catch (IOException e) {
			System.out.println("Patients import failed.");
			e.printStackTrace();
		}
		patients.addAll(purePatients);
	}

	protected List<List<Characteristic>> divideData(Patient patient)
			throws Exception {
		List<List<Characteristic>> res = null;
		switch (divisionOption) {
		case Periodic:
			res = DataDivider.dividePatientDataPeriodically(patient);
			break;
		case Regular:
			res = DataDivider.divideCollectionRegularly(
					patient.getCharacteristics(), divisionPartsAmount);
			break;
		case PerThenReg:
			res = DataDivider.dividePatientPeriodicallyThenRegularly(patient,
					divisionPartsAmount);
			break;
		}

		return res;
	}
}
