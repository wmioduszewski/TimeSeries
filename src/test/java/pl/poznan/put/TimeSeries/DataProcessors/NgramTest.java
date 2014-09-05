package pl.poznan.put.TimeSeries.DataProcessors;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class NgramTest {

	@Test
	public void test() {
		HashMap<String, AtomicInteger> res = NgramProcessor.slashString("abracadabra", 3);

		for (Entry<String,AtomicInteger> elem : res.entrySet()) {
			System.out.println(elem.getKey() + " " + elem.getValue());
		}
	}

}
