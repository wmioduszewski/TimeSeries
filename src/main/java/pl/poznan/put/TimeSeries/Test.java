package pl.poznan.put.TimeSeries;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.TimeSeries.DataOperators.DataDivider;
import pl.poznan.put.TimeSeries.DataOperators.DataImporterBase;
import pl.poznan.put.TimeSeries.DataOperators.DataImporterCsv;
import pl.poznan.put.TimeSeries.DataOperators.PatientGroupConverter;
import pl.poznan.put.TimeSeries.DataOperators.PureDataImporter;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.PatientGroup;
import pl.poznan.put.TimeSeries.Model.RegressionResult;
import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;
import pl.poznan.put.TimeSeries.Reporting.ResultReporter;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.DataAverager;
import pl.poznan.put.TimeSeries.Util.PureDataArffExporter;
import pl.poznan.put.TimeSeries.Util.RegressionCalculator;
import weka.experiment.AveragingResultProducer;

public class Test {

	public static void main(String[] args) throws Exception {
		importData();
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
	
	private static void importData() throws IOException{
		String csvDataPath = Configuration.getProperty("csvDataSet");
		String pureDataPath = Configuration.getProperty("pureDataSet");
		
		DataImporterBase importer = new DataImporterCsv(csvDataPath);
		List<Patient> csvPatients = importer.ImportData();
		importer = new PureDataImporter(pureDataPath);
		List<Patient> purePatients = importer.ImportData();
		
		List<Patient> patients = new ArrayList<Patient>();
		
		patients.addAll(csvPatients);
		patients.addAll(purePatients);
	}
}
