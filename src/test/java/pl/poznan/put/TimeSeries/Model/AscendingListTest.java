package pl.poznan.put.TimeSeries.Model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class AscendingListTest {

	@Test
	public void oneElementTest() {
		AscendingList list = new AscendingList(3);
		list.add(new KnnPair(0, 1.2));

		ArrayList<KnnPair> expected = new ArrayList<KnnPair>();
		expected.add(new KnnPair(0, 1.2));
		ArrayList<KnnPair> actual = list.getList();
		assertEquals(expected, actual);
	}

	@Test
	public void nElementUnsortedTest() {
		AscendingList list = new AscendingList(5);
		list.add(new KnnPair(0, 5.2));
		list.add(new KnnPair(1, 3.2));
		list.add(new KnnPair(2, 2.2));
		list.add(new KnnPair(3, -1.2));
		list.add(new KnnPair(4, -1.21));

		ArrayList<KnnPair> expected = new ArrayList<KnnPair>();
		expected.add(new KnnPair(0, -1.21));
		expected.add(new KnnPair(1, -1.2));
		expected.add(new KnnPair(2, 2.2));
		expected.add(new KnnPair(3, 3.2));
		expected.add(new KnnPair(4, 5.2));

		ArrayList<KnnPair> actual = list.getList();
		assertEquals(expected, actual);
	}

	@Test
	public void comparisionTest() {
		KnnPair p1 = new KnnPair(0, 1.1);
		KnnPair p2 = new KnnPair(1, 1.2);
		int res = p1.compareTo(p2);
		assertEquals(res, -1);

		p1 = new KnnPair(0, 1.00000000001);
		p2 = new KnnPair(0, 1.00000000002);
		res = p1.compareTo(p2);
		assertEquals(res, 0);

		p1 = new KnnPair(0, 0.1);
		p2 = new KnnPair(1, 0.1);
		res = p1.compareTo(p2);
		assertEquals(res, 0);

		p1 = new KnnPair(0, 7.6);
		p2 = new KnnPair(1, 6.5);
		res = p1.compareTo(p2);
		assertEquals(res, 1);
	}

	@Test
	public void overNElementUnsortedTest() {
		AscendingList list = new AscendingList(2);
		list.add(new KnnPair(3, -1.2));
		list.add(new KnnPair(0, 5.2));
		list.add(new KnnPair(1, 3.2));
		list.add(new KnnPair(4, -1.21));
		list.add(new KnnPair(2, 2.2));

		ArrayList<KnnPair> expected = new ArrayList<KnnPair>();
		expected.add(new KnnPair(0, -1.21));
		expected.add(new KnnPair(0, -1.2));

		ArrayList<KnnPair> actual = list.getList();
		assertEquals(expected, actual);
	}

}
