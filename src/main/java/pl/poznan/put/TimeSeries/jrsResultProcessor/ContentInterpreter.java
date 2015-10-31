package pl.poznan.put.TimeSeries.jrsResultProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.poznan.put.TimeSeries.Classifying.ExperimentResult;

public class ContentInterpreter {

	private Path path = null;

	public ExperimentResult analyzeFile(Path path, boolean isMultiFile) {

		this.path = path;

		ExperimentResult res = new ExperimentResult(0, 0, 0, 0, 0, 0);

		// so far it is in percent, divide by 100
		double acc = retrieveCommonStatistic("Correctly Classified Instances", "(.*?)(\\d+.\\d+)%(.*?)") / 100;
		double f1 = retrieveCommonStatistic("F1", "(\t*?)(\\d+.\\d+)\t") / 100;

		res.setF1(f1);
		res.setAccuracy(acc);

		if (!isMultiFile)
			retrieveBinaryClassStatistics(res);

		res.setPcc(res.getAccuracy());
		return res;
	}

	private double retrieveCommonStatistic(String startsWith, String strPattern) {
		double res = -1;
		try (Stream<String> stream = Files.lines(path)) {
			String specificLine = stream.filter(line -> line.startsWith(startsWith)).findFirst().get();
			Pattern pattern = Pattern.compile(strPattern);
			Matcher m = pattern.matcher(specificLine);
			if (m.find()) {
				res = Double.parseDouble(m.group(2));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	private void retrieveBinaryClassStatistics(ExperimentResult resultToFill) {
		double sensitivity = -1, specificity = -1,gMean=-1;

		try (Stream<String> stream = Files.lines(path)) {
			int flag = 0;
			for (String line : stream.collect(Collectors.toList())) {
				if (line.startsWith("TP Rate")){
					flag = 1;
					continue;
				}
				if (flag == 1) {
					Pattern pattern = Pattern.compile("^(\\d.\\d+)(.*?)");
					Matcher m = pattern.matcher(line);
					if (m.find()) {
						// System.out.println("BLUBI "+ m.group(1));
						sensitivity = Double.parseDouble(m.group(1));
					}
					flag = 2;
					continue;
				}
				if (flag == 2) {
					Pattern pattern = Pattern.compile("^(\\d.\\d+)(.*?)");
					Matcher m = pattern.matcher(line);
					if (m.find()) {
						specificity = Double.parseDouble(m.group(1));
					}
					flag = 0;
					continue;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gMean = Math.sqrt(specificity * sensitivity);
		
		resultToFill.setSensitivity(sensitivity);
		resultToFill.setSpecificity(specificity);
		resultToFill.setgMean(gMean);
		
		// return res;
	}

}
