package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.DataExporters.NewSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Model.SaxArffCandidateRow;
import pl.poznan.put.TimeSeries.Util.DataDivider;

public class EamonnSaxWorkflow extends EamonnWorkflowBase {

	public EamonnSaxWorkflow(DivisionOptions divisionOption, boolean isDominant) {
		super(divisionOption, isDominant);
	}

	List<SaxArffCandidateRow> arffCandidateRows;

	@Override
	protected void processData() {
		arffCandidateRows = new ArrayList<SaxArffCandidateRow>();

		for (EamonnRecord record : records) {
			LinkedList<HashMap<String, Integer>> periodicallyCountedNgrams = new LinkedList<HashMap<String, Integer>>();

			List<String> dividedSax = DataDivider.divideStringRegularly(
					record.getSaxString(), divisionPartsAmount);

			for (String elem : dividedSax) {
				HashMap<String, Integer> ngramCountMap = PeriodicNgramCounter
						.slashStringAndCountNgrams(elem, windowLen);
				periodicallyCountedNgrams.add(ngramCountMap);
			}

			SaxArffCandidateRow row = new SaxArffCandidateRow(
					periodicallyCountedNgrams, record.getDestinationClass());
			arffCandidateRows.add(row);
		}
	}

	@Override
	protected void exportArff() throws Exception {
		NewSaxArffBuilder arffBuilder = new NewSaxArffBuilder(isDominant);
		arffBuilder.buildInstancesFromStats(arffCandidateRows);
		arffBuilder.saveArff(arffCVpath);
	}

	@Override
	protected void reportStatistics() {
		WorkflowBase.reportInputStatistics(records);
	}
}
