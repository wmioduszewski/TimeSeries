package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.DominantArffBuilder;
import pl.poznan.put.TimeSeries.DataExporters.SaxArffExporterBase;
import pl.poznan.put.TimeSeries.DataExporters.CountedSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.DataDivider;
import weka.core.Instances;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption, isDominant);
	}
	private SaxArffExporterBase exporter;
	List<CalculatedRecord> calculatedRecords;

	@Override
	protected void processData() {
		calculatedRecords = new ArrayList<CalculatedRecord>();

		for (Patient patient : patients) {
			ArrayList<HashMap<String, Integer>> listHashMap = new ArrayList<HashMap<String, Integer>>();

			List<String> dividedSax = DataDivider.divideStringRegularly(
					patient.getSaxString(), divisionPartsAmount);

			for (String string : dividedSax) {
				HashMap<String, Integer> ngramCountMap = PeriodicNgramCounter
						.slashStringAndCountNgrams(string, windowLen);
				listHashMap.add(ngramCountMap);
			}

			CalculatedRecord calcRecord = new CalculatedRecord(listHashMap,
					patient.getDestinationClass());
			calculatedRecords.add(calcRecord);
		}
	}

	@Override
	protected void exportArff() throws Exception {
		exporter.saveArff(arffPath);
	}

	@Override
	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(patients);
	}

	@Override
	protected Instances buildInstances() {
		if(isDominant)
			exporter = new DominantArffBuilder(calculatedRecords);
		else
			exporter = new CountedSaxArffBuilder(calculatedRecords);
		return exporter.buildInstances();
	}

}
