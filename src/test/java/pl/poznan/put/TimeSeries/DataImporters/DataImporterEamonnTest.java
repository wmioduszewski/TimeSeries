package pl.poznan.put.TimeSeries.DataImporters;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.Config;

public class DataImporterEamonnTest {

	List<EamonnRecord> records = null;
	private static String testSetName = "testData";
	private static int oldAlphaVal;
	private static int oldOutputVal;

	private List<EamonnRecord> importRecords() {
		DataImporterEamonn importer = new DataImporterEamonn(testSetName);
		try {
			records = importer.importEamonnData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		oldAlphaVal = Config.getInstance().getSaxAlphabeatSize();
		oldOutputVal = Config.getInstance().getSaxOutputLength();
		File f = new File(String.format("%s", testSetName));
		f.mkdir();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File(String.format("%s", testSetName));
		f.delete();
		Config.getInstance().setSaxAlphabeatSize(oldAlphaVal);
		Config.getInstance().setSaxOutputLength(oldOutputVal);
	}

	private void setConfiguration(int alphabet, int output) {
		Config cfg = Config.getInstance();
		cfg.setSaxAlphabeatSize(alphabet);
		cfg.setSaxOutputLength(output);
	}

	private void writeToFile(List<String> instances) {
		File f = new File(
				String.format("%s/%s_TRAIN", testSetName, testSetName));
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f));

			for (String string : instances) {
				bw.write(string + System.getProperty("line.separator"));

			}
			bw.flush();
			bw.close();

			f = new File(String.format("%s/%s_TEST", testSetName, testSetName));
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cleanupFiles() {
		File f = new File(String.format("%s/%s_TEST", testSetName, testSetName));
		f.delete();
		f = new File(String.format("%s/%s_TRAIN", testSetName, testSetName));
		f.delete();

	}

	private String saxTestBase(int alphabet, int output, String instance) {
		setConfiguration(alphabet, output);
		List<String> list = new ArrayList<String>();
		list.add(instance);
		writeToFile(list);
		List<EamonnRecord> records = importRecords();
		cleanupFiles();
		return records.get(0).getSaxString();
	}

	@Test
	public void sax1() {
		String res = saxTestBase(3, 3, "0 1 2 3");
		assertEquals(res, "abc");
	}

	@Test
	public void sax2() {
		String res = saxTestBase(3, 7, "0 0.5 0.5 2.2 2.3 3 0 1.5");
		assertEquals(res, "aabccac");
	}

	@Test
	public void sax3() {
		String res = saxTestBase(3, 5, "0 1 1 2 0 0 1 2");
		assertEquals(res, "ababc");
	}
}
