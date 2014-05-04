package pl.poznan.put.TimeSeries.Classifying;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class FirstClassifier extends Classifier {

	private static final long serialVersionUID = 8495869910146343292L;

	private int ngramSize;
	private List<HashMap<String, Integer>> ngramsInClasses;
	private Attribute saxAttribute = null;
	private int numClasses;

	public FirstClassifier(int ngramSize) {
		this.ngramSize = ngramSize;
	}

	@Override
	public void buildClassifier(Instances data) throws Exception {
		data.deleteWithMissingClass();

		numClasses = data.numClasses();
		ngramsInClasses = new ArrayList<HashMap<String, Integer>>();

		for (int i = 0; i < numClasses; i++) {
			ngramsInClasses.add(new HashMap<String, Integer>());
		}

		for (int i = 0; i < data.numAttributes() - 1; i++) {
			if (data.attribute(i).name().equals("saxString")) {
				saxAttribute = data.attribute(i);
			}
		}

		for (int i = 0; i < data.numInstances(); i++) {
			Instance instance = data.instance(i);
			int classIndex = (int) instance.classIndex();

			List<String> ngrams = ngramCreator(instance
					.stringValue(saxAttribute));

			for (String ngram : ngrams) {
				HashMap<String, Integer> currentMap = ngramsInClasses
						.get(classIndex);
				if (!currentMap.containsKey(ngram)) {
					currentMap.put(ngram, 1);
				} else {
					currentMap.put(ngram, currentMap.get(ngram) + 1);
				}
			}
		}
	}

	private HashMap<String, Integer> CountNgrams(List<String> ngrams) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (String ngram : ngrams) {
			if (!map.containsKey(ngram)) {
				map.put(ngram, 1);
			} else {
				map.put(ngram, map.get(ngram) + 1);
			}
		}
		return map;
	}

	@Override
	public double classifyInstance(Instance instance) throws Exception {

		List<String> patientNgrams = ngramCreator(instance
				.stringValue(saxAttribute));
		
		HashMap<String, Integer> patientCountedNgrams = CountNgrams(patientNgrams);
		
		double[] distances = new double[numClasses];

		for (String ngram : patientNgrams) {
			
			for (int i = 0; i < numClasses; i++) {
				double patternValue=0;
				if(ngramsInClasses.get(i).containsKey(ngram)){
					patternValue = ngramsInClasses.get(i).get(ngram);
				}
				double currNgramDist = patternValue - patientCountedNgrams.get(ngram);
				distances[i] += Math.pow(currNgramDist, 2);
			}
		}
		
		for(int i = 0; i < numClasses; i++){
			distances[i]= Math.sqrt(distances[i]);
		}
		
		double minDist = Double.MAX_VALUE;
		int bestClassIndex = -1;
		for(int i = 0; i < numClasses; i++){
			if(distances[i]<minDist){
				minDist = distances[i];
				bestClassIndex = i;
			}
		}
		return bestClassIndex;
	}

	public List<String> ngramCreator(String stringToDivide) {
		List<String> ngrams = new ArrayList<String>();

		for (int i = 0; i < stringToDivide.length() - ngramSize + 1; i++) {
			ngrams.add(stringToDivide.substring(i, i + ngramSize));
		}

		return ngrams;
	}

}
