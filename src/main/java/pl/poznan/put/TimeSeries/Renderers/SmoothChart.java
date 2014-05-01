package pl.poznan.put.TimeSeries.Renderers;

import java.util.List;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Smoother;

public class SmoothChart extends ChartBase {

	private boolean isEwma;
	private float omega;
	private int size;

	public SmoothChart(float omega) {
		isEwma = true;
		this.omega = omega;
	}

	public SmoothChart(int smoothingSize) {
		isEwma = false;
		this.size = smoothingSize;
	}

	@Override
	protected XYDataset createDataset(List<Patient> patients) {
		TimeSeriesCollection collection = new TimeSeriesCollection();

		for (Patient patient : patients) {
			TimeSeries series = new TimeSeries(patient.getChartCaption());

			List<Characteristic> smoothCharacteristics;
			if (isEwma) {
				smoothCharacteristics = Smoother.SmoothEWMACharacteristics(
						patient.getCharacteristics(), omega);
			} else {
				smoothCharacteristics = Smoother.SmoothCharacteristics(
						patient.getCharacteristics(), size);
			}

			for (Characteristic c : smoothCharacteristics) {
				series.addOrUpdate(c.getFreeChartExTime(), c.getTfadj());
			}
			collection.addSeries(series);
		}
		return collection;
	}

	@Override
	public String getChartPrefix() {
		return isEwma ? "EWMA" : "Smooth";
	}

}
