package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.RegressionArffExporter;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Util.Convert;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public class PatientRegressionWorkflow extends PatientWorkflowBase {

	public PatientRegressionWorkflow(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption,isDominant);
	}

	List<UnifiedArffRow> rows;

	@Override
	protected void processData() throws Exception {

		rows = new ArrayList<UnifiedArffRow>();

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

			UnifiedArffRow arffRow = new UnifiedArffRow(regResults,
					patient.getDestinationClass());
			rows.add(arffRow);
		}
	}

	@Override
	protected void exportArff() throws IOException {
		RegressionArffExporter exporter = new RegressionArffExporter(
				"UnifiedData");
		exporter.saveUnifiedRecordsToArffData(rows, arffCVpath);
	}

	@Override
	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(patients);
	}

}
