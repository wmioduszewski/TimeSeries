package pl.poznan.put.TimeSeries.DataExporters;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import pl.poznan.put.TimeSeries.Util.StringDominance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class NewSaxArffBuilder extends NewArffExporterBase {

	public NewSaxArffBuilder(boolean isDominant) {
		super(isDominant);
		// TODO Auto-generated constructor stub
	}

	private static int regularPartsForDivision = CommonConfig.getInstance().getDivisionPartsAmount();

	private List<List<String>> distincts;
	List<Double> destClasses;
	private int inputSize; 

	public void buildInstancesFromStats(List<CalculatedRecord> input)
			throws Exception {
		
		inputSize = input.size();
		
		distincts = getPeriodicDistincts(input);

		destClasses = input.stream().map(x -> x.getDestClass())
				.distinct().collect(Collectors.toList());

		for (CalculatedRecord linkedList : input) {
			Instance patient = new Instance(distincts.size());
			int attrIndex = 0;
			for (int i = 0; i < regularPartsForDivision; i++) {
				List<String> currentDistincts = distincts.get(i);
				HashMap<String, Integer> currentMap = linkedList
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

		// cutAttributes(instances);
	}

	public static Instances buildDominantInstancesFromStats(
			List<CalculatedRecord> input) throws Exception {

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

		Attribute destClassAttribute = constructDestinationClassesNominalAttribute(destClasses);
		attrInfo.addElement(destClassAttribute);

		Instances instances = new Instances("Sax", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		for (CalculatedRecord linkedList : input) {
			Instance patient = new Instance(attrInfo.size());
			int attrIndex = 0;
			for (int i = 0; i < regularPartsForDivision; i++) {
				List<String> currentDistincts = distincts.get(i);
				HashMap<String, Integer> currentMap = linkedList
						.getPeriodicNgrams().get(i);
				for (int j = 0; j < currentDistincts.size(); j++) {
					int lowersRes = 0;
					int greatersRes = 0;
					String key = currentDistincts.get(j);

					List<String> lowers = StringDominance
							.getListOfLessOrEqualStrings(key, currentDistincts);
					List<String> greaters = StringDominance
							.getListOfGreaterOrEqualStrings(key,
									currentDistincts);

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
			List<CalculatedRecord> input) {
		List<List<String>> distincts = new LinkedList<List<String>>();
		for (int i = 0; i < regularPartsForDivision; i++) {
			List<String> currentPeriodDistincts = new LinkedList<String>();
			for (CalculatedRecord linkedList : input) {
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

	@Override
	protected void setAttributes() {
		FastVector attrInfo = new FastVector();
		for (int i = 0; i < regularPartsForDivision; i++) {
			String prefix = "o" + (i + 1);
			for (String elem : distincts.get(i)) {
				attrInfo.addElement(new Attribute(prefix + elem));
			}
		}
		
		Attribute destClassAttribute = null;
		try {
			destClassAttribute = constructDestinationClassesNominalAttribute(destClasses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attrInfo.addElement(destClassAttribute);

		Instances instances = new Instances("Sax", attrInfo, inputSize);
		instances.setClassIndex(instances.numAttributes() - 1);

	}
}
