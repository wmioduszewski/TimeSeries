package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;
import edu.hawaii.jmotif.lib.ts.TSException;

public abstract class PatientDataImporterBase {

	protected String _filepath;
	List<Patient> patients;

	public PatientDataImporterBase(String filePath) {
		this._filepath = filePath;
	}

	public abstract List<Patient> importData() throws IOException;

	protected void computeSaxForPatients() {
		int alphabeatSize = CommonConfig.getInstance().getSaxAlphabeatSize();

		int outputLength = CommonConfig.getInstance().getSaxOutputLength();
		
		for (Patient patient : patients) {
			String sax = null;
			try {
				sax = SaxPerformer.TranslateTimeSeriesToString(patient,
						outputLength, alphabeatSize);
			} catch (CloneNotSupportedException | TSException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Patient> ImportData(int fromIndex, int toIndex)
			throws IOException {
		return importData().subList(fromIndex, toIndex);
	}

}
