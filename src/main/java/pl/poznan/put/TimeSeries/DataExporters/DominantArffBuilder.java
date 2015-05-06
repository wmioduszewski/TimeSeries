package pl.poznan.put.TimeSeries.DataExporters;

import java.util.HashMap;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Util.StringDominance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DominantArffBuilder extends ArffExporterBase {

	public DominantArffBuilder(List<CalculatedRecord> input) {
		super(input);
	}

	@Override
	protected void setAttributes() {
		attrInfo = new FastVector();
		for (int i = 0; i < regularPartsForDivision; i++) {
			String prefix = "o" + (i + 1);
			for (String elem : distincts.get(i)) {
				attrInfo.addElement(new Attribute(prefix + "atM" + elem));
				attrInfo.addElement(new Attribute(prefix + "atL" + elem));
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
		for (List<String> elem : distincts) {
			// StringDominance.eraseMaxString(elem);
			// StringDominance.eraseMinString(elem);
		}

		instances = new Instances("Sax dominant", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		for (CalculatedRecord calculatedRecord : input) {
			Instance record = new Instance(attrInfo.size());
			int attrIndex = 0;
			for (int i = 0; i < regularPartsForDivision; i++) {
				List<String> currentDistincts = distincts.get(i);
				HashMap<String, Integer> currentMap = calculatedRecord
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

					record.setValue(attrIndex++, lowersRes);
					record.setValue(attrIndex++, greatersRes);
				}
			}
			record.setValue(attrIndex,
					destClasses.indexOf(calculatedRecord.getDestClass()));
			instances.add(record);
		}
		return instances;
	}
}