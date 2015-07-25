package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class Main {

	public static void main(String[] args) throws Exception {

		long startTime = System.currentTimeMillis();

		launchExperiment(Experiments.KNN, Datasets.PATIENTS);
//		for (Datasets dataset : Datasets.values()) {
//			launchExperiment(Experiments.REGRESSION, dataset);
//			launchExperiment(Experiments.KNN, Datasets.PATIENTS);
//		}

		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("Execution took " + diff / 1000 + "s." + diff % 1000
				+ "ms");
	}

	private static void launchExperiment(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		
		//TODO: Launcher class with suitable logging
		
		System.out.println("\n-------Experiment " + chosenExperiment.name() +" for " + dataset.name());
		dataset.setAsCurrent();
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		Classifier classifier = chosenExperiment.getClassifier();
		ExperimentBase experiment = new CrossValidationExperiment(classifier);

		workflow.runExperiment(experiment);
		// String path = workflow.saveArff();
		// workflow.executeArff(experiment,
		// "output/arffOutput/Dominant3p3gramRegular.arff");
	}
}
