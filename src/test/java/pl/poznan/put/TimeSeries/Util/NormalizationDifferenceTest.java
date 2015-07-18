package pl.poznan.put.TimeSeries.Util;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.poznan.put.TimeSeries.Model.EamonnRecord;

public class NormalizationDifferenceTest {

	@Test
	public void NormalizationDifference() throws Exception {

		int alphabeatSize = 3;
		int outputLength = 3;

		List<Float> baseArray = Arrays.asList(1f, 2f, 3f);
		float globalMin = 0f;
		float globalMax = 5f;
		List<Float> normalizedArray = new ArrayList<Float>();
		normalizedArray.add(globalMin);
		normalizedArray.addAll(baseArray);
		normalizedArray.add(globalMax);

		EamonnRecord recordClean = new EamonnRecord(1, baseArray);
		recordClean.setSaxString(SaxPerformer.TranslateUnifiedRecordToString(
				recordClean, outputLength, alphabeatSize));

		EamonnRecord recordNormalized = new EamonnRecord(1, normalizedArray);
		recordNormalized.setSaxString(SaxPerformer
				.TranslateUnifiedRecordToString(recordNormalized,
						outputLength + 2, alphabeatSize));

		String cleanSax = recordClean.getSaxString();
		String normalizedSax = recordNormalized.getSaxString();

		normalizedSax = normalizedSax.substring(1, normalizedSax.length() - 1);

		// normalization gives a result
		assertNotEquals(normalizedSax, cleanSax);
	}

}
