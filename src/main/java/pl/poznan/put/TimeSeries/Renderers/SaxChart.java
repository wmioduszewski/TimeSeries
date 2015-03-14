package pl.poznan.put.TimeSeries.Renderers;

import java.util.List;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Model.Patient;

public class SaxChart extends ChartBase {

	private float globalMin;
	private float globalMax;
	private int saxOutLength;
	private int saxAlfaSize;

	public SaxChart(float globalMin, float globalMax, int saxOutLength, int saxAlfaSize) {
		this.globalMin = globalMin;
		this.globalMax = globalMax;
		this.saxAlfaSize = saxAlfaSize;
		this.saxOutLength = saxOutLength;
	}

	protected XYDataset createDataset(List<Patient> patients) {
		// float globalMin = -20.6f;
		// float globalMax = 48.9f;
		TimeSeriesCollection collection = new TimeSeriesCollection();

		for (Patient patient : patients) {
			TimeSeries series = new TimeSeries(patient.getChartCaption());

			String saxStr = patient.getSaxString();

			int minutesInDay = 60 * 24;
			float period = minutesInDay / (float) saxOutLength;
			float letterWeight = (globalMax - globalMin)
					/ (float) saxAlfaSize;

			for (int i = 0; i < saxStr.length(); i++) {
				DateTime dt = new DateTime(2014, 4, 1, 12, 00);
				DateTime prop = dt.minusMinutes(i * (-1) * (int) period);

				Minute m = new Minute(prop.getMinuteOfHour(),
						prop.getHourOfDay(), prop.getDayOfMonth(),
						prop.getMonthOfYear(), prop.getYear());
				int diff = saxStr.charAt(i) - 'a';
				series.addOrUpdate(m, diff * letterWeight + globalMin);
			}
			collection.addSeries(series);
		}
		return collection;
	}

	@Override
	public String getChartPrefix() {
		return "Sax";
	}
}
