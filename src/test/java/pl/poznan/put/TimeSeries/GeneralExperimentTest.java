package pl.poznan.put.TimeSeries;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class GeneralExperimentTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		
		
		String dataPath = CommonConfig.getInstance().getSingleDataPath();
		String folderPath = CommonConfig.getInstance().getDataFolderPath();
		CommonConfig.getInstance().setDataFolderPath("testData/");
		CommonConfig.getInstance().setSingleDataPath("SAMPLEDATASET");
		
		for (Experiments chosenExperiment : Experiments.values()) {
			WorkflowBase workflow = chosenExperiment.getWorkflow();
			Classifier classifier = chosenExperiment.getClassifier();
			ExperimentBase experiment = new CrossValidationExperiment(classifier);

			try {
				workflow.runExperiment(experiment);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
		
		CommonConfig.getInstance().setSingleDataPath(dataPath);
		CommonConfig.getInstance().setDataFolderPath(folderPath);
	}
}
