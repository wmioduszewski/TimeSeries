package pl.poznan.put.TimeSeries.DataExporters;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;

public class RegressionArffExporter extends ArffExporterBase {

	private List<UnifiedArffRow> records;

	public RegressionArffExporter(String relationTitle) {
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
		
		List<Object> classList = records.stream()
				.map(x -> x.getDestinationClass()).collect(Collectors.toList());
		List<Object> distinct = classList.stream().map(x -> x).distinct()
				.collect(Collectors.toList());
		
		String classes = "";
		for (Object problemClass : distinct) {
			if (classes.length() > 0)
				classes += ",";
			classes += problemClass;
		}

		attributes.put("destClass", "{" + classes + "}");
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
