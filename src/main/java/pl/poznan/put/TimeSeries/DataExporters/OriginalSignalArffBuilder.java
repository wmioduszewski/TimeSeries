package pl.poznan.put.TimeSeries.DataExporters;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.IRecord;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class OriginalSignalArffBuilder extends ArffExporterBase {

	private List<? extends IRecord> input;

	public OriginalSignalArffBuilder(List<? extends IRecord> records) {
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

		for (IRecord record : input) {

			Instance instance = new Instance(attrInfo.size());
			instance.setDataset(instances);
			List<Float> vals = record.getValues();
			for (int i = 0; i < instances.numAttributes() - 1; i++) {
				instance.setValue(i, vals.get(i));
			}
			int destClassIndex = getIndexOfDestinationClass(record
					.getDestinationClass());
			instance.setValue(instances.numAttributes() - 1, destClassIndex);
			instances.add(instance);
		}
		return instances;
	}

}
