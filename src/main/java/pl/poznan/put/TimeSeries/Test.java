package pl.poznan.put.TimeSeries;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import pl.poznan.put.TimeSeries.Util.StringDominance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class Test {
	
	public static void testDominance(){
		String input = "";
		Scanner sc = new Scanner(System.in);
		while(!"end".equals(input)){
			input = sc.nextLine();
			String [] arr = input.split(" ");
			if(StringDominance.isStringGreaterOrEqualTo(arr[0], arr[1]))
				System.out.println(String.format("%s is greater or equal to %s", arr[1], arr[0]));
			if(StringDominance.isStringLessOrEqualTo(arr[0], arr[1]))
				System.out.println(String.format("%s is less or equal to %s", arr[1], arr[0]));
		}
	}

	public static void main(String[] args) throws Exception {
//		Instances ins = produceInstances();
//		saveArff(ins, "mdstest");
		testDominance();
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

		int attrValue = 0;
		for (int j = 0; j < patientCount; j++) {
			int attrIndex = 0;
			Instance patient = new Instance(attrInfo.size());
			for (int i = 0; i < attrCount; i++) {
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
