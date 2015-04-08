package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.NewSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.DataDivider;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption, isDominant);
	}

	List<CalculatedRecord> nestedList;

	@Override
	protected void processData() {
		nestedList = new ArrayList<CalculatedRecord>();

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
			nestedList.add(calcRecord);
		}
	}

	@Override
	protected void exportArff() throws Exception {
		NewSaxArffBuilder arffBuilder = new NewSaxArffBuilder(isDominant);
		arffBuilder.buildInstancesFromStats(nestedList);
		arffBuilder.saveArff(arffCVpath);
	}

	@Override
	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(patients);
	}

}
