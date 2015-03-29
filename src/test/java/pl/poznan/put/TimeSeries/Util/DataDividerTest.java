package pl.poznan.put.TimeSeries.Util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataDividerTest {
	@Test
	public void moduloSafeStringLength() {
		List<String> res = DataDivider.divideStringRegularly("abcdefghi", 3);
		List<String> exp = Arrays.asList("abc","def","ghi");
		assertEquals(exp, res);
		
		res = DataDivider.divideStringRegularly("abc", 3);
		exp = Arrays.asList("a","b","c");
		assertEquals(exp, res);
		
		res = DataDivider.divideStringRegularly("abc", 1);
		exp = Arrays.asList("abc");
	}
	
	@Test
	public void moduloUnsafeStringLength(){
		List<String> res = DataDivider.divideStringRegularly("abcdefgh", 3);
		List<String> exp = Arrays.asList("abc","def","gh");
		assertEquals(exp, res);
		
		res = DataDivider.divideStringRegularly("abc", 4);
		exp = Arrays.asList("a","b","c");
		assertEquals(exp, res);
		
		res = DataDivider.divideStringRegularly("abcdefghijk", 2);
		exp = Arrays.asList("abcdef","ghijk");
		assertEquals(exp, res);
	}
	
	@Test
	public void moduloSafeCollectionDivision(){
		List<String> inputList = Arrays.asList("a","b","c","d","e","f");
		List<List<String>> expectedList = Arrays.asList(
				Arrays.asList("a","b"),
				Arrays.asList("c","d"),
				Arrays.asList("e","f"));
		List<List<String>> res = DataDivider.divideCollectionRegularly(inputList, 3);
		assertEquals(expectedList, res);
		
		inputList = Arrays.asList("a","b","c","d","e","f");
		expectedList = Arrays.asList(
				Arrays.asList("a"),
				Arrays.asList("b"),
				Arrays.asList("c"),
				Arrays.asList("d"),
				Arrays.asList("e"),
				Arrays.asList("f"));
		res = DataDivider.divideCollectionRegularly(inputList, 6);
		assertEquals(expectedList, res);
		
		inputList = Arrays.asList("a","b","c","d","e","f");
		expectedList = Arrays.asList(
				Arrays.asList("a","b","c","d","e","f"));
		res = DataDivider.divideCollectionRegularly(inputList, 1);
		assertEquals(expectedList, res);
	}
	
	@Test
	public void moduloUnsafeCollectionDivision(){
		List<String> inputList = Arrays.asList("a","b","c","d","e");
		List<List<String>> expectedList = Arrays.asList(
				Arrays.asList("a","b"),
				Arrays.asList("c","d"),
				Arrays.asList("e"));
		List<List<String>> res = DataDivider.divideCollectionRegularly(inputList, 3);
		assertEquals(expectedList, res);
		
		inputList = Arrays.asList("a","b","c","d","e","f");
		expectedList = Arrays.asList(
				Arrays.asList("a"),
				Arrays.asList("b"),
				Arrays.asList("c"),
				Arrays.asList("d"),
				Arrays.asList("e"),
				Arrays.asList("f"));
		res = DataDivider.divideCollectionRegularly(inputList, 7);
		assertEquals(expectedList, res);
		
		inputList = Arrays.asList("a","b","c","d","e","f");
		expectedList = Arrays.asList(
				Arrays.asList("a","b"),
				Arrays.asList("c","d"),
				Arrays.asList("e"),
				Arrays.asList("f"));
		res = DataDivider.divideCollectionRegularly(inputList, 4);
		assertEquals(expectedList, res);
	}
}
