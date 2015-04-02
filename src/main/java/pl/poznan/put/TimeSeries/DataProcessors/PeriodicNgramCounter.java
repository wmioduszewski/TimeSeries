package pl.poznan.put.TimeSeries.DataProcessors;


import java.util.HashMap;

public class PeriodicNgramCounter {

	public static HashMap<String, Integer> slashStringAndCountNgrams(String input,
			int windowLen) {
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		StringBuilder sb = new StringBuilder();
		sb.append(input.substring(0, windowLen));
		res.put(sb.toString(), 1);

		for (int i = windowLen; i < input.length(); i++) {
			sb.deleteCharAt(0);
			sb.append(input.charAt(i));
			String currNgram = sb.toString();

			if (res.containsKey(currNgram)) {
				res.put(currNgram,res.get(currNgram)+1);
			} else {
				res.put(currNgram, 1);
			}
		}
		return res;
	}
}
