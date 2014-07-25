package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.DataExporters.RecordToArffTranslator;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;

public class PatientSaxWorkflow extends PatientWorkflowBase {

	public PatientSaxWorkflow() {
		super();
	}

	@Override
	protected void processData() {
		
		RecordToArffTranslator translator = new RecordToArffTranslator("PatientToSax");
		
		int attrLength = Integer.parseInt(Configuration.getProperty("saxAttributeLength"));
		Pair<List<Patient>, List<Patient>> res = dividePatientsToTrainAndTest(patients);
		List<UnifiedRecordType> trainPatients = CastPatients(res.getLeft());
		List<UnifiedRecordType> testPatients = CastPatients(res.getRight());
		try {
			translator.saveUnifiedRecordsToArffData(trainPatients, tempTrainPath, attrLength, true);
			translator.saveUnifiedRecordsToArffData(testPatients, tempTestPath, attrLength, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void runExperiment() {		 
		try {
			double res = Experiment.runExperiment(classifier, tempTrainPath, tempTestPath);
			System.out.println("The result for " + this.getClass().getSimpleName() +" is: " + res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void reportResult() {
		// TODO Auto-generated method stub
		
	}
	
	private List<UnifiedRecordType> CastPatients(List<Patient> patients){
		List<UnifiedRecordType> res = new ArrayList<UnifiedRecordType>();
		for (Patient patient : patients) {
			UnifiedRecordType obj = new UnifiedRecordType(patient.getDestinationClass(), patient.getValues());
			obj.setSaxString(patient.getSaxString().getContent());
			res.add(obj);
		}
		
		return res;
	}
	
	private Pair<List<Patient>, List<Patient>> dividePatientsToTrainAndTest(List<Patient> input){
		List<Patient> train = new ArrayList<Patient>();
		List<Patient> test = new ArrayList<Patient>();
		
		List<Patient> healthList = input.stream().filter(x->x.getDestinationClass()==0).collect(Collectors.toList());
		List<Patient> sickList = input.stream().filter(x->x.getDestinationClass()==1).collect(Collectors.toList());
		
		
		int limit = (int) (healthList.size()*trainTestRatio);
		for(int i =0;i<healthList.size();i++){
			Patient obj = healthList.get(i);
			if(i<limit){
				train.add(obj);
			}
			else{
				test.add(obj);
			}
		}
		
		limit = (int) (sickList.size()*trainTestRatio);
		for(int i =0;i<sickList.size();i++){
			Patient obj = sickList.get(i);
			if(i<limit){
				train.add(obj);
			}
			else{
				test.add(obj);
			}
		}
		return new MutablePair<List<Patient>, List<Patient>>(train,test);
	}

	@Override
	protected void setTempPaths() {
		tempTrainPath = "output/saxPatientsTrainTemp.arff";
		tempTestPath = "output/saxPatientsTestTemp.arff";
	}

}
