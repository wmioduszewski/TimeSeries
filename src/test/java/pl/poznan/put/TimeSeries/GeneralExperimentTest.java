package pl.poznan.put.TimeSeries;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public class GeneralExperimentTest {

	private static String folderPath;
	private static String path;

	@BeforeClass
	public static void beforeClass() throws Exception {
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
			try {
				Launcher.runExperiment(chosenExperiment,
						Datasets.SAMPLEUNITTEST);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void fileBasedExperiments() throws Exception {
		for (Experiments chosenExperiment : Experiments.values()) {
			String path = Launcher.exportArff(chosenExperiment,
					Datasets.SAMPLEUNITTEST);
			Launcher.processArff(chosenExperiment, path);
		}
	}
}
