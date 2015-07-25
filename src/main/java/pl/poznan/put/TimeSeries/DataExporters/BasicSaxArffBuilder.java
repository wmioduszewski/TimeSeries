package pl.poznan.put.TimeSeries.DataExporters;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class BasicSaxArffBuilder extends ArffExporterBase {

	private List<? extends IRecord> input;

	public BasicSaxArffBuilder(List<? extends IRecord> records) {
		this.input = records;
		setDestinationClasses(input);
		setAttributes();
	}

	@Override
	public Instances buildInstances() {
		instances = new Instances("Basic ngrams", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		int saxAttrId = 0;
		int destClassAttrId = 1;
		for (IRecord row : input) {
			Instance instance = new Instance(attrInfo.size());
			instance.setDataset(instances);
			instance.setValue(saxAttrId, row.getSaxString());
			int destClassIndex = getIndexOfDestinationClass(row
					.getDestinationClass());

			instance.setValue(destClassAttrId, destClassIndex);
			instances.add(instance);
		}
		return instances;
	}

	@Override
	protected void setAttributes() {
		attrInfo = new FastVector();

		// needed only to pass null value - to mark the attribute as string attr

		FastVector attrVals = null;
		attrInfo.addElement(new Attribute("saxString", attrVals));

		Attribute destClassAttribute = null;
		try {
			destClassAttribute = constructDestinationClassesNominalAttribute(destClasses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		attrInfo.addElement(destClassAttribute);
	}
}
