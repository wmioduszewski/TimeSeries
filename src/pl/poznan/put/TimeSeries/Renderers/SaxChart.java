package pl.poznan.put.TimeSeries.Renderers;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;

public class SaxChart extends ApplicationFrame{

	private static final long serialVersionUID = 5590657258624758130L;
	String title;
	int _outLength;
	int alphSz;
	
	public SaxChart(String title,List<Patient> patients, Dimension dimension,int outputLength, int alphabeatSize) {
		super(title);		
		this.title = title;
		alphSz = alphabeatSize;
		_outLength = outputLength;
        final ChartPanel chartPanel = paintPatientChart(patients);
        chartPanel.setPreferredSize(dimension);
        setContentPane(chartPanel);
	}
	
	private ChartPanel paintPatientChart(List<Patient> patients) {
		XYDataset dataset = createDataset(patients);
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		//ChartUtilities.saveChartAsJPEG(new File("testchart"), chart, 1000, 1000);		
		panel.setPreferredSize(new Dimension(500, 270));
		return panel;
	}
	
	private XYDataset createDataset(List<Patient> patients) {
		float min = -20.6f;
		float max = 48.9f;
		TimeSeriesCollection collection = new TimeSeriesCollection();
		
		for (Patient patient : patients) {
			TimeSeries series = new TimeSeries(patient.getChartCaption());
			
			SaxString saxStr =patient.getSaxStrings().get(0); 
			
			int minutesInDay = 60*24;
			float period = minutesInDay / (float) saxStr.getOutputLength();
			float letterWeight = (max - min) / (float) saxStr.getAlphabeatLength(); 
			
			for (int i=0;i<saxStr.getContent().length();i++)
			{
				DateTime dt = new DateTime(2014,4,1,12,00);
				DateTime prop = dt.minusMinutes(i*(-1)*(int)period);
				
				int mt = prop.getMinuteOfHour();
				int hr = prop.getHourOfDay();
				
				
				Minute m = new Minute(prop.getMinuteOfHour(),prop.getHourOfDay(),prop.getDayOfMonth(),prop.getMonthOfYear(),prop.getYear());
				int diff = saxStr.getContent().charAt(i) - 'a';
				series.addOrUpdate(m,diff * letterWeight + min);
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
