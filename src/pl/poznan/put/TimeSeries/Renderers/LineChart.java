package pl.poznan.put.TimeSeries.Renderers;

import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class LineChart {

	public static ChartPanel PaintPatientChart(Patient patient) {
		XYDataset dataset = createDataset(patient);
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		
		return panel;
	}
	
	public static ChartPanel PaintPatientsChart(Patient[] patients) {
		XYDataset dataset = createDatasets(patients);
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(500, 270));
		return panel;
	}
	
	private static XYDataset createDatasets(Patient[] patients) {
		
		XYSeriesCollection collection = new XYSeriesCollection();
		
		for (Patient patient : patients) {
			XYSeries series = new XYSeries(patient.getId());
			for (Characteristic c : patient.getCharacteristics()) {
				series.add(c.getExaminationTime().getMillisOfDay(), c.getTfadj());
			}
			collection.addSeries(series);	
		}

		return collection;
	}

	private static XYDataset createDataset(Patient patient) {

		final XYSeries series = new XYSeries(patient.getId());
		for (Characteristic c : patient.getCharacteristics()) {
			series.add(c.getExaminationTime().getMillisOfDay(), c.getTfadj());
		}

		XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);

		return collection;

	}

	private static JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(
				"Line Chart Patient", // chart title
				"X", // x axis label
				"Y", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);

		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}

}
