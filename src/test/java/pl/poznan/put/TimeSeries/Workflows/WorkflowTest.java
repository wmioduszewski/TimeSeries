package pl.poznan.put.TimeSeries.Workflows;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Classifying.ExperimentBase;
import pl.poznan.put.TimeSeries.Classifying.ExperimentResult;
import pl.poznan.put.TimeSeries.Classifying.RegularExperiment;
import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.classifiers.trees.J48;

public class WorkflowTest {
	
	private static String path;
	@BeforeClass
	public static void beforeClass() throws Exception{
		path = CommonConfig.getInstance().getSingleDataPath();
		CommonConfig.getInstance().setSingleDataPath("SAMPLEDATASET");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		CommonConfig.getInstance().setSingleDataPath(path);
	}

	@Test
	public void test() {
		ExperimentBase experiment = new RegularExperiment(new J48()); 
		WorkflowBase workflow = new EamonnSaxWorkflow(DivisionOptions.Regular, true);
		ExperimentResult result = null;
		try {
			workflow.runExperiment(experiment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
