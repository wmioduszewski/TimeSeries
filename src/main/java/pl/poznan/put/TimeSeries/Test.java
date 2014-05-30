package pl.poznan.put.TimeSeries;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.TimeSeries.DataOperators.PureDataImporter;
import pl.poznan.put.TimeSeries.Reporting.ResultReporter;

public class Test {

	public static void main(String[] args) throws Exception {
		test();
	}
	
	
	private static void test() throws IOException{
		PureDataImporter imp = new PureDataImporter("C:/Users/Wojciech/Documents/studia/mgr/praca mgr/stationary data/dataset3/dane20140519/dane20140519/");
		imp.ImportData();
	}
}
