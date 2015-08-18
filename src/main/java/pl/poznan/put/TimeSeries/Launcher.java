package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class Launcher {

	enum Variants {
		ARFFEXPORT, ARFFPROCESS, FULL;
	}

	public static String exportArff(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		CommonConfig.getInstance().setCurrentDataset(dataset);
		CommonConfig.getInstance().setCurrentExperiment(chosenExperiment);
		
		System.out.println("Arff export for " + dataset.name()
				+ " has started.");
		dataset.setAsCurrent();
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		String path = workflow.saveArff();
		System.out.println("Arff for " + dataset.name()
				+ " has been exported to " + path);
		return path;
	}

	public static void processArff(Experiments chosenExperiment, String arffPath)
			throws Exception {
		System.out.println("Processing: " + arffPath + "...");
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		Classifier classifier = chosenExperiment.getClassifier();
		ExperimentBase experiment = new CrossValidationExperiment(classifier);
		workflow.executeArff(experiment, arffPath);
		System.out.println("Arff has been processed.");
	}

	public static void runExperiment(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		System.out.println("Experiment " + chosenExperiment.name() + " for "
				+ dataset.name() + " has been started.");
		dataset.setAsCurrent();
		Classifier classifier = chosenExperiment.getClassifier();
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		ExperimentBase experiment = new CrossValidationExperiment(classifier);
		workflow.runExperiment(experiment);
		System.out.println("Experiment performed successfully");
	}
}
