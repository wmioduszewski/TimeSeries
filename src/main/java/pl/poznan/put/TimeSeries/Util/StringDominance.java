package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;

public class StringDominance {

	public static boolean isStringGreaterOrEqualTo(String base,
			String examinedString) {
		if (base.length() != examinedString.length())
			throw new IllegalArgumentException("Strings lengths differ");

		for (int i = 0; i < base.length(); i++) {
			if(base.charAt(i) > examinedString.charAt(i))
				return false;
		}
		return true;
	}
	
	public static boolean isStringLessOrEqualTo(String base,
			String examinedString) {
		if (base.length() != examinedString.length())
			throw new IllegalArgumentException("Strings lengths differ");
		
		for (int i = 0; i < base.length(); i++) {
			if(base.charAt(i) < examinedString.charAt(i))
				return false;
		}
	
		return true;
	}
	
	public static List<String> getListOfLessOrEqualStrings(String base, List<String> list){
		List<String> res = new ArrayList<String>();
		String min = list.get(0);
		for (String elem : list) {
			if(isStringLessOrEqualTo(min,elem))
				min = elem;
			if(isStringLessOrEqualTo(base, elem))
				res.add(elem);
		}
		
		//erase the lowest attr 
		//res.remove(min);
		
		return res;
	}
	
	public static List<String> getListOfGreaterOrEqualStrings(String base, List<String> list){
		List<String> res = new ArrayList<String>();
		
		String max = list.get(0);
		for (String elem : list) {
			if(isStringGreaterOrEqualTo(max, elem))
				max = elem;
			if(isStringGreaterOrEqualTo(base, elem))
				res.add(elem);
		}

		//erase the greatest attr
		//res.remove(max);
		
		return res;
	}

}
