package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
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
		
		for (Patient patient : patients) {
			
			try {
				List<List<Characteristic>> listlist = DataDivider.dividePatientDataPeriodically(patient);
				
				HashMap<String, AtomicInteger> ngramCountMap = NgramProcessor.slashString(patient.getSaxString().getContent(), windowLen);	
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		Pair<List<Patient>, List<Patient>> res = PatientUtils.dividePatientsToTrainAndTest(patients);
		List<UnifiedRecordType> trainPatients = PatientUtils.castPatients(res
				.getLeft());
		List<UnifiedRecordType> testPatients = PatientUtils.castPatients(res
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
