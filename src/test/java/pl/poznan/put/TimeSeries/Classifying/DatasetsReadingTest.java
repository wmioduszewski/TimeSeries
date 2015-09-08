package pl.poznan.put.TimeSeries.Classifying;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DatasetsReadingTest {

	@Test
	public void readDatasetsEnum() {
		assertTrue(Datasets.values()[0].name().equals("ECG200"));
		assertTrue(Datasets.values()[1].name().equals("ECGFIVEDAYS"));
		assertTrue(Datasets.values()[2].name().equals("SAMPLEUNITTEST"));
		assertTrue(Datasets.values()[3].name().equals("TWOLEADECG"));
		assertTrue(Datasets.values()[4].name().equals("YOGA"));
		assertTrue(Datasets.values()[5].name().equals("MOTESTRAIN"));
		assertTrue(Datasets.values()[6].name().equals("ITALYPOWERDEMAND"));
		assertTrue(Datasets.values()[7].name().equals("CHLORINECONCENTRATION"));
		assertTrue(Datasets.values()[8].name().equals("TWOPATTERNS"));
		assertTrue(Datasets.values()[9].name().equals("WAFER"));
		assertTrue(Datasets.values()[10].name().equals("INLINESKATE"));
		assertTrue(Datasets.values()[11].name().equals("PATIENTS"));
	}
	
	@Test
	public void readExperimentEnum(){
		assertTrue(Experiments.values()[0].name().equals("REGRESSION"));
		assertTrue(Experiments.values()[1].name().equals("DOMINANT"));
		assertTrue(Experiments.values()[2].name().equals("COUNTED"));
		assertTrue(Experiments.values()[3].name().equals("NGRAM"));
		assertTrue(Experiments.values()[4].name().equals("KNN"));
		assertTrue(Experiments.values()[5].name().equals("DTW"));
	}
	
	@Test
	public void readVariantsEnum(){
		assertTrue(Variants.values()[0].name().equals("ARFFEXPORT"));
		assertTrue(Variants.values()[1].name().equals("ARFFPROCESS"));
		assertTrue(Variants.values()[2].name().equals("FULL"));
	}
}
