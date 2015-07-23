package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.DominantArffBuilder;
import pl.poznan.put.TimeSeries.DataExporters.SaxArffExporterBase;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Util.DataDivider;
import weka.core.Instances;

public class EamonnDominantWorkflow extends EamonnWorkflowBase{

	public EamonnDominantWorkflow(DivisionOptions divisionOption) {
		super(divisionOption);
	}

	private SaxArffExporterBase exporter;
	private List<CalculatedRecord> calculatedRecords;
	
	@Override
	protected Instances buildInstances() {
		exporter = new DominantArffBuilder(calculatedRecords);
		return exporter.buildInstances();
	}

	@Override
	protected void exportArff() throws Exception {
		exporter.saveArff(arffPath);
		
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

}
