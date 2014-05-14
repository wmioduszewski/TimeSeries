package pl.poznan.put.TimeSeries.Sax;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.poznan.put.TimeSeries.Model.UnifiedRecordType;

public class GeneralCase {

	@Test
	public void test() throws Exception {
		double vals[] = new double[]{2,3,4};
		UnifiedRecordType unif = new UnifiedRecordType(1, vals);
		String output = SaxPerformer.TranslateUnifiedRecordToString(unif, vals.length, 10);
		
		System.out.println(output);
	}

}
