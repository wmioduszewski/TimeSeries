package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;

/**
 * List with ascending order by value attribute Holds only limited best objects
 * (limit passed in constructor)
 */
public class AscendingList {

	private int limit;

	private ArrayList<KnnPair> pairs;

	public AscendingList(int limit) {
		super();
		this.limit = limit;
		pairs = new ArrayList<KnnPair>();
	}

	public void add(KnnPair pair) {
		if (pairs.size() >= limit) {
			KnnPair lastElem = pairs.get(pairs.size() - 1);
			if (pair.compareTo(lastElem) < 0) {
				pairs.remove(lastElem);
				sortAdd(pair);
			}
		} else {
			sortAdd(pair);
		}
	}

	public ArrayList<KnnPair> getList() {
		return pairs;
	}

	private void sortAdd(KnnPair pair) {
		int i = 0;
		if (pairs.size() > 0) {
			while (pair.compareTo(pairs.get(i)) >= 0) {
				if (++i >= pairs.size())
					break;
			}
		}
		pairs.add(i, pair);
	}

}
