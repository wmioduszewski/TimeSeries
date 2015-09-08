package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.DominantArffBuilder;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Util.Config;
import pl.poznan.put.TimeSeries.Util.DataDivider;
import weka.core.Instances;

public class DominantWorkflow extends WorkflowBase {

	private List<CalculatedRecord> calculatedRecords;

	public DominantWorkflow(DivisionOptions divisionOption, boolean glaucoma) {
		super(divisionOption, glaucoma);
	}

	@Override
	protected Instances buildInstances() {
		exporter = new DominantArffBuilder(calculatedRecords);
		return exporter.buildInstances();
	}

	@Override
	protected void processData() throws Exception {
		calculatedRecords = new ArrayList<CalculatedRecord>();

		for (IRecord record : records) {
			ArrayList<HashMap<String, Integer>> periodicallyCountedNgrams = new ArrayList<HashMap<String, Integer>>();

			List<String> dividedSax = DataDivider.divideStringRegularly(
					record.getSaxString(), divisionPartsAmount);

			for (String elem : dividedSax) {
				HashMap<String, Integer> ngramCountMap = PeriodicNgramCounter
						.slashStringAndCountNgrams(elem, windowLen);
				periodicallyCountedNgrams.add(ngramCountMap);
			}

			CalculatedRecord calcRecord = new CalculatedRecord(
					periodicallyCountedNgrams, record.getDestinationClass());
			calculatedRecords.add(calcRecord);
		}

	}

	@Override
	protected void setConcerningParams() {
		concerningParameters.add(Pair.of("parts",divisionPartsAmount));
		concerningParameters.add(Pair.of("ngram", windowLen));
		concerningParameters.add(Pair.of("alpha", Config.getInstance().getSaxAlphabeatSize()));
	}

}
