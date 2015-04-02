package pl.poznan.put.TimeSeries.DataProcessors;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class PeriodicNgramCounterTest {

	@Test
	public void basicTest() {
		HashMap<String, Integer> res = PeriodicNgramCounter.slashStringAndCountNgrams(
				"abracadabra", 3);
		
		HashMap<String,Integer> exp = new HashMap<String,Integer>();
		exp.put("abr", 2);
		exp.put("bra", 2);
		exp.put("dab", 1);
		exp.put("rac", 1);
		exp.put("cad", 1);
		exp.put("aca", 1);
		exp.put("ada", 1);
		
		assertEquals(exp,res);
	}
	
	@Test
	public void onesTest() {
		HashMap<String, Integer> res = PeriodicNgramCounter.slashStringAndCountNgrams(
				"abc", 1);
		HashMap<String,Integer> exp = new HashMap<String,Integer>();
		exp.put("a", 1);
		exp.put("b", 1);
		exp.put("c", 1);	
		
		assertEquals(exp,res);
	}
	
	@Test
	public void countingTest(){
		HashMap<String, Integer> res = PeriodicNgramCounter.slashStringAndCountNgrams(
				"aaaaaaaaaaaa", 2);
		HashMap<String,Integer> exp = new HashMap<String,Integer>();
		exp.put("aa", 11);
		assertEquals(exp,res);
	}
	
	public void repetitiveTest(){
		HashMap<String, Integer> res = PeriodicNgramCounter.slashStringAndCountNgrams(
				"abcabcabcabc", 3);
		HashMap<String,Integer> exp = new HashMap<String,Integer>();
		exp.put("abc", 4);
		exp.put("bca", 3);
		exp.put("cab", 3);
		assertEquals(exp,res);
	}

}
