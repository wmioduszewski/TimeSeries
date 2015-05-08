package pl.poznan.put.TimeSeries.DataExporters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public abstract class SaxArffExporterBase extends ArffExporterBase {

	protected List<List<String>> distincts;
	protected List<CalculatedRecord> input;

	public SaxArffExporterBase(List<CalculatedRecord> input) {
		this.input = input;
		getPeriodicDistincts();
		destClasses = input.stream().map(x -> x.getDestClass()).distinct()
				.collect(Collectors.toList());
		setAttributes();
	}

	// TODO: probably should not be here, in this class
	private void cutAttributes() {
		float attributesToCutRatio = CommonConfig.getInstance()
				.getAttributesToCutRatio();
		int attributesToCut = (int) ((instances.numAttributes() - 1) * attributesToCutRatio);
		Random rand = new Random();
		for (int i = 0; i < attributesToCut; i++) {
			int index = rand.nextInt(instances.numAttributes() - 1);
			instances.deleteAttributeAt(index);
		}
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
