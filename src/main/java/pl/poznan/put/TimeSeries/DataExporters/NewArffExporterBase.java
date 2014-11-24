package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import pl.poznan.put.TimeSeries.Util.Configuration;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public abstract class NewArffExporterBase {
	
	protected Instances instances;
	protected FastVector attrInfo; 
	protected boolean isDominant;
	
	
	
	public NewArffExporterBase(boolean isDominant) {
		super();
		this.isDominant = isDominant;
	}

	protected abstract void setAttributes();
	
	public void buildInstances(){
		setAttributes();
	}
	
	private void cutAttributes(){
		float attributesToCutRatio = Float.parseFloat(Configuration.getProperty("attributesToCutRatio"));
		int attributesToCut = (int) ((instances.numAttributes()-1) * attributesToCutRatio);
		Random rand = new Random();
		for(int i=0;i<attributesToCut;i++){
			int index = rand.nextInt(instances.numAttributes()-1);
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

}
