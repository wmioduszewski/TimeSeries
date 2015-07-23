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

public class PatientDominantWorkflow extends PatientWorkflowBase {

	private SaxArffExporterBase exporter;

	List<CalculatedRecord> calculatedRecords;

	public PatientDominantWorkflow(DivisionOptions divisionOption) {
		super(divisionOption);
	}

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
	protected void processData() {
		calculatedRecords = new ArrayList<CalculatedRecord>();

		for (IRecord records : records) {
			ArrayList<HashMap<String, Integer>> listHashMap = new ArrayList<HashMap<String, Integer>>();

			List<String> dividedSax = DataDivider.divideStringRegularly(
					records.getSaxString(), divisionPartsAmount);

			for (String string : dividedSax) {
				HashMap<String, Integer> ngramCountMap = PeriodicNgramCounter
						.slashStringAndCountNgrams(string, windowLen);
				listHashMap.add(ngramCountMap);
			}

			CalculatedRecord calcRecord = new CalculatedRecord(listHashMap,
					records.getDestinationClass());
			calculatedRecords.add(calcRecord);
		}
	}

}
