package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.DominantArffBuilder;
import pl.poznan.put.TimeSeries.DataExporters.SaxArffExporterBase;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.DataDivider;
import weka.core.Instances;

public class PatientDominantWorkflow extends PatientWorkflowBase {

	public PatientDominantWorkflow(DivisionOptions divisionOption) {
		super(divisionOption);
		// TODO Auto-generated constructor stub
	}

	private SaxArffExporterBase exporter;
	List<CalculatedRecord> calculatedRecords;

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

}
