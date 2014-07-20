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

public class PatientRegressionWorkflow extends WorkflowBase{

	private List<Patient> patients;
	
	public PatientRegressionWorkflow() {
		super();
		patients = new ArrayList<Patient>();
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
		//patients.addAll(csvPatients);
		patients.addAll(purePatients);
	}

	@Override
	protected void processData() {
		PatientDataDivider patientDivider = new PatientDataDivider();
		List<UnifiedArffRow> rows = new ArrayList<UnifiedArffRow>();

		for (Patient patient : patients) {

			try {
				UnifiedArffRow arffRow = patientDivider
						.ComputeRegression(patient);
				rows.add(arffRow);
			} catch (Exception e) {
				System.out.println("patient missed");
				e.printStackTrace();
			}
		}
		
		//List<UnifiedArffRow> trainSet = rows.stream().limit(rows.size()/2).collect(Collectors.toList());
		//List<UnifiedArffRow> testSet = rows.stream().;
		UnifiedArffExporter exporter = new UnifiedArffExporter("UnifiedData");
		try {
			exporter.saveUnifiedRecordsToArffData(rows, "output/tempArff.arff");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void runExperiment() {
		Classifier classifier = new NaiveBayes();
		Experiment.runExperiment(classifier, trainSetPath, testSetPath)
		
	}

	@Override
	protected void reportResult() {
		// TODO Auto-generated method stub
		
	}

}
