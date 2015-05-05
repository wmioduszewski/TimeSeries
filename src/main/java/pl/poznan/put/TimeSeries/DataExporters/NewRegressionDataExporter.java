package pl.poznan.put.TimeSeries.DataExporters;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import weka.core.Instances;

public class NewRegressionDataExporter extends ArffExporterBase {

	public NewRegressionDataExporter(List<CalculatedRecord> input) {
		super(input);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setAttributes() {
		// TODO Auto-generated method stub
	}

	@Override
	public Instances buildInstances() {
		// TODO Auto-generated method stub
		return null;
	}

}
