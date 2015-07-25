package pl.poznan.put.TimeSeries.DataExporters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;

public abstract class SaxArffExporterBase extends ArffExporterBase {

	protected List<List<String>> distincts;
	protected List<CalculatedRecord> input;

	public SaxArffExporterBase(List<CalculatedRecord> input) {
		this.input = input;
		getPeriodicDistincts();
		setDestinationClasses(input);
	}
	
	protected void getPeriodicDistincts() {
		distincts = new ArrayList<List<String>>();
		for (int i = 0; i < regularPartsForDivision; i++) {
			List<String> currentPeriodDistincts = new ArrayList<String>();
			for (CalculatedRecord calculatedRecord : input) {
				Set<String> keys = calculatedRecord.getPeriodicNgrams().get(i)
						.keySet();
				for (String key : keys) {
					if (!currentPeriodDistincts.contains(key)) {
						currentPeriodDistincts.add(key);
					}
				}
			}
			distincts.add(currentPeriodDistincts);
		}
	}
}
