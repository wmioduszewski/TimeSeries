package pl.poznan.put.TimeSeries.Classifying;

import java.util.List;

import com.fastdtw.dtw.FastDTW;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.timeseries.TimeSeriesBase.Builder;
import com.fastdtw.util.Distances;

import pl.poznan.put.TimeSeries.Model.AscendingList;
import pl.poznan.put.TimeSeries.Model.KnnPair;
import pl.poznan.put.TimeSeries.Util.CommonConfig;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.NearestNeighbourSearch;

public class DtwSearch extends NearestNeighbourSearch {

	private double[] distances;

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
		distances = new double[m_Instances.numInstances()];
		AscendingList list = new AscendingList(k);

		for (int i = 0; i < m_Instances.numInstances(); i++) {
			Instance current = m_Instances.instance(i);
			distances[i] = getDistance(current, target);

			if (!target.equals(current))
				list.add(new KnnPair(i, distances[i]));
		}

		Instances kNearestNeighbors = new Instances(m_Instances, k);

		List<KnnPair> neighbors = list.getList();
		for (int i = 0; i < k; i++) {
			int index = neighbors.get(i).getIndex();
			kNearestNeighbors.add(m_Instances.instance(index));
		}

		return kNearestNeighbors;
	}

	private static double getDistance(Instance current, Instance target) {
		Builder builder1 = TimeSeriesBase.builder();
		Builder builder2 = TimeSeriesBase.builder();

		for (int i = 0; i < current.numAttributes(); i++) {
			if (i != current.classIndex()) {
				builder1.add(i, current.value(i));
			}
		}

		for (int i = 0; i < target.numAttributes(); i++) {
			if (i != target.classIndex()) {
				builder2.add(i, target.value(i));
			}
		}

		TimeSeries ts1 = builder1.build();
		TimeSeries ts2 = builder2.build();
		int radius = CommonConfig.getInstance().getDtwSearchRadius();
		double distance = FastDTW.compare(ts1, ts2, radius,
				Distances.EUCLIDEAN_DISTANCE).getDistance();
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
