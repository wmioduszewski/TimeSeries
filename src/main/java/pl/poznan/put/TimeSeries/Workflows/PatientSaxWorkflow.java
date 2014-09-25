package pl.poznan.put.TimeSeries.Workflows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pl.poznan.put.TimeSeries.DataExporters.NewSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.NgramProcessor;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxArffCandidateRow;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.core.Instances;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow() {
		super();
	}

	@Override
	protected void processData() {
		int windowLen = Integer
				.parseInt(Configuration.getProperty("ngramSize"));

		List<SaxArffCandidateRow> nestedList = new ArrayList<SaxArffCandidateRow>();

		for (Patient patient : patients) {
			LinkedList<HashMap<String, AtomicInteger>> listHashMap = new LinkedList<HashMap<String, AtomicInteger>>();
			try {

				List<String> dividedSax = DataDivider.DivideStringRegularly(
						patient.getSaxString().getContent(),
						regularPartsForDivision);

				for (String string : dividedSax) {
					HashMap<String, AtomicInteger> ngramCountMap = NgramProcessor
							.slashString(string, windowLen);
					listHashMap.add(ngramCountMap);
				}

			} catch (Exception e) {
				System.out.println("Processing Patient data failed.");
				e.printStackTrace();
			}
			SaxArffCandidateRow row = new SaxArffCandidateRow(listHashMap,
					patient.getDestinationClass());
			nestedList.add(row);
		}

		Instances insts = NewSaxArffBuilder.buildInstancesFromStats(nestedList);
		NewSaxArffBuilder.saveArff(insts, tempCVpath);
	}

	@Override
	protected void reportResult() {
	}

	@Override
	protected void setTempPaths() {
		// tempTrainPath = "output/saxPatientsTrainTemp.arff";
		// tempTestPath = "output/saxPatientsTestTemp.arff";
		tempCVpath = "output/saxPatients4p2g.arff";
	}

}
