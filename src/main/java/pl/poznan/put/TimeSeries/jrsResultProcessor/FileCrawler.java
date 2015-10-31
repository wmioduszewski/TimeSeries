package pl.poznan.put.TimeSeries.jrsResultProcessor;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Classifying.Datasets;
import pl.poznan.put.TimeSeries.Classifying.ExperimentResult;
import pl.poznan.put.TimeSeries.Classifying.Experiments;
import pl.poznan.put.TimeSeries.Util.FileLister;

public class FileCrawler {

	//DOMINANTforECGFIVEDAYSwithparts-1ngram-2alpha-3_AD.isf.jRS_LGBalanced_n10_k20_x5_a3VCDomLEM.res
	
	//private String[] arffVariants = new String[] { "parts-10", "parts-20", "parts-3", "parts-5", "parts-48" };
	
	private String[] arffVariants = new String[] { 
			"parts-1ngram-2alpha-3", 
			"parts-1ngram-2alpha-5", 
			"parts-1ngram-2alpha-11", 
			"parts-1ngram-3alpha-3", 
			"parts-1ngram-3alpha-5",
			"parts-1ngram-3alpha-11",
			"parts-3ngram-2alpha-3",
			"parts-3ngram-2alpha-5",
			"parts-3ngram-2alpha-11",
			"parts-3ngram-3alpha-3",
			"parts-3ngram-3alpha-5",
			"parts-3ngram-3alpha-11",
			"parts-5ngram-2alpha-3",
			"parts-5ngram-2alpha-5",
			"parts-5ngram-2alpha-11",
			"parts-5ngram-3alpha-3",
			"parts-5ngram-3alpha-5",
			"parts-5ngram-3alpha-11",			
	};
	
	private Datasets dataset = Datasets.ECGFIVEDAYS;
	private String outputFolder = "output/results/dominant/";
	private String inputFolder = "output/results/dominant-clean/";

	private Experiments experiment = Experiments.DOMINANT;

	private String prefix, sufix;
	private String[] vcdomlemVariants = new String[] { "_AD.isf.jRS_LGBalanced_n10_k20_x5_a3VCDomLEM.res",
			"_AD.isf.jRS_LGBalanced_n20_k20_x5_a3VCDomLEM.res" };
			
	public void wholeExperiment() throws Exception {
		for (String prefix : arffVariants) {
			for (String sufix : vcdomlemVariants) {
				Path path = Paths.get(outputFolder + experiment + "4allwith-" + prefix + sufix);
				try (BufferedWriter writer = Files.newBufferedWriter(path)) {
					writer.write(resultsForEachDataset(prefix, sufix));
				}
			}
		}
	}

	public String resultsForEachDataset(String arff, String vc) {
		StringBuilder sb = new StringBuilder();
		sb.append(prepareFileHeader(arff,vc));

		for (Datasets dataset : Datasets.values()) {
			if (dataset == Datasets.WAFER || dataset == Datasets.SAMPLEUNITTEST || dataset == Datasets.ECG200)
				continue;
			this.dataset = dataset;
			constructPrefixAndSufix(arff, vc);
			ExperimentResult res = computeResult();

			sb.append("\n" + dataset);
			sb.append("\n" + res.toString() + "\n");

		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	private String prepareFileHeader(String arff, String vc) {
		StringBuilder sb = new StringBuilder();
		sb.append("VCDOMLEM " + experiment + " with params:\n");
		sb.append(arff);
		sb.append("\n" + vc.substring(vc.lastIndexOf("LGBalanced") + 2, vc.length()));
		sb.append("\n");
		return sb.toString();
	}

	public ExperimentResult computeResult() {
		ExperimentResult res = new ExperimentResult(0, 0, 0, 0, 0, 0);
		List<Path> paths = listFiles();
		for (Path path : paths) {
			res.add(computeSingleResult(path, paths.size() > 1));
		}
		res.divideBy(paths.size());
		return res;
	}

	private ExperimentResult computeSingleResult(Path path, boolean isMultiFile) {
		ContentInterpreter interpreter = new ContentInterpreter();
		ExperimentResult res = interpreter.analyzeFile(path, isMultiFile);
		return res;
	}

	private void constructPrefixAndSufix(String arff, String vc) {
		StringBuilder sb = new StringBuilder();
		sb.append(experiment);
		sb.append("for");
		sb.append(dataset);
		sb.append("with");
		sb.append(arff);
		prefix = sb.toString();
		sufix = vc;
	}

	private List<Path> listFiles() {
		String[] list = FileLister.getDirectoryFiles(inputFolder, prefix, sufix);
		List<Path> paths = new ArrayList<>();
		for (String elem : list) {
			Path elemPath = Paths.get(inputFolder + elem);
			paths.add(elemPath);
		}
		return paths;
	}
}
