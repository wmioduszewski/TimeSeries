package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.SaxArffCandidateRow;
import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class NewSaxArffBuilder {

	private static int regularPartsForDivision = Integer.parseInt(Configuration
			.getProperty("regularPartsForDivision"));

	public static Instances buildInstancesFromStats(
			List<SaxArffCandidateRow> input) {

		List<List<String>> distincts = new LinkedList<List<String>>();
		for (int i = 0; i < regularPartsForDivision; i++) {
			List<String> currentPeriodDistincts = new LinkedList<String>();
			for (SaxArffCandidateRow linkedList : input) {
				Set<String> keys = linkedList.getPeriodicNgrams().get(i)
						.keySet();
				for (String key : keys) {
					if (!currentPeriodDistincts.contains(key)) {
						currentPeriodDistincts.add(key);
					}
				}
			}
			distincts.add(currentPeriodDistincts);
		}

		FastVector attrInfo = new FastVector();

		for (int i = 0; i < regularPartsForDivision; i++) {
			String prefix = "o" + (i + 1);
			for (String elem : distincts.get(i)) {
				attrInfo.addElement(new Attribute(prefix + elem));
			}
		}

		List<Double> destClasses = input.stream().map(x -> x.getDestClass())
				.distinct().collect(Collectors.toList());

		if (destClasses.size() == 1)
			System.out.println("There is only one class in dataset!");

		FastVector destValues = new FastVector();
		for (Double elem : destClasses) {
			destValues.addElement(elem.toString());
		}

		Attribute destClass = new Attribute("destClass", destValues);
		attrInfo.addElement(destClass);

		Instances instances = new Instances("Sax", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		for (SaxArffCandidateRow linkedList : input) {
			Instance patient = new Instance(attrInfo.size());
			int attrIndex = 0;
			for (int i = 0; i < regularPartsForDivision; i++) {
				List<String> currentDistincts = distincts.get(i);
				HashMap<String, AtomicInteger> currentMap = linkedList
						.getPeriodicNgrams().get(i);
				for (int j = 0; j < currentDistincts.size(); j++) {
					int count = 0;
					String key = currentDistincts.get(j);
					if (currentMap.containsKey(key)) {
						count = currentMap.get(key).intValue();
					}
					patient.setValue(attrIndex++, count);
				}
			}
			patient.setValue(attrIndex,//TODO: test it with real data
					destClasses.indexOf(linkedList.getDestClass()));
			instances.add(patient);
		}

		return instances;
	}

	public static void saveArff(Instances dataSet, String path) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
		try {
			saver.setFile(new File(path));
			saver.writeBatch();
		} catch (IOException e) {
			System.out.println(String.format("Unable to save arff to path: %s",
					path));
			e.printStackTrace();
		}
	}

}
