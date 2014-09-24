package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.DataExporters.RegressionArffExporter;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.PatientDataDivider;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Util.Convert;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public class EamonnRegressionWorkflow extends EamonnWorkflowBase{

	@Override
	protected void processData() {
		
		List<UnifiedArffRow> rows = new ArrayList<UnifiedArffRow>();

		for (UnifiedRecord record : records)  {
			try {				
				List<List<Float>> listlist = DataDivider.DivideCollectionRegularly(record.getValues(), regularPartsForDivision);
				
				List<RegressionResult> regResults = new ArrayList<RegressionResult>();
				for (List<Float> list : listlist) {					
					RegressionResult result = RegressionCalculator.ComputeRegression(list);
					regResults.add(result);
				}
				
				UnifiedArffRow arffRow = new UnifiedArffRow(regResults, record.getDestinationClass());
				rows.add(arffRow);
			} catch (Exception e) {
				System.out.println("patient missed");
				e.printStackTrace();
			}
		}
		
		RegressionArffExporter exporter = new RegressionArffExporter("UnifiedData");
		try {
			exporter.saveUnifiedRecordsToArffData(rows, tempTestPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void reportResult() {
		
	}

	@Override
	protected void setTempPaths() {
		tempTestPath = "output/EamonnTwoLeadEcgRegression1.arff";
		
	}

}
