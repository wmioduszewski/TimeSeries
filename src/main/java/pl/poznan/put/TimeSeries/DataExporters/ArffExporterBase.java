package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import pl.poznan.put.TimeSeries.Model.IAssignedClass;
import pl.poznan.put.TimeSeries.Util.Config;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public abstract class ArffExporterBase {

	protected static int regularPartsForDivision = Config.getInstance()
			.getDivisionPartsAmount();

	protected static Attribute constructDestinationClassesNominalAttribute(
			List<Double> destClasses) throws Exception {
		
		if (destClasses.size() == 1)
			throw new Exception("There is only one class in dataset!");

		FastVector destValues = new FastVector();
		for (Double elem : destClasses) {
			Integer ii = elem.intValue();
			destValues.addElement(ii.toString());
		}
		Attribute destClassAttribute = new Attribute("destClass", destValues);
		return destClassAttribute;
	}

	protected FastVector attrInfo;
	protected List<Double> destClasses;
	protected Instances instances;

	public abstract Instances buildInstances();

	public int getIndexOfDestinationClass(Double classValue) {
		int classIndex = destClasses.indexOf(classValue);
		return classIndex;
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
	
	protected abstract void setAttributes();

	protected void setDestinationClasses(List<? extends IAssignedClass> records){
		destClasses = records.stream().map(x -> x.getDestinationClass()).distinct()
				.collect(Collectors.toList());
	}
}
