package pl.poznan.put.TimeSeries;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.TimeSeries.DataExporters.PureDataArffExporter;
import pl.poznan.put.TimeSeries.DataExporters.UnifiedArffExporter;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterBase;
import pl.poznan.put.TimeSeries.DataImporters.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataImporters.PureDataImporter;
import pl.poznan.put.TimeSeries.DataProcessors.DataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.PatientDataDivider;
import pl.poznan.put.TimeSeries.DataProcessors.PatientGroupConverter;
import pl.poznan.put.TimeSeries.Model.IRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.PatientGroup;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedArffRow;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Reporting.ResultReporter;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.DataAverager;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;
import weka.experiment.AveragingResultProducer;

public class Test {

	public static void main(String[] args) throws Exception {
		//List<Patient> patients = importData();

		
		IRecord rec = new Patient(3);
		if(rec instanceof Patient)
		{
			Patient p = (Patient) rec;
			p.setAge(1);
			System.out.println("Pacjent " + p.getAge());
		}
		else if(rec instanceof UnifiedRecordType)
		{
			UnifiedRecordType uni = (UnifiedRecordType) rec;
			
			System.out.println("Eamonn " + uni.getDestinationClass());
		}
		
		//divideData(patients);
	}

	private static void divideData(List<Patient> patients) throws Exception {

		PatientDataDivider patientDivider = new PatientDataDivider();
		List<UnifiedArffRow> rows = new ArrayList<UnifiedArffRow>();

		for (Patient patient : patients) {

			try {
				UnifiedArffRow arffRow = patientDivider
						.ComputeRegression(patient);
				rows.add(arffRow);
			} catch (Exception e) {
				System.out.println("patient missed");
			}
		}

		UnifiedArffExporter exporter = new UnifiedArffExporter("UnifiedData");
		exporter.saveUnifiedRecordsToArffData(rows, "testtest");
	}

	private static void test() throws Exception {
		PureDataImporter imp = new PureDataImporter(
				Configuration.getProperty("pureDataSet"));
		List<Patient> pacjenci = imp.ImportData();

		boolean isSick = false;
		List<PatientGroup> groups = DataDivider.divideData(pacjenci, isSick);

		List<UnifiedRecordType> records = PatientGroupConverter
				.RegressionConversion(groups);
		PureDataArffExporter exporter = new PureDataArffExporter("PureData");
		exporter.saveUnifiedRecordsToArffData(records,
				Configuration.getProperty("pureArffOutput")
						+ "pureArffTest.arff");

		System.out.println("Koniec");
	}

	private static List<Patient> importData() throws IOException {
		String csvDataPath = Configuration.getProperty("csvDataSet");
		String pureDataPath = Configuration.getProperty("pureDataSet");

		DataImporterBase importer ;//= new DataImporterCsv(csvDataPath);
//		List<Patient> csvPatients = importer.ImportData();
		importer = new PureDataImporter(pureDataPath);
		List<Patient> purePatients = importer.ImportData();

		List<Patient> patients = new ArrayList<Patient>();

//		patients.addAll(csvPatients);
		patients.addAll(purePatients);
		return patients;
	}
}
