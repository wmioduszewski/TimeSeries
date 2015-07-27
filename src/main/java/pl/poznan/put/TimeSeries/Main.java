package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.Experiments;

public class Main {

	public static void main(String[] args) throws Exception {
		launchExperiment(Experiments.KNN, Datasets.PATIENTS);
	}

	private static void launchExperiment(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		long startTime = System.currentTimeMillis();
		
		Launcher.processArff(chosenExperiment, "output/arffOutput/Knn3p3gramRegular.arff");
		
		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("Execution took " + diff / 1000 + "s." + diff % 1000
				+ "ms");
	}
}
