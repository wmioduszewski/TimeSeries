package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Util.Config;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class Launcher {

	public static String exportArff(Experiments chosenExperiment, Datasets dataset)
			throws Exception {
		Config.getInstance().setCurrentDataset(dataset);
		Config.getInstance().setCurrentExperiment(chosenExperiment);

		System.out.println("Arff export for " + dataset.name()
				+ " has started.");
		dataset.setAsCurrent();
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		String path = workflow.saveArff();
		System.out.println("Arff for " + dataset.name()
				+ " has been exported to " + path);
		return path;
	}

	public static String processArff(Experiments chosenExperiment, String arffPath)
			throws Exception {
		Config.getInstance().setCurrentExperiment(chosenExperiment);
		System.out.println("Processing: " + arffPath + "...");
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		Classifier classifier = chosenExperiment.getClassifier();
		ExperimentBase experiment = new CrossValidationExperiment(classifier);
		String res = workflow.executeArff(experiment, arffPath);
		System.out.print(res);
		System.out.println("Arff has been processed.");
		return res;
	}

	public static String runExperiment(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		Config.getInstance().setCurrentDataset(dataset);
		Config.getInstance().setCurrentExperiment(chosenExperiment);
		System.out.println("Experiment " + chosenExperiment.name() + " for "
				+ dataset.name() + " has been started.");
		dataset.setAsCurrent();
		Classifier classifier = chosenExperiment.getClassifier();
		WorkflowBase workflow = chosenExperiment.getWorkflow();
		ExperimentBase experiment = new CrossValidationExperiment(classifier);
		String res = workflow.runExperiment(experiment);
		System.out.print(res);
		System.out.println("Experiment performed successfully");
		return res;
	}
}
