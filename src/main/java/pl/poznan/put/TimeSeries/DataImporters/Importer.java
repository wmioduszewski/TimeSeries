package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Config;

public class Importer {
	
	public static List<? extends IRecord> importData(boolean glaucoma) throws Exception{
		if(glaucoma)
			return importPatients();
		else 
			return importEamonnData();
	}
	
	private static List<? extends IRecord> importEamonnData() throws Exception {
		List<? extends IRecord> records = null;
		DataImporterEamonn importer = new DataImporterEamonn(Config
				.getInstance().getSingleDataPath());
		try {
			records = importer.importEamonnData();
		} catch (Exception e) {
			System.out.println("Eamonn data import failed.");
			e.printStackTrace();
			throw e;
		}
		return records;
	}
	
	private static List<? extends IRecord> importPatients(){
		List<? extends IRecord> records = null;
		String pureDataPath = Config.getInstance().getGlaucomaDataSet();
		List<Patient> purePatients = null;
		PatientDataImporterBase importer;
		try {
			importer = new PatientDataImporter2(pureDataPath);
			purePatients = importer.importData();
		} catch (IOException e) {
			System.out.println("Patients import failed.");
			e.printStackTrace();
		}
		records = purePatients;
		return records;
	}

}
