package pl.poznan.put.TimeSeries.DataImporters;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.Patient;

public class Importer {
	
	public static List<? extends IRecord> importEamonnData(String inputPath) throws Exception {
		List<? extends IRecord> records = null;
		DataImporterEamonn importer = new DataImporterEamonn(inputPath);
		try {
			records = importer.importData();
		} catch (Exception e) {
			System.out.println("Eamonn data import failed.");
			e.printStackTrace();
		}
		return records;
	}
	
	public static List<? extends IRecord> importPatients(String inputPath){
		List<? extends IRecord> records = null;
		List<Patient> patients = null;
		PatientDataImporterBase importer;
		try {
			importer = new PatientDataImporter(inputPath);
			patients = importer.importData();
		} catch (IOException e) {
			System.out.println("Patients import failed.");
			e.printStackTrace();
		}
		records = patients;
		return records;
	}

}
