package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.NewSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxArffCandidateRow;
import weka.core.Instances;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption,isDominant);
	}

	List<SaxArffCandidateRow> nestedList;

	@Override
	protected void processData() {
		nestedList = new ArrayList<SaxArffCandidateRow>();

		for (Patient patient : patients) {
			LinkedList<HashMap<String, AtomicInteger>> listHashMap = new LinkedList<HashMap<String, AtomicInteger>>();

			List<String> dividedSax = DataDivider.DivideStringRegularly(patient
					.getSaxString().getContent(), divisionPartsAmount);

			for (String string : dividedSax) {
				HashMap<String, AtomicInteger> ngramCountMap = PeriodicNgramCounter
						.slashStringAndCountNgrams(string, windowLen);
				listHashMap.add(ngramCountMap);
			}

			SaxArffCandidateRow row = new SaxArffCandidateRow(listHashMap,
					patient.getDestinationClass());
			nestedList.add(row);
		}
	}

	@Override
	protected void exportArff() throws Exception {
		Instances instances;
		if(isDominant){
			instances = NewSaxArffBuilder.buildDominantInstancesFromStats(nestedList);
		}
		else{
			instances = NewSaxArffBuilder.buildInstancesFromStats(nestedList);			
		}
		NewSaxArffBuilder.saveArff(instances, arffCVpath);
	}

	@Override
	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(patients);
	}

}
