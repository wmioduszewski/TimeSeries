package pl.poznan.put.TimeSeries.Renderers;

import java.util.List;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class CleanChart extends ChartBase {

	protected XYDataset createDataset(List<Patient> patients) {

		TimeSeriesCollection collection = new TimeSeriesCollection();

		for (Patient patient : patients) {
			TimeSeries series = new TimeSeries(patient.getChartCaption());
			for (Characteristic c : patient.getCharacteristics()) {
				series.addOrUpdate(c.getFreeChartExTime(), c.getTfadj());
			}
			collection.addSeries(series);
		}
		return collection;
	}

	@Override
	public String getChartPrefix() {
		return "Clean";
	}
}
