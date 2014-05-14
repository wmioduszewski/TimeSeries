package pl.poznan.put.TimeSeries.Sax;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import edu.hawaii.jmotif.lib.ts.TPoint;
import edu.hawaii.jmotif.lib.ts.TSException;
import edu.hawaii.jmotif.lib.ts.Timeseries;
import edu.hawaii.jmotif.logic.sax.SAXFactory;

public class SaxPerformer {

	public static String TranslateTimeSeriesToString(Patient patient,
			int outputLength, int alphabeatSize)
			throws CloneNotSupportedException, TSException {

		List<Characteristic> chars = patient.getCharacteristics();
		double[] vals = new double[chars.size()];
		long[] times = new long[chars.size()];

		for (int i = 0; i < chars.size(); i++) {
			vals[i] = chars.get(i).getTfadj();
			times[i] = chars.get(i).getExaminationTime().getMillis();
		}

		Timeseries series = new Timeseries(vals, times);
		String sax = SAXFactory.ts2string(series, outputLength, alphabeatSize);

		return sax;
	}

	public static String TranslateUnifiedRecordToString(
			UnifiedRecordType record, int outputLength, int alphabeatSize)
			throws Exception {

		double[] vals = record.getVals();
		long[] times = new long[vals.length];

		for (int i = 0; i < vals.length; i++) {
			times[i] = i;
		}
		Timeseries series = new Timeseries(vals, times);
		String sax = SAXFactory.ts2string(series, outputLength, alphabeatSize);

		return sax;
	}

}
