package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.DataExporters.UnifiedArffExporter;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.DataProcessors.PatientDataDivider;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.rules.JRip;

public class PatientRegressionWorkflow extends PatientWorkflowBase{

	public PatientRegressionWorkflow() {
		super();
		patients = new ArrayList<Patient>();
	}

	@Override
	protected void processData() {
		PatientDataDivider patientDivider = new PatientDataDivider();
		List<UnifiedArffRow> trainSet = new ArrayList<UnifiedArffRow>();

		for (Patient patient : patients) {

			try {
				UnifiedArffRow arffRow = patientDivider
						.ComputeRegression(patient);
				trainSet.add(arffRow);
			} catch (Exception e) {
				System.out.println("patient missed");
				e.printStackTrace();
			}
		}
		
		List<UnifiedArffRow> testSet = new ArrayList<UnifiedArffRow>();
		
		int limit = trainSet.size()/5;
		for(int i =0;i<limit;i++){
			UnifiedArffRow obj = trainSet.get(i);
			testSet.add(obj);
			trainSet.remove(obj);
		}
		long trainHealtCount = trainSet.stream().filter(x->x.getDestinationClass()==0).count();
		long trainSickCount = trainSet.stream().filter(x->x.getDestinationClass()==1).count();
		
		long testHealtCount = testSet.stream().filter(x->x.getDestinationClass()==0).count();
		long testSickCount = testSet.stream().filter(x->x.getDestinationClass()==1).count();
		
		System.out.println(String.format("Train set has %d patients health and %d patients sick.",trainHealtCount,trainSickCount));
		System.out.println(String.format("Test set has %d patients health and %d patients sick.",testHealtCount,testSickCount));
		
		UnifiedArffExporter exporter = new UnifiedArffExporter("UnifiedData");
		try {
			exporter.saveUnifiedRecordsToArffData(trainSet, "output/tempArffTrain.arff");
			exporter.saveUnifiedRecordsToArffData(testSet, "output/tempArffTest.arff");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void runExperiment() {
		Classifier classifier = new LinearRegression();
		try {
			double res = Experiment.runExperiment(classifier, "output/tempArffTrain.arff", "output/tempArffTest.arff");
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

}
