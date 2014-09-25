package pl.poznan.put.Deprecated;

import java.awt.Dimension;

import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import pl.poznan.put.TimeSeries.Renderers.ChartBase;
import pl.poznan.put.TimeSeries.Renderers.ChartWindow;
import pl.poznan.put.TimeSeries.Renderers.CleanChart;

public class ChartGenerator {

	private void displayWindowChart(JFreeChart jchart) {
		ChartWindow window = new ChartWindow("Time Series", new Dimension(1600,
				1000), jchart);
		window.pack();
		RefineryUtilities.centerFrameOnScreen(window);
		window.setVisible(true);
	}

	private static void graphics() {
		ChartBase currentChart;
		currentChart = new CleanChart();
		// currentChart = new SmoothChart(omega);
		// currentChart = new SmoothChart(smoothingSize);
		// currentChart = new SaxChart(-20.6f, 48.9f);

		// exporter.SaveChartsToFile(currentChart, chartsExportFolder);
	}
}
