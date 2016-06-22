package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Config;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public abstract class PatientDataImporterBase implements IDataImporter{

	protected String _filepath;
	protected List<Patient> patients;

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

	public List<Patient> importData(int fromIndex, int toIndex)
			throws IOException {
		return importData().subList(fromIndex, toIndex);
	}
	
	protected DateTime getDateTimeByStringClock(String clock) throws Exception {

		int minutes = 0;
		int hours = 0;
		if (clock.contains(":")) {
			String[] minuteElems = clock.trim().split(":");

			minutes = Integer.parseInt(minuteElems[1]);
			hours = Integer.parseInt(minuteElems[0]);
		} else {
			throw new Exception("Data is in wrong format");
		}
		int days = 2;
		hours = hours < 24 ? hours : 0;
		if (hours >= 12)
			days = 1;
		DateTime dt = new DateTime(2014, 4, days, hours, minutes, 0);
		return dt;
	}

}
