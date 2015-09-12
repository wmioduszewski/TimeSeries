package pl.poznan.put.TimeSeries.DataExporters;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Util.Config;
import pl.poznan.put.TimeSeries.Util.DataDivider;

public class SaxExporterTest {

	private int divisionPartsAmount = 3;
	private int windowLen = 2;
	private double destinationClass = 1.0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Config.getInstance().setDivisionPartsAmount(3);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private List<CalculatedRecord> createTestInstance() {
		List<CalculatedRecord> calculatedRecords = new ArrayList<CalculatedRecord>();

		List<String> saxStrings = Arrays.asList("abaabbbbaabb", "abbabaaaabba", "aaababbbaaaa");
		for (String saxString : saxStrings) {
			ArrayList<HashMap<String, Integer>> periodicallyCountedNgrams = new ArrayList<HashMap<String, Integer>>();

			List<String> dividedSax = DataDivider.divideStringRegularly(saxString, divisionPartsAmount);

			for (String elem : dividedSax) {
				HashMap<String, Integer> ngramCountMap = PeriodicNgramCounter.slashStringAndCountNgrams(elem,
						windowLen);
				periodicallyCountedNgrams.add(ngramCountMap);
			}

			CalculatedRecord calcRecord = new CalculatedRecord(periodicallyCountedNgrams, destinationClass++);
			calculatedRecords.add(calcRecord);
		}
		return calculatedRecords;
	}

	@Test
	public void countedTest() {
		String exportPath = "testArff";
		String patternPath = "testData/proper counted output.txt";
		SaxArffExporterBase exporter = new CountedSaxArffBuilder(createTestInstance());
		boolean result = CommonCase.isExporterCorrect(exporter, patternPath, exportPath);
		assertTrue(result);
	}

	@Test
	public void dominantTest() {
		String exportPath = "testArff";
		String patternPath = "testData/proper dominant output.txt";
		SaxArffExporterBase exporter = new DominantArffBuilder(createTestInstance());
		boolean result = CommonCase.isExporterCorrect(exporter, patternPath, exportPath);
		assertTrue(result);
	}

}
