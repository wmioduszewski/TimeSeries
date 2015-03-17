package pl.poznan.put.TimeSeries.DataImporters;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public class DataImporterEamonnTest {

	List<EamonnRecord> records = null ;
	@Before
	public void setUp() throws Exception {
		
		CommonConfig cfg = CommonConfig.getInstance();
		cfg.setSaxAlphabeatSize(3);
		cfg.setSaxOutputLength(7);
		cfg = CommonConfig.getInstance();
		
		DataImporterEamonn importer = new DataImporterEamonn("testData");		
		try {
			records = importer.importEamonnData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Test
	public void saxWasApplied(){
		assertTrue(records.size()>0);
		for (EamonnRecord eamonnRecord : records) {
			assertNotNull(eamonnRecord.getSaxString());			
		}
	}
	
	@Test
	public void saxWasCorrect(){
		String sax0 = records.get(0).getSaxString();
		String sax1 = records.get(1).getSaxString();
		String sax2 = records.get(2).getSaxString();
		
		assertEquals(sax0, "bcbaaab");
		assertEquals(sax1, "ccccbba");
		assertEquals(sax1, "cccaaca");
		//todo: dodać implementację i test na maksimum lokalne i globalne
	}
}

