package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PeriodicNgramCounter {

	public static HashMap<String, AtomicInteger> slashStringAndCountNgrams(String input,
			int windowLen) {
		HashMap<String, AtomicInteger> res = new HashMap<String, AtomicInteger>();
		StringBuilder sb = new StringBuilder();
		sb.append(input.substring(0, windowLen));
		res.put(sb.toString(), new AtomicInteger(1));

		for (int i = windowLen; i < input.length(); i++) {
			sb.deleteCharAt(0);
			sb.append(input.charAt(i));
			String currNgram = sb.toString();

			if (res.containsKey(currNgram)) {
				res.get(currNgram).incrementAndGet();
			} else {
				res.put(currNgram, new AtomicInteger(1));
			}
		}
		return res;
	}
	
	public static HashMap<String, AtomicInteger> slashStringAndCountDominatingNgrams(String input,
			int windowLen) {

		HashMap<String, AtomicInteger> res = new HashMap<String, AtomicInteger>();
		
		
		
		return res;
	}
}
