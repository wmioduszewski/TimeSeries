package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.RegressionArffBuilder;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.RegressionArffRow;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Util.DataDivider;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;
import weka.core.Instances;

public class RegressionWorkflow extends WorkflowBase {

	private List<RegressionArffRow> rows;

	public RegressionWorkflow(DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
	}

	@Override
	protected Instances buildInstances() {
		exporter = new RegressionArffBuilder(rows);
		return exporter.buildInstances();
	}

	@Override
	protected void processData() throws Exception {
		rows = new ArrayList<RegressionArffRow>();
		for (IRecord record : records) {
			List<List<Float>> nestedCharacteristicList = DataDivider
					.divideRecord(record, divisionOption, divisionPartsAmount);
			List<RegressionResult> regResults = new ArrayList<RegressionResult>();

			for (List<Float> list : nestedCharacteristicList) {
				RegressionResult result = RegressionCalculator
						.ComputeRegression(list);
				regResults.add(result);
			}

			RegressionArffRow arffRow = new RegressionArffRow(regResults,
					record.getDestinationClass());
			rows.add(arffRow);
		}
	}

}
