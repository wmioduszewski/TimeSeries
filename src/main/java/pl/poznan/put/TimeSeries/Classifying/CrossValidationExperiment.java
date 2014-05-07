package pl.poznan.put.TimeSeries.Classifying;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import weka.attributeSelection.ClassifierSubsetEval;
import weka.attributeSelection.LinearForwardSelection;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class CrossValidationExperiment {
	
	public static void runExperiment(Classifier classifier, String trainSetPath,
			String testSetPath, long seed) throws Exception {
		BufferedReader readerTrain = new BufferedReader(new FileReader(trainSetPath));
		Instances trainSet = new Instances(readerTrain);
		trainSet.randomize(new Random(seed));
		readerTrain.close();
		
		BufferedReader readerTest = new BufferedReader(new FileReader(testSetPath));
		Instances testSet = new Instances(readerTest);
		testSet.randomize(new Random(seed));
		readerTest.close();
		
		if (trainSet.classIndex() == -1) trainSet.setClassIndex(trainSet.numAttributes() - 1);
		if (testSet.classIndex() == -1) testSet.setClassIndex(testSet.numAttributes() - 1);
		
		System.out.println("Data loaded.");
		
		classifier.buildClassifier(trainSet);
		System.out.println("Classifier has been learned.");
		
		System.out.println("Test evaluation...");
		
		double loss01 = 0;
		double squaredError = 0;
		for(int i = 0; i < testSet.numInstances(); i++) {
			Instance instance = testSet.instance(i);
			
			int truth = (int) instance.classValue();
			double[] distribution = classifier.distributionForInstance(instance);
			int prediction = distribution[1] >= distribution[0] ? 1 : 0;

			loss01 += truth == prediction ? 0 : 1;
			squaredError += Math.pow(1.0 - distribution[truth], 2);
		}
		
		loss01 /= (double)testSet.numInstances();
		squaredError /= (double)testSet.numInstances();
		
		System.out.println("Train/Test evaluation for " + classifier.getClass().getSimpleName());
		System.out.println(" - 0/1 loss:           " + loss01);
		System.out.println(" - squared-error loss: " + squaredError);
		System.out.println("--------------------------------------");
	}

	public static void runCVExperiment(Classifier classifier, String dataSetPath,
			int folds, double partOfDataSet, long seed) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(dataSetPath));
		Instances dataSetTmp = new Instances(reader);
		dataSetTmp.randomize(new Random(seed));
		Instances dataSet = new Instances(dataSetTmp, 0, (int)((double)dataSetTmp.numInstances() * partOfDataSet));
		reader.close();
		
		if (dataSet.classIndex() == -1) dataSet.setClassIndex(dataSet.numAttributes() - 1);
		
		System.out.println("Data loaded. Data set size: " + dataSet.numInstances());
	
		double loss01 = 0;
		double squaredError = 0;
		for (int n = 0; n < folds; n++) {
			System.out.println("Cross-Validation: fold " + (n+1) + "/" + folds);
			
			Instances train = dataSet.trainCV(folds, n);
			Instances test = dataSet.testCV(folds, n);
			
			Classifier classifierCopy = Classifier.makeCopy(classifier);
			classifierCopy.buildClassifier(train);
			
			double _loss01 = 0;
			double _squaredError = 0;
			for(int i = 0; i < test.numInstances(); i++) {
				Instance instance = test.instance(i);
				
				int truth = (int) instance.classValue();
				double[] distribution = classifierCopy.distributionForInstance(instance);
				int prediction = distribution[1] >= distribution[0] ? 1 : 0;

				_loss01 += truth == prediction ? 0 : 1;
				_squaredError += Math.pow(1.0 - distribution[truth], 2);
			}
			
			_loss01 /= (double)test.numInstances();
			_squaredError /= (double)test.numInstances();
			
			loss01 += _loss01;
			squaredError += _squaredError;
		}
		
		loss01 /= (double)folds;
		squaredError /= (double)folds;
		
		System.out.println("CV evaluation for " + classifier.getClass().getSimpleName());
		System.out.println(" - 0/1 loss:           " + loss01);
		System.out.println(" - squared-error loss: " + squaredError);
		System.out.println("--------------------------------------");
	}
	
	

	public static void main(String[] args) throws Exception {
		String dataSetPath = "data/patients.arff";//"output/patients alph10.arff";
		runCVExperiment(new NgramClassifier(3), dataSetPath, 4, 0.9, 1000);		
	}

}