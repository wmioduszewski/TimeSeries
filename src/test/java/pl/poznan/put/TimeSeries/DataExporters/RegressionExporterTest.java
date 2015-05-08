package pl.poznan.put.TimeSeries.DataExporters;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.poznan.put.TimeSeries.Model.RegressionArffRow;
import pl.poznan.put.TimeSeries.Model.RegressionResult;

public class RegressionExporterTest {

	private List<RegressionArffRow> createData() {
		List<RegressionArffRow> rows = new ArrayList<RegressionArffRow>();

		List<RegressionResult> results1 = Arrays.asList(new RegressionResult(1,
				2), new RegressionResult(1.2, 0.2), new RegressionResult(0.01,
				2.22), new RegressionResult(1.333, 3.2));
		List<RegressionResult> results2 = Arrays.asList(new RegressionResult(3,
				4), new RegressionResult(1.5, 6.2), new RegressionResult(-0.01,
				-1.22), new RegressionResult(1.443, -3.25));
		List<RegressionResult> results3 = Arrays.asList(new RegressionResult(
				0.71, -7.2), new RegressionResult(17.2, 20.2),
				new RegressionResult(-0.01, 2.322), new RegressionResult(
						1.3353, 3.32));

		rows.add(new RegressionArffRow(results1, 0));
		rows.add(new RegressionArffRow(results2, 1));
		rows.add(new RegressionArffRow(results3, 2));

		return rows;
	}

	@Test
	public void test() {
		String exportPath = "testRegression";
		String patternPath = "testData/proper regression output.txt";
		ArffExporterBase exporter = new RegressionArffBuilder(createData());
		boolean result = CommonCase.isExporterCorrect(exporter, patternPath,
				exportPath);
		assertTrue(result);
	}

}
