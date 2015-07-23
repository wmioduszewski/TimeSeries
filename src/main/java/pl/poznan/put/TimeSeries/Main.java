package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class Main {

	public static void main(String[] args) throws Exception {

		long startTime = System.currentTimeMillis();

		launchExperiment(Experiments.DOMINANT);

		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("Execution took " + diff / 1000 + "s."
				+ diff % 1000 + "ms");
	}

	private static void launchExperiment(Experiments chosenExperiment)
			throws Exception {
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		Classifier classifier = chosenExperiment.getClassifier();
		ExperimentBase experiment = new CrossValidationExperiment(classifier);

		workflow.runExperiment(experiment);
//		String path = workflow.saveArff();
//		workflow.executeArff(experiment, "output/arffOutput/Dominant3p3gramRegular.arff");
	}
}
