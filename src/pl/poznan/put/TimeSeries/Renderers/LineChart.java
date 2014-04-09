package pl.poznan.put.TimeSeries.Renderers;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.joda.time.LocalTime;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class LineChart extends ApplicationFrame{

	private static final long serialVersionUID = 2458634274354667267L;
	String title;
	
	public LineChart(String title,List<Patient> patients, Dimension dimension) {
		super(title);
		this.title = title;
        final ChartPanel chartPanel = paintPatientChart(patients);
        chartPanel.setPreferredSize(dimension);
        setContentPane(chartPanel);
	}
	
	private ChartPanel paintPatientChart(List<Patient> patients) {
		XYDataset dataset = createDataset(patients);
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(500, 270));
		return panel;
	}
	
	private XYDataset createDataset(List<Patient> patients) {
		
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

	private JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				title, // chart title
				"Time", // x axis label
				"TFADJ", // y axis label
				dataset, // data
				//PlotOrientation.VERTICAL, 
				true, // include legend
				true, // tooltips
				false
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
