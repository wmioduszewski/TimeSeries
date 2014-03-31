package pl.poznan.put.TimeSeries.View;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.*;

import pl.poznan.put.TimeSeries.DataReaders.CsvReader;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Renderers.LineChart;

public class MainForm extends ApplicationFrame {

	public MainForm(String title) {
		super(title);

		try {
			List<Patient> res = CsvReader
					.ReadData("doc/dane/gTimeData.7.5.20130123a_sub.csv");
			//od indeksu = 10 => pacjenci chorzy 
			Patient[] patients = new Patient[]{
					res.get(0),
					res.get(1),
					res.get(2),
					res.get(3),
					res.get(4),
					};
			
			ChartPanel chartPanel = LineChart.PaintPatientsChart(patients);
			chartPanel.setPreferredSize(new Dimension(1800, 1000));
			setContentPane(chartPanel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {

		final MainForm demo = new MainForm("Line Chart Patient");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
