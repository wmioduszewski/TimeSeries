package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.DataExporters.SaxArffExporter;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.NgramProcessor;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedRecord;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.PatientUtils;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow() {
		super();
	}

	@Override
	protected void processData() {
		SaxArffExporter translator = new SaxArffExporter("PatientToSax");
		int attrLength = Integer.parseInt(Configuration
				.getProperty("ngramSize"));
		
		
		int windowLen = Integer.parseInt(Configuration.getProperty(""));
		
		
		List<LinkedList<HashMap<String, AtomicInteger>>> nestedList = new ArrayList<LinkedList<HashMap<String,AtomicInteger>>>(); 
		
		for (Patient patient : patients) {
			LinkedList<HashMap<String, AtomicInteger>> listHashMap = new LinkedList<HashMap<String,AtomicInteger>>();
			try {
				List<List<Characteristic>> listlist = DataDivider.dividePatientDataPeriodically(patient);
				
				List<String> dividedSax = Arrays.asList("abra","cadabra","czaryimagia");
				
				for (String string : dividedSax) {
					
					HashMap<String, AtomicInteger> ngramCountMap = NgramProcessor.slashString(patient.getSaxString().getContent(), windowLen);
					listHashMap.add(ngramCountMap);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nestedList.add(listHashMap);			
		}
		
		Pair<List<Patient>, List<Patient>> res = PatientUtils.dividePatientsToTrainAndTest(patients);
		List<UnifiedRecord> trainPatients = PatientUtils.castPatients(res
				.getLeft());
		List<UnifiedRecord> testPatients = PatientUtils.castPatients(res
				.getRight());
		
		try {
			translator.saveUnifiedRecordsToArffData(trainPatients,
					tempTrainPath, attrLength, true);
			translator.saveUnifiedRecordsToArffData(testPatients, tempTestPath,
					attrLength, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
