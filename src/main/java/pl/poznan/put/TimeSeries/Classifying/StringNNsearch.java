package pl.poznan.put.TimeSeries.Classifying;

import java.util.List;

import pl.poznan.put.TimeSeries.Model.AscendingList;
import pl.poznan.put.TimeSeries.Model.KnnPair;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.NearestNeighbourSearch;

public class StringNNsearch extends NearestNeighbourSearch {

	private double[] distances;
	private int indexOfSaxAttr = 0;

	@Override
	public String getRevision() {
		return null;
	}

	@Override
	public Instance nearestNeighbour(Instance target) throws Exception {
		Instances instances = kNearestNeighbours(target, 1);
		return instances.firstInstance();
	}

	@Override
	public Instances kNearestNeighbours(Instance target, int k)
			throws Exception {
		AscendingList list = new AscendingList(k);

		for (int i = 0; i < m_Instances.numInstances(); i++) {
			Instance current = m_Instances.instance(i);
			distances[i] = getDistance(target.stringValue(indexOfSaxAttr),
					current.stringValue(indexOfSaxAttr));
			
			if (!target.equals(current))
				list.add(new KnnPair(i, distances[i]));
		}

		Instances kNearestNeighbors = new Instances(m_Instances, k);
		
		List<KnnPair> neighbors = list.getList();
		for(int i=0;i<k;i++){
			int index = neighbors.get(i).getIndex(); 
			kNearestNeighbors.add(m_Instances.instance(index));
		}
		
		return kNearestNeighbors;
	}

	private static double getDistance(String a, String b) {
		String longer, shorter;
		if (a.length() >= b.length()) {
			longer = a;
			shorter = b;
		} else {
			longer = b;
			shorter = a;
		}

		int limit = shorter.length();
		double distance = 0;

		for (int i = 0; i < limit; i++) {
			distance += Math.abs(longer.charAt(i) - shorter.charAt(i));
		}

		for (int i = limit; i < longer.length(); i++) {
			distance += longer.charAt(i) - 'a' + 1;
		}
		return distance;
	}

	@Override
	public double[] getDistances() throws Exception {
		return distances;
	}

	@Override
	public void update(Instance ins) throws Exception {
		// TODO Auto-generated method stub

	}

}
