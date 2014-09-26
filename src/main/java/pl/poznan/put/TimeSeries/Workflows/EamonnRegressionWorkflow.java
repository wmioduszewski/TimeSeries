package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.RegressionArffExporter;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public class EamonnRegressionWorkflow extends EamonnWorkflowBase {

	public EamonnRegressionWorkflow(DivisionOptions divisionOption) {
		super(divisionOption);
	}

	List<UnifiedArffRow> rows;

	@Override
	protected void processData() {
		rows = new ArrayList<UnifiedArffRow>();
		for (EamonnRecord record : records) {
			try {
				List<List<Float>> nestedCharacteristicList = divideData(record);
				List<RegressionResult> regResults = new ArrayList<RegressionResult>();
				for (List<Float> list : nestedCharacteristicList) {
					RegressionResult result = RegressionCalculator
							.ComputeRegression(list);
					regResults.add(result);
				}
				UnifiedArffRow arffRow = new UnifiedArffRow(regResults,
						record.getDestinationClass());
				rows.add(arffRow);
			} catch (Exception e) {
				System.out.println("cuś dziwnego");
				e.printStackTrace();
			}
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
		WorkflowBase.reportInputStatistics(records);
	}

}
