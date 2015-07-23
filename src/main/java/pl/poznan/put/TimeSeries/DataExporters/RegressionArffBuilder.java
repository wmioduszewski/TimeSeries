package pl.poznan.put.TimeSeries.DataExporters;

import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.RegressionRow;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class RegressionArffBuilder extends ArffExporterBase {

	private List<RegressionRow> input;

	public RegressionArffBuilder(List<RegressionRow> input) {
		this.input = input;
		destClasses = input.stream().map(x -> x.getDestinationClass())
				.distinct().collect(Collectors.toList());
		setAttributes();
	}

	protected void setAttributes() {
		attrInfo = new FastVector();

		int regressionCount = input.stream().findFirst().get()
				.getRegressionResults().size();

		for (int i = 0; i < regressionCount; i++) {
			attrInfo.addElement(new Attribute(String.format("slope%d", i + 1)));
			attrInfo.addElement(new Attribute(String.format("intercept%d",
					i + 1)));
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
		instances = new Instances("Regression", attrInfo, input.size());
		instances.setClassIndex(instances.numAttributes() - 1);

		for (RegressionRow row : input) {
			Instance instance = new Instance(attrInfo.size());
			int attrIdx = 0;
			for (RegressionResult regResult : row.getRegressionResults()) {
				instance.setValue(attrIdx++, regResult.getSlope());
				instance.setValue(attrIdx++, regResult.getIntercept());
			}

			int destClassIndex = getIndexOfDestinationClass(row
					.getDestinationClass());
			instance.setValue(attrIdx, destClassIndex);
			instances.add(instance);
		}
		return instances;
	}
}
