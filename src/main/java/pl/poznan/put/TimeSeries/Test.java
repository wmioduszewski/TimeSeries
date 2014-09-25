package pl.poznan.put.TimeSeries;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class Test {

	public static void main(String[] args) throws Exception {
		Instances ins = produceInstances();
		saveArff(ins, "mdstest");
	}

	private static Instances produceInstances() {

		int attrCount = 3;
		int patientCount = 3;

		FastVector attrInfo = new FastVector();
		for (int i = 0; i < attrCount; i++) {
			attrInfo.addElement(new Attribute("attr" + i));
		}

		FastVector destValues = new FastVector();
		destValues.addElement("1");
		destValues.addElement("2");

		Attribute destClass = new Attribute("destClass", destValues);
		attrInfo.addElement(destClass);

		Instances instances = new Instances("Sax", attrInfo, 3);
		instances.setClassIndex(instances.numAttributes() - 1);

		int attrValue=0;
		for (int j = 0; j < patientCount; j++) {
			int attrIndex = 0;
			Instance patient = new Instance(attrInfo.size());
			for (int i = 0; i < attrCount ; i++) {
				patient.setValue(attrIndex++, attrValue++);
			}
			patient.setValue(attrIndex, 1);
			patient.setDataset(instances);
			instances.add(patient);
		}
		return instances;
	}

	public static void saveArff(Instances dataSet, String path) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
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
