package pl.poznan.put.TimeSeries.Classifying;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class FirstClassifier extends Classifier {

	private static final long serialVersionUID = 8495869910146343292L;

	
	
	@Override
	public void buildClassifier(Instances data) throws Exception {
		
		
	}
	
	@Override
	public double classifyInstance(Instance instance) throws Exception {
		 
		return 0.0;
	}

}
