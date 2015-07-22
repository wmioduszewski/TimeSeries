package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.RegressionArffBuilder;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.RegressionArffRow;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Util.Convert;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;
import weka.core.Instances;

public class PatientRegressionWorkflow extends PatientWorkflowBase {

	public PatientRegressionWorkflow(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption,isDominant);
	}

	private List<RegressionArffRow> rows;
	private RegressionArffBuilder exporter; 
	
	@Override
	protected void processData() throws Exception {

		rows = new ArrayList<RegressionArffRow>();

		for (Patient patient : patients) {
			List<List<Characteristic>> nestedCharacteristicList = divideData(patient);
			List<RegressionResult> regResults = new ArrayList<RegressionResult>();
			for (List<Characteristic> list : nestedCharacteristicList) {
				List<Float> floats = Convert
						.fromCharacteristicsToFloatList(list);
				RegressionResult result = RegressionCalculator
						.ComputeRegression(floats);
				regResults.add(result);
			}

			RegressionArffRow arffRow = new RegressionArffRow(regResults,
					patient.getDestinationClass());
			rows.add(arffRow);
		}
	}

	@Override
	protected void exportArff() throws IOException {
		exporter.saveArff(arffPath);
	}

	@Override
	protected Instances buildInstances() {
		exporter= new RegressionArffBuilder(rows);
		return exporter.buildInstances();
	}

}
