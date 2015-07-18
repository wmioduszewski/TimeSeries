package pl.poznan.put.TimeSeries.Util;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
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
	
	public static String TranslateUnifiedRecordToString(EamonnRecord record,
			int outputLength, int alphabeatSize) throws Exception {

		List<Float> floats = record.getValues();
		int size = floats.size();

		double[] vals = new double[size];
		long[] times = new long[size];

		for (int i = 0; i < vals.length; i++) {
			vals[i] = floats.get(i);
			times[i] = i;
		}
		Timeseries series = new Timeseries(vals, times);
		String sax = SAXFactory.ts2string(series, outputLength, alphabeatSize);

		return sax;
	}
	
	public static void applyNormalizedSax(List<EamonnRecord> records,
			int outputLength, int alphabeatSize) throws Exception {
		float globalMax = Float.MIN_VALUE;
		float globalMin = Float.MAX_VALUE;
		for (EamonnRecord eamonnRecord : records) {
			for (Float value : eamonnRecord.getValues()) {
				if(value>globalMax) globalMax = value;
				if(value<globalMin) globalMin = value;
			}
		}
		
		for (EamonnRecord eamonnRecord : records) {
			eamonnRecord.addValueAtTheBeginning(globalMin);
			eamonnRecord.addValueAtTheEnd(globalMax);
		}
		
		for (EamonnRecord eamonnRecord : records) {
			String sax = TranslateUnifiedRecordToString(eamonnRecord, outputLength, alphabeatSize);
			eamonnRecord.setSaxString(sax);
		}
	}

}
