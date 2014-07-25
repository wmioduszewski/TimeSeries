package pl.poznan.put.TimeSeries.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Util.Configuration;

public abstract class PatientWorkflowBase extends WorkflowBase{

	protected List<Patient> patients;
	protected float trainTestRatio = Float.parseFloat(Configuration.getProperty("trainToTestRatio"));
	
	public PatientWorkflowBase() {
		super();
	}
	
	protected Pair<List<UnifiedArffRow>, List<UnifiedArffRow>> divideRowsToTrainAndTest(List<UnifiedArffRow> input){
		List<UnifiedArffRow> train = new ArrayList<UnifiedArffRow>();
		List<UnifiedArffRow> test = new ArrayList<UnifiedArffRow>();
		
		List<UnifiedArffRow> healthList = input.stream().filter(x->x.getDestinationClass()==0).collect(Collectors.toList());
		List<UnifiedArffRow> sickList = input.stream().filter(x->x.getDestinationClass()==1).collect(Collectors.toList());
		
		
		int limit = (int) (healthList.size()*trainTestRatio);
		for(int i =0;i<healthList.size();i++){
			UnifiedArffRow obj = healthList.get(i);
			if(i<limit){
				train.add(obj);
			}
			else{
				test.add(obj);
			}
		}
		
		limit = (int) (sickList.size()*trainTestRatio);
		for(int i =0;i<sickList.size();i++){
			UnifiedArffRow obj = sickList.get(i);
			if(i<limit){
				train.add(obj);
			}
			else{
				test.add(obj);
			}
		}
		return new MutablePair<List<UnifiedArffRow>, List<UnifiedArffRow>>(train,test);
	}
	
	protected void ReportInputStatistics(List<UnifiedArffRow> train, List<UnifiedArffRow> test){
		long trainHealtCount = train.stream().filter(x->x.getDestinationClass()==0).count();
		long trainSickCount = train.stream().filter(x->x.getDestinationClass()==1).count();
		
		long testHealtCount = test.stream().filter(x->x.getDestinationClass()==0).count();
		long testSickCount = test.stream().filter(x->x.getDestinationClass()==1).count();
		
		System.out.println(String.format("Train set has %d patients health and %d patients sick.",trainHealtCount,trainSickCount));
		System.out.println(String.format("Test set has %d patients health and %d patients sick.",testHealtCount,testSickCount));
	}

	@Override
	protected void importData() {
		String csvDataPath = Configuration.getProperty("csvDataSet");
		String pureDataPath = Configuration.getProperty("pureDataSet");
		List<Patient> csvPatients = null;
		List<Patient> purePatients = null;
		DataImporterBase importer = new DataImporterCsv(csvDataPath);
		try {
			csvPatients = importer.ImportData();
			importer = new PureDataImporter(pureDataPath);
			purePatients = importer.ImportData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		patients.addAll(csvPatients);
		patients.addAll(purePatients);
	}
}
