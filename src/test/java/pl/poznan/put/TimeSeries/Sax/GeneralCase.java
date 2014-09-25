package pl.poznan.put.TimeSeries.Sax;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public class GeneralCase {

	@Test
	public void test() throws Exception {
		List<Float> vals = Arrays.asList(2f,3f,4f);
		EamonnRecord unif = new EamonnRecord(1, vals);
		String output = SaxPerformer.TranslateUnifiedRecordToString(unif, vals.size(), 10);
		
		System.out.println(output);
	}

}
