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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.poznan.put.TimeSeries.DataExporters.DeprecatedDivisionArffExporter;
import pl.poznan.put.TimeSeries.DataExporters.RegressionArffExporter;
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
import pl.poznan.put.TimeSeries.Workflows.PatientRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;
import weka.experiment.AveragingResultProducer;

public class Test {	
	
	public static void main(String[] args) throws Exception {
		
	}

	private static void test() throws Exception {
		PureDataImporter imp = new PureDataImporter(
				Configuration.getProperty("pureDataSet"));
		List<Patient> pacjenci = imp.ImportData();

		boolean isSick = false;
		List<PatientGroup> groups = DataDivider.divideData(pacjenci, isSick);

		List<UnifiedRecordType> records = PatientGroupConverter
				.RegressionConversion(groups);
		DeprecatedDivisionArffExporter exporter = new DeprecatedDivisionArffExporter("PureData");
		exporter.saveUnifiedRecordsToArffData(records,
				Configuration.getProperty("pureArffOutput")
						+ "pureArffTest.arff");

		System.out.println("Koniec");
	}
}
