package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;
import edu.hawaii.jmotif.lib.ts.TSException;

public abstract class DataImporterBase {

	protected String _filepath;
	List<Patient> patients;

	public DataImporterBase(String filePath) {
		this._filepath = filePath;
	}

	public abstract List<Patient> ImportData() throws IOException;

	protected void computeSaxForPatients() {
		int alphabeatSize = Integer.parseInt(Configuration
				.getProperty("saxAlphabeatSize"));
		;
		int outputLength = Integer.parseInt(Configuration
				.getProperty("saxOutputLength"));
		;
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
		return ImportData().subList(fromIndex, toIndex);
	}

}
