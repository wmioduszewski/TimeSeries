package pl.poznan.put.TimeSeries;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Classifying.CrossValidationExperiment;
import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.classifiers.Classifier;

public class GeneralExperimentTest {

	private static String path;
	private static String folderPath;
	@BeforeClass
	public static void beforeClass() throws Exception{
		path = CommonConfig.getInstance().getSingleDataPath();
		folderPath = CommonConfig.getInstance().getDataFolderPath();
		CommonConfig.getInstance().setSingleDataPath("SAMPLEDATASET");
		CommonConfig.getInstance().setDataFolderPath("testData/");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		CommonConfig.getInstance().setSingleDataPath(path);
		CommonConfig.getInstance().setDataFolderPath(folderPath);
	}

	@Test
	public void directExperiments() {
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
	
	@Test
	public void fileBasedExperiments() throws Exception {
		for (Experiments chosenExperiment : Experiments.values()) {
			String path=Launcher.exportArff(chosenExperiment, Datasets.SAMPLEUNITTEST);
			Launcher.processArff(chosenExperiment, path);
		}
	}
}
