package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.poznan.put.TimeSeries.Util.DataDivider;

public class DividingDataTest {

	@Test
	public void test() {
		
		List<Integer> list = Arrays.asList(1,4,3,4,6,5);
		
		List<List<Integer>> res = DataDivider.divideCollectionRegularly(list,2);
		res.iterator();
	}

}
