package pl.poznan.put.TimeSeries;

import java.time.Duration;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.Experiments;

public class Main {

	public static void main(String[] args) throws Exception {
//		launchExperiment(Experiments.DTW, Datasets.ECG200);
		launchExperimentForAllDatasets(Experiments.DTW);
	}
	
	private static void launchExperimentForAllDatasets(Experiments chosenExperiment) throws Exception{
		for (Datasets dataset : Datasets.values()) {
			long startTime = System.currentTimeMillis();
			if(dataset==Datasets.SAMPLEUNITTEST) continue;
			Launcher.runExperiment(chosenExperiment, dataset);
		
		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		Duration d = Duration.ofMillis(diff);
		System.out.println("Execution took " + d);
		}
	}

	private static void launchExperiment(Experiments chosenExperiment,
			Datasets dataset) throws Exception {
		long startTime = System.currentTimeMillis();

		Launcher.runExperiment(chosenExperiment, dataset);

		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("Execution took " + diff / 1000 + "s." + diff % 1000
				+ "ms");
	}
}
