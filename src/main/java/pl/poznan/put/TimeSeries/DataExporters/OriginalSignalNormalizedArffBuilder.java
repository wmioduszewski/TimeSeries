package pl.poznan.put.TimeSeries.DataExporters;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class OriginalSignalNormalizedArffBuilder extends ArffExporterBase {

	private List<EamonnRecord> input;

	public OriginalSignalNormalizedArffBuilder(List<EamonnRecord> records) {
		this.input = records;
		destClasses = input.stream().map(x -> x.getDestinationClass())
				.distinct().collect(Collectors.toList());
		setAttributes();
	}

	@Override
	protected void setAttributes() {
		attrInfo = new FastVector();

		int attrCount = CommonConfig.getInstance().getSaxOutputLength();

		for (int i = 0; i < attrCount; i++) {
			attrInfo.addElement(new Attribute("char" + (i + 1)));
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
		instances = new Instances("Pure signal normalized by SAX", attrInfo,
				input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		for (EamonnRecord eamonnRecord : input) {
			Instance instance = new Instance(attrInfo.size());
			instance.setDataset(instances);
			char[] vals = eamonnRecord.getSaxString().toCharArray();
			for (int i = 0; i < instances.numAttributes() - 1; i++) {
				instance.setValue(i, vals[i] - 'a');
			}
			int destClassIndex = getIndexOfDestinationClass(eamonnRecord
					.getDestinationClass());
			instance.setValue(instances.numAttributes() - 1, destClassIndex);
			instances.add(instance);
		}
		return instances;
	}

}
