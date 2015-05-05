package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public abstract class ArffExporterBase {

	protected Instances instances;
	protected FastVector attrInfo;

	protected static int regularPartsForDivision = CommonConfig.getInstance()
			.getDivisionPartsAmount();

	protected List<List<String>> distincts;
	List<Double> destClasses;
	protected List<CalculatedRecord> input;

	public ArffExporterBase(List<CalculatedRecord> input) {
		this.input = input;
		getPeriodicDistincts();
		destClasses = input.stream().map(x -> x.getDestClass()).distinct()
				.collect(Collectors.toList());
		setAttributes();
	}

	protected abstract void setAttributes();

	public abstract Instances buildInstances();

	// TODO: probably should not be here, in this class
	private void cutAttributes() {
		float attributesToCutRatio = CommonConfig.getInstance()
				.getAttributesToCutRatio();
		int attributesToCut = (int) ((instances.numAttributes() - 1) * attributesToCutRatio);
		Random rand = new Random();
		for (int i = 0; i < attributesToCut; i++) {
			int index = rand.nextInt(instances.numAttributes() - 1);
			instances.deleteAttributeAt(index);
		}
	}

	public void saveArff(String path) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(instances);
		try {
			saver.setFile(new File(path));
			saver.writeBatch();
		} catch (IOException e) {
			System.out.println(String.format("Unable to save arff to path: %s",
					path));
			e.printStackTrace();
		}
	}

	protected static Attribute constructDestinationClassesNominalAttribute(
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

	protected void getPeriodicDistincts() {
		distincts = new ArrayList<List<String>>();
		for (int i = 0; i < regularPartsForDivision; i++) {
			List<String> currentPeriodDistincts = new ArrayList<String>();
			for (CalculatedRecord calculatedRecord : input) {
				Set<String> keys = calculatedRecord.getPeriodicNgrams().get(i)
						.keySet();
				for (String key : keys) {
					if (!currentPeriodDistincts.contains(key)) {
						currentPeriodDistincts.add(key);
					}
				}
			}
			distincts.add(currentPeriodDistincts);
		}
	}
}
