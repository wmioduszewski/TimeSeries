package pl.poznan.put.TimeSeries.Util;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import edu.hawaii.jmotif.lib.ts.Timeseries;
import edu.hawaii.jmotif.logic.sax.SAXFactory;

public class SaxPerformer {

	public static void applyNormalizedSax(List<? extends IRecord> records,
			int outputLength, int alphabeatSize) throws Exception {
		float globalMax = Float.MIN_VALUE;
		float globalMin = Float.MAX_VALUE;
		for (IRecord record : records) {
			for (Float value : record.getValues()) {
				if (value > globalMax)
					globalMax = value;
				if (value < globalMin)
					globalMin = value;
			}
		}

		for (IRecord record : records) {
			record.addValueAtTheBeginning(globalMin);
			record.addValueAtTheEnd(globalMax);
		}

		for (IRecord record : records) {
			String sax = translateRecordToString(record, outputLength,
					alphabeatSize);
			record.setSaxString(sax);
		}
	}

	public static String translateRecordToString(IRecord record,
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

}
