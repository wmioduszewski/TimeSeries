package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.DataExporters.NewSaxArffBuilder;
import pl.poznan.put.TimeSeries.DataExporters.SaxArffExporter;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.NgramProcessor;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxArffCandidateRow;
import pl.poznan.put.TimeSeries.Model.SaxString;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.PatientUtils;
import weka.core.Instances;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow() {
		super();
	}
	
	@Override
	protected void processData() {
		SaxArffExporter translator = new SaxArffExporter("PatientToSax");
		int attrLength = Integer.parseInt(Configuration
				.getProperty("ngramSize"));

		int windowLen = Integer
				.parseInt(Configuration.getProperty("ngramSize"));

		List<SaxArffCandidateRow> nestedList = new ArrayList<SaxArffCandidateRow>();

//		patients.clear();
//		Patient p = new Patient(1);
//		p.setSaxString(new SaxString("abbaaaabbbbbbababa", 0, 0));
//		p.setSick(true);
//		patients.add(p);
//		p = new Patient(2);
//		p.setSaxString(new SaxString("bbbbbbaaaaaabababa", 0, 0));
//		p.setSick(false);
//		patients.add(p);

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SaxArffCandidateRow row = new SaxArffCandidateRow(listHashMap, patient.getDestinationClass());
			nestedList.add(row);
		}

		Instances insts = NewSaxArffBuilder.buildInstancesFromStats(nestedList);
		NewSaxArffBuilder.saveArff(insts,"testtest.arff");

	}

	@Override
	protected void runExperiment() {
		try {
			double res = Experiment.runExperiment(classifier, tempTrainPath,
					tempTestPath);
			System.out.println("The result for "
					+ this.getClass().getSimpleName() + " is: " + res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void reportResult() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void setTempPaths() {
		tempTrainPath = "output/saxPatientsTrainTemp.arff";
		tempTestPath = "output/saxPatientsTestTemp.arff";
	}

}
