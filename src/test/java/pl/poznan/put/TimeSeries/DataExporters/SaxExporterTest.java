package pl.poznan.put.TimeSeries.DataExporters;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.DataProcessors.PeriodicNgramCounter;
import pl.poznan.put.TimeSeries.Model.CalculatedRecord;
import pl.poznan.put.TimeSeries.Util.DataDivider;

public class SaxExporterTest {

	private List<CalculatedRecord> calculatedRecords;
	private int divisionPartsAmount = 3;
	private int windowLen = 2;
	private double destinationClass = 1.0;

	private void createTestInstance() {
		calculatedRecords = new ArrayList<CalculatedRecord>();

		List<String> saxStrings = Arrays.asList("abaabbbbaabb", "abbabaaaabba",
				"aaababbbaaaa");
		for (String saxString : saxStrings) {
			ArrayList<HashMap<String, Integer>> periodicallyCountedNgrams = new ArrayList<HashMap<String, Integer>>();

			List<String> dividedSax = DataDivider.divideStringRegularly(
					saxString, divisionPartsAmount);

			for (String elem : dividedSax) {
				HashMap<String, Integer> ngramCountMap = PeriodicNgramCounter
						.slashStringAndCountNgrams(elem, windowLen);
				periodicallyCountedNgrams.add(ngramCountMap);
			}

			CalculatedRecord calcRecord = new CalculatedRecord(
					periodicallyCountedNgrams, destinationClass++);
			calculatedRecords.add(calcRecord);
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void nonDominantTest() {
		createTestInstance();
		String arffPath = "testArff";
		boolean doAttributesCut = false;

		ArffExporterBase builder = new CountedSaxArffBuilder(calculatedRecords);

		builder.buildInstances();
		builder.saveArff(arffPath);

		// TODO: check if file ok

		File f = new File(arffPath);
		f.delete();
	}

	@Test
	public void DominantTest() {
		createTestInstance();
		String arffPath = "testArff";
		String patternPath = "testData/proper dominant output.txt";

		ArffExporterBase builder = new DominantArffBuilder(calculatedRecords);

		try {
			builder.buildInstances();
		} catch (Exception e) {
			e.printStackTrace();
		}
		builder.saveArff(arffPath);

		boolean result = checkIfFilesContentIsTheSame(arffPath, patternPath);

		File f = new File(arffPath);
		f.delete();

		assertTrue(result);
	}

	private boolean checkIfFilesContentIsTheSame(String pathA, String pathB) {
		String contentA = "a";
		String contentB = "b";
		try {
			contentA = new String(Files.readAllBytes(Paths.get(pathA)));
			contentB = new String(Files.readAllBytes(Paths.get(pathB)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		contentA = contentA.replaceAll("\r\n", "\n");
		contentB = contentB.replaceAll("\r\n", "\n");

		return contentA.equals(contentB);
	}

}
