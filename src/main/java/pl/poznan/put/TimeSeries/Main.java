package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.Experiments;

public class Main {

	public static void main(String[] args) throws Exception {
		launchExperiment(Experiments.KNN, Datasets.CHLORINECONCENTRATION);
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
