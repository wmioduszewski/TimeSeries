package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;

public class StringDominance {

	public static boolean isStringGreaterOrEqualTo(String base,
			String examinedString) {
		if (base.length() != examinedString.length())
			throw new IllegalArgumentException("Strings lengths differ");

		for (int i = 0; i < base.length(); i++) {
			if (base.charAt(i) > examinedString.charAt(i))
				return false;
		}
		return true;
	}

	public static boolean isStringLessOrEqualTo(String base,
			String examinedString) {
		if (base.length() != examinedString.length())
			throw new IllegalArgumentException("Strings lengths differ");

		for (int i = 0; i < base.length(); i++) {
			if (base.charAt(i) < examinedString.charAt(i))
				return false;
		}
		return true;
	}

	public static List<String> getListOfLessOrEqualStrings(String base,
			List<String> list) {
		List<String> res = new ArrayList<String>();
		for (String elem : list) {
			if (isStringLessOrEqualTo(base, elem))
				res.add(elem);
		}
		return res;
	}

	public static List<String> getListOfGreaterOrEqualStrings(String base,
			List<String> list) {
		List<String> res = new ArrayList<String>();
		for (String elem : list) {
			if (isStringGreaterOrEqualTo(base, elem))
				res.add(elem);
		}
		return res;
	}

	public static void eraseMinString(List<String> input) {
		String min = input.get(0);
		for (String elem : input) {
			if (isStringLessOrEqualTo(min, elem))
				min = elem;
		}
		input.remove(min);
	}

	public static void eraseMaxString(List<String> input) {
		String max = input.get(0);
		for (String elem : input) {
			if (isStringGreaterOrEqualTo(max, elem))
				max = elem;
		}
		input.remove(max);
	}

}
