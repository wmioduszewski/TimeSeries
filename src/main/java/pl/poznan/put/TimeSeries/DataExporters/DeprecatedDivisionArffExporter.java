package pl.poznan.put.TimeSeries.DataExporters;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;

public class DeprecatedDivisionArffExporter extends ArffExporterBase {
	private List<UnifiedRecord> records;

	public DeprecatedDivisionArffExporter(String relationTitle) {
		super(relationTitle);
	}

	public void saveUnifiedRecordsToArffData(List<UnifiedRecord> records,
			String destinationPath) throws FileNotFoundException {
		this.records = records;
		performExport(destinationPath);
	}

	@Override
	protected void assignAttributes() {
		String attrType = "NUMERIC";
		for (int i = 0; i < Limits.AgeLimits.size(); i++) {
			AgeLimit currLimit = Limits.AgeLimits.get(i);
			attributes.put(String.format("age%d-%dscope,",
					currLimit.getLowerBound(), currLimit.getUpperBound()),
					attrType);
			attributes.put(
					String.format("age%d-%dintercept,",
							currLimit.getLowerBound(),
							currLimit.getUpperBound()), attrType);
		}

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
		attributes.put("class", "{" + classes + "}");
	}

	@Override
	protected void insertData() {
		arffFileContent.append("@DATA\n");
		for (UnifiedRecord record : records) {
			String diagnosis = String.valueOf(record.getDestinationClass());
			List<Float> vals = record.getValues();
			for (int i = 0; i < vals.size(); i++) {
				arffFileContent.append(vals.get(i) + ",");
			}
			arffFileContent.append(diagnosis + "\n");
		}
	}
}
