package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Config;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public abstract class PatientDataImporterBase {

	protected String _filepath;
	List<Patient> patients;

	public PatientDataImporterBase(String filePath) {
		this._filepath = filePath;
	}

	public abstract List<Patient> importData() throws IOException;

	protected void computeSaxForPatients() {
		int alphabeatSize = Config.getInstance().getSaxAlphabeatSize();

		int outputLength = Config.getInstance().getSaxOutputLength();
		try {
			SaxPerformer.applyNormalizedSax(patients, outputLength,
					alphabeatSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Patient> ImportData(int fromIndex, int toIndex)
			throws IOException {
		return importData().subList(fromIndex, toIndex);
	}

}
