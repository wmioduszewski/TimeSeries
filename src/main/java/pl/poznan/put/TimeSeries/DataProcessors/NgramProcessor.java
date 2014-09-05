package pl.poznan.put.TimeSeries.DataProcessors;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NgramProcessor {

	private static LinkedList<String> ngrams;

	public static HashMap<String, AtomicInteger> slashString(String input, int windowLen) {
		HashMap<String, AtomicInteger> res = new HashMap<String, AtomicInteger>();
		StringBuilder sb = new StringBuilder();
		sb.append(input.substring(0, windowLen));
		res.put(sb.toString(), new AtomicInteger(1));

		for (int i = windowLen; i < input.length(); i++) {
			sb.deleteCharAt(0);
			sb.append(input.charAt(i));
			String currNgram = sb.toString();
			
			if(res.containsKey(currNgram)){
				res.get(currNgram).incrementAndGet();
			}else{
				res.put(currNgram, new AtomicInteger(1));
			}
		}
		return res;
	}

}
