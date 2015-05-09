package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public abstract class ArffExporterBase {

	protected static int regularPartsForDivision = CommonConfig.getInstance()
			.getDivisionPartsAmount();
	protected Instances instances;
	protected FastVector attrInfo;
	protected List<Double> destClasses;
	protected abstract void setAttributes();

	public abstract Instances buildInstances();

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
}
