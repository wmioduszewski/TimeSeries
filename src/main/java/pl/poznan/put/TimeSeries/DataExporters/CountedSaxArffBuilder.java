package pl.poznan.put.TimeSeries.DataExporters;

import java.util.HashMap;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class CountedSaxArffBuilder extends SaxArffExporterBase {

	public CountedSaxArffBuilder(List<CalculatedRecord> input) {
		super(input);
	}

	@Override
	protected void setAttributes() {
		attrInfo = new FastVector();
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
			e.printStackTrace();
		}
		attrInfo.addElement(destClassAttribute);
	}

	@Override
	public Instances buildInstances() {

		instances = new Instances("Sax counted", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		int distinctsElementsSum = (int) distincts.stream()
				.flatMap(x -> x.stream()).count();

		for (CalculatedRecord calculatedRecord : input) {
			Instance instance = new Instance(distinctsElementsSum + 1);
			int attrIndex = 0;
			for (int i = 0; i < regularPartsForDivision; i++) {
				List<String> currentDistincts = distincts.get(i);
				HashMap<String, Integer> currentMap = calculatedRecord
						.getPeriodicNgrams().get(i);
				for (int j = 0; j < currentDistincts.size(); j++) {
					int count = 0;
					String key = currentDistincts.get(j);
					if (currentMap.containsKey(key)) {
						count = currentMap.get(key).intValue();
					}
					instance.setValue(attrIndex++, count);
				}
			}
			instance.setValue(attrIndex,
					destClasses.indexOf(calculatedRecord.getDestClass()));
			instances.add(instance);
		}

		// cutAttributes(instances);
		return instances;
	}
}
