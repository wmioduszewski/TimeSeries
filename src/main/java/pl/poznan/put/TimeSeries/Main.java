package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public class Main {

	public static void main(String[] args) throws Exception {
		Experiments exp = Experiments.KNN;
		Datasets dataset = Datasets.CHLORINECONCENTRATION;
		
		CommonConfig.getInstance().setCurrentDataset(dataset);
		CommonConfig.getInstance().setCurrentExperiment(exp);
		launchExperiment(exp, dataset);
	}

	private static void launchExperiment(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		long startTime = System.currentTimeMillis();

		Launcher.exportArff(chosenExperiment, dataset);

		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("Execution took " + diff / 1000 + "s." + diff % 1000
				+ "ms");
	}
}
