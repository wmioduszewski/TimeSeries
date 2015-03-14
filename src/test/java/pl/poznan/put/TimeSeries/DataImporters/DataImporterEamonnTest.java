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

public class DataImporterEamonnTest {

	List<EamonnRecord> records = null ;
	@Before
	public void setUp() throws Exception {
		DataImporterEamonn importer = new DataImporterEamonn("testData");		
		try {
			records = importer.importEamonnData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void anythingWasRead() {
		assertTrue(records.size()>0);
	}
	
	@Test
	public void saxWasApplied(){
		for (EamonnRecord eamonnRecord : records) {
			assertNotNull(eamonnRecord.getSaxString());			
		}
	}
	
	@Test
	public void testNieprzechodzący(){
		fail("You shall not pass!");
	}

}
