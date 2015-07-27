package pl.poznan.put.TimeSeries.DataExporters;

import java.util.Comparator;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class OriginalSignalArffBuilder extends ArffExporterBase {

	private List<? extends IRecord> input;

	public OriginalSignalArffBuilder(List<? extends IRecord> records) {
		this.input = records;
		setDestinationClasses(input);
		setAttributes();
	}

	@Override
	protected void setAttributes() {
		attrInfo = new FastVector();

		Comparator<IRecord> comp = (x, y) -> Integer.compare(x.getValues()
				.size(), y.getValues().size());

		int attrCount = input.stream().min(comp).get().getValues().size();

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
		instances = new Instances("Original signal", attrInfo, input.size());
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
