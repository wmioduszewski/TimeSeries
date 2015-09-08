package pl.poznan.put.TimeSeries;

import java.time.Duration;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Classifying.Variants;
import pl.poznan.put.TimeSeries.Util.Config;

public class Main {

	private interface Experimentable {
		String runExperiment(Experiments experiment, Datasets dataset)
				throws Exception;
	}

	public static void main(String[] args) throws Exception {
		doTheJob();
	}

	private static void datasetJob(Experimentable experimentable,
			Experiments experiment) throws Exception {

		Datasets dataset = getDataset();
		if (dataset == null) {
			for (Datasets elem : Datasets.values()) {
				dataset = elem;
				if (dataset == Datasets.SAMPLEUNITTEST)
					continue;
				runExperimentableSingular(experimentable, experiment, dataset);
			}
		} else
			runExperimentableSingular(experimentable, experiment, dataset);
	}

	private static void doTheJob() throws Exception {
		int id = Config.getInstance().getVariant();
		Variants variant = Variants.values()[id];
		Experiments experiment = getExperiment();
		Experimentable experimentable = null;

		switch (variant) {
		case ARFFEXPORT:
			experimentable = new Experimentable() {

				@Override
				public String runExperiment(Experiments experiment,
						Datasets dataset) throws Exception {
					return Launcher.exportArff(experiment, dataset);
				}
			};
			datasetJob(experimentable, experiment);
			break;

		case ARFFPROCESS:
			String arffPath = Config.getInstance().getInputArff();
			Launcher.processArff(experiment, arffPath);
			break;

		case FULL:
			experimentable = new Experimentable() {

				@Override
				public String runExperiment(Experiments experiment,
						Datasets dataset) throws Exception {
					return Launcher.runExperiment(experiment, dataset);
				}
			};
			datasetJob(experimentable, experiment);
			break;
		}
	}

	private static Datasets getDataset() {
		int id = Config.getInstance().getTargetDataset();
		if (id < 0)
			return null;
		return Datasets.values()[id];
	}

	private static Experiments getExperiment() {
		int id = Config.getInstance().getExperimentId();
		return Experiments.values()[id];
	}

	private static void runExperimentableSingular(
			Experimentable experimentable, Experiments experiment,
			Datasets dataset) throws Exception {
		long startTime = System.currentTimeMillis();

		experimentable.runExperiment(experiment, dataset);

		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		Duration d = Duration.ofMillis(diff);
		System.out.println("Execution took " + d);
	}
}
