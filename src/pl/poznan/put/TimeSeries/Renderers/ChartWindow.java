package pl.poznan.put.TimeSeries.Renderers;

import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

public class ChartWindow extends ApplicationFrame {

	//
	
	public ChartWindow(String title, Dimension dimension, JFreeChart chart) {
		super(title);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(dimension);
        setContentPane(chartPanel);
	}

}
