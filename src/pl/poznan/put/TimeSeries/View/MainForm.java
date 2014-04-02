package pl.poznan.put.TimeSeries.View;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.*;

import pl.poznan.put.TimeSeries.DataReaders.CsvReader;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Renderers.LineChart;

public class MainForm {
	
	private static Patient[] ReadData(String path)
	{
		Patient[] patients = null;
		try {
			List<Patient> res = CsvReader
					.ReadData(path);
			//od indeksu = 10 => pacjenci chorzy 
			patients = new Patient[]{
					res.get(0),
					res.get(1),
					res.get(2),
					res.get(3),
					res.get(4),
					};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patients;
	}
	
	public static void main(String[] args) {
		Dimension prefferedSize = new Dimension(1800,1100);
		Patient[] patients = ReadData("doc/dane/gTimeData.7.5.20130123a_sub.csv");
		final LineChart lineChart = new LineChart("Line Chart Demo", patients,prefferedSize);
        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
	}
}
