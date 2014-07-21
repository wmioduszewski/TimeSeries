package pl.poznan.put.TimeSeries.DataExporters;

import java.io.FileNotFoundException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;

public class UnifiedArffExporter extends ArffExporterBase {

	private List<UnifiedArffRow> records;

	public UnifiedArffExporter(String relationTitle) {
		super(relationTitle);
	}

	public void saveUnifiedRecordsToArffData(List<UnifiedArffRow> records,
			String destinationPath) throws FileNotFoundException {
		this.records = records;
		performExport(destinationPath);
	}

	@Override
	protected void assignAttributes() {
		String numberType = "NUMERIC";
		int regressionCount = records.stream().findFirst().get()
				.getRegressionResults().size();
		for (int i = 0; i < regressionCount; i++) {
			attributes.put(String.format("slope%d", i + 1), numberType);
			attributes.put(String.format("intercept%d", i + 1), numberType);
		}
//		attributes.put("age", numberType);
		attributes.put("destClass", numberType);
	}

	@Override
	protected void insertData() {
		arffFileContent.append("@DATA\n");

		for (UnifiedArffRow record : records) {
			String destClass = String.valueOf(record.getDestinationClass());
			for (RegressionResult result : record.getRegressionResults()) {
				arffFileContent.append(result.getSlope() + ","
						+ result.getIntercept() + ",");
			}
			arffFileContent.append(destClass + "\n");
		}
	}

}
