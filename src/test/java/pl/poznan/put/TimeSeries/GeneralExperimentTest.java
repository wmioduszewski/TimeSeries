package pl.poznan.put.TimeSeries;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class GeneralExperimentTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
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
	}
	
	

}
