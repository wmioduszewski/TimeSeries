package pl.poznan.put.TimeSeries.DataExporters;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class OriginalSignalArffBuilder extends ArffExporterBase {

	private List<EamonnRecord> input;

	public OriginalSignalArffBuilder(List<EamonnRecord> records) {
		this.input = records;
		destClasses = input.stream().map(x -> x.getDestinationClass())
				.distinct().collect(Collectors.toList());
		setAttributes();
	}

	@Override
	protected void setAttributes() {
		attrInfo = new FastVector();

		int attrCount = input.stream()
				.max((x, y) -> (x.getValues().size() - y.getValues().size()))
				.get().getValues().size();

		for (int i = 0; i < attrCount; i++) {
			attrInfo.addElement(new Attribute("a" + (i + 1)));
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
		instances = new Instances("Pure signal", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		for (EamonnRecord eamonnRecord : input) {

			Instance instance = new Instance(attrInfo.size());
			instance.setDataset(instances);
			List<Float> vals = eamonnRecord.getValues();
			for (int i = 0; i < instances.numAttributes() - 1; i++) {
				instance.setValue(i, vals.get(i));
			}
			int destClassIndex = getIndexOfDestinationClass(eamonnRecord
					.getDestinationClass());
			instance.setValue(instances.numAttributes() - 1, destClassIndex);
			instances.add(instance);
		}
		return instances;
	}

}
