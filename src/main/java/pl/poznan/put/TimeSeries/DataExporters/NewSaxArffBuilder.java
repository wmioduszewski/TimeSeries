package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.SaxArffCandidateRow;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.StringDominance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class NewSaxArffBuilder extends NewArffExporterBase {

	private static int regularPartsForDivision = Integer.parseInt(Configuration
			.getProperty("divisionPartsAmount"));

	public static Instances buildInstancesFromStats(
			List<SaxArffCandidateRow> input) throws Exception {

		List<List<String>> distincts = getPeriodicDistincts(input);
		
		List<Double> destClasses = input.stream().map(x -> x.getDestClass())
				.distinct().collect(Collectors.toList());

		FastVector attrInfo = new FastVector();
		for (int i = 0; i < regularPartsForDivision; i++) {
			String prefix = "o" + (i + 1);
			for (String elem : distincts.get(i)) {
				attrInfo.addElement(new Attribute(prefix + elem));
			}
		}

		Attribute destClassAttribute = constructDestinationClassesAttribute(destClasses);
		attrInfo.addElement(destClassAttribute);

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
			patient.setValue(attrIndex,// TODO: test it with real data
					destClasses.indexOf(linkedList.getDestClass()));
			instances.add(patient);
		}
		
		//cutAttributes(instances);

		return instances;
	}

	public static Instances buildDominantInstancesFromStats(
			List<SaxArffCandidateRow> input) throws Exception {

		List<List<String>> distincts = getPeriodicDistincts(input);
		for (List<String> elem : distincts) {
				StringDominance.eraseMaxString(elem);
				StringDominance.eraseMinString(elem);
		}

		List<Double> destClasses = input.stream().map(x -> x.getDestClass())
				.distinct().collect(Collectors.toList());

		FastVector attrInfo = new FastVector();
		for (int i = 0; i < regularPartsForDivision; i++) {
			String prefix = "o" + (i + 1);
			for (String elem : distincts.get(i)) {
				attrInfo.addElement(new Attribute(prefix + "atM" + elem));
				attrInfo.addElement(new Attribute(prefix + "atL" + elem));
			}
		}

		Attribute destClassAttribute = constructDestinationClassesAttribute(destClasses);
		attrInfo.addElement(destClassAttribute);

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
					int lowersRes = 0;
					int greatersRes = 0;
					String key = currentDistincts.get(j);
					
					List<String> lowers = StringDominance.getListOfLessOrEqualStrings(key, currentDistincts);
					List<String> greaters = StringDominance.getListOfGreaterOrEqualStrings(key, currentDistincts);
					
					for (String lower : lowers) {
						if (currentMap.containsKey(lower)) {
							lowersRes += currentMap.get(lower).intValue();
						}						
					}
					
					for (String greater : greaters) {
						if (currentMap.containsKey(greater)) {
							greatersRes += currentMap.get(greater).intValue();
						}						
					}
					
					patient.setValue(attrIndex++, lowersRes);
					patient.setValue(attrIndex++, greatersRes);
				}
			}
			patient.setValue(attrIndex,// TODO: test it with real data
					destClasses.indexOf(linkedList.getDestClass()));
			instances.add(patient);
		}

		return instances;
	}

	private static List<List<String>> getPeriodicDistincts(
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
		return distincts;
	}

	private static Attribute constructDestinationClassesAttribute(
			List<Double> destClasses) throws Exception {
		if (destClasses.size() == 1)
			throw new Exception("There is only one class in dataset!");

		FastVector destValues = new FastVector();
		for (Double elem : destClasses) {
			destValues.addElement(elem.toString());
		}
		Attribute destClassAttribute = new Attribute("destClass", destValues);
		return destClassAttribute;
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
	
	private static void cutAttributes(Instances instances){
		float attributesToCutRatio = Float.parseFloat(Configuration.getProperty("attributesToCutRatio"));
		int attributesToCut = (int) ((instances.numAttributes()-1) * attributesToCutRatio);
		Random rand = new Random();
		for(int i=0;i<attributesToCut;i++){
			int index = rand.nextInt(instances.numAttributes()-1);
			instances.deleteAttributeAt(index);
		}
	}

}
