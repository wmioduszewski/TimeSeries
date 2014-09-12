package pl.poznan.put.TimeSeries.Workflows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Experiment;
import pl.poznan.put.TimeSeries.DataExporters.RegressionArffExporter;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.PatientDataDivider;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Util.Convert;
import pl.poznan.put.TimeSeries.Util.PatientUtils;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;

public class PatientRegressionWorkflow extends PatientWorkflowBase{

	public PatientRegressionWorkflow() {
		super();
	}

	@Override
	protected void processData() {
				
		PatientDataDivider patientDivider = new PatientDataDivider();
		List<UnifiedArffRow> rows = new ArrayList<UnifiedArffRow>();

		for (Patient patient : patients) {
			try {				
				List<List<Characteristic>> listlist = DataDivider.DivideCollectionRegularly(patient.getCharacteristics(), regularPartsForDivision);				
				List<RegressionResult> regResults = new ArrayList<RegressionResult>();
				for (List<Characteristic> list : listlist) {
					List<Float> floats = Convert.fromCharacteristicsToFloatList(list);
					RegressionResult result = RegressionCalculator.ComputeRegression(floats);
					regResults.add(result);
				}
				
				UnifiedArffRow arffRow = new UnifiedArffRow(regResults, patient.getDestinationClass());
				rows.add(arffRow);
			} catch (Exception e) {
				System.out.println("patient missed");
				e.printStackTrace();
			}
		}
//		Pair<List<UnifiedArffRow>, List<UnifiedArffRow>> pair = PatientUtils.divideRowsToTrainAndTest(rows);
//		List<UnifiedArffRow> trainSet = pair.getLeft();
//		List<UnifiedArffRow> testSet = pair.getRight();
		
		//ReportInputStatistics(trainSet, testSet);
		
		RegressionArffExporter exporter = new RegressionArffExporter("UnifiedData");
		try {
			//exporter.saveUnifiedRecordsToArffData(trainSet, tempTrainPath);
			//exporter.saveUnifiedRecordsToArffData(testSet, tempTestPath);
			exporter.saveUnifiedRecordsToArffData(rows, tempTestPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void runExperiment() {		
		try {
			//double res = Experiment.runExperiment(classifier, tempTrainPath, tempTestPath);
			int folds = 10;
			double partOfDataSet = 1;
			long seed = 1000;
			CrossValidationExperiment.runCVExperiment(classifier, tempTestPath, folds, partOfDataSet, seed);
			//System.out.println("The result for " + this.getClass().getSimpleName() +" is: " + res);
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
		tempTrainPath = "output/tempRegressionArffTrain.arff";
		tempTestPath = "output/tempRegressionArffTestCV.arff";
		
	}

}
