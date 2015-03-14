package pl.poznan.put.TimeSeries.Sax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import pl.poznan.put.TimeSeries.DataImporters.PatientDataImporterPure;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.EamonnRecord;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.SaxPerformer;

public class GeneralCase {

	@Test
	public void test() throws Exception {
		List<Float> vals = Arrays.asList(1f,20f, 30f, 40f,40f);
		EamonnRecord unif = new EamonnRecord(1, vals);
		String output = SaxPerformer.TranslateUnifiedRecordToString(unif,
				vals.size(), 4);

		System.out.println(output);
	}
	
	@Test
	public void NormalizationDifference() throws Exception{
		
		int alphabeatSize = Integer.parseInt(Configuration
				.getProperty("saxAlphabeatSize"));
		;
		int outputLength = Integer.parseInt(Configuration
				.getProperty("saxOutputLength"));
		
		PatientDataImporterPure importer = new PatientDataImporterPure(Configuration.getProperty("pureDataSet"));
		List<Patient> patients = importer.ImportData();
		
		Patient p = patients.get(0);
		String oldSax = p.getSaxString();
		System.out.println(p.getId());
		List<Characteristic> oldChars = p.getCharacteristics();
		DateTime minDate = oldChars.stream().min(Comparator.comparing(x-> x.getExaminationTime())).get().getExaminationTime();
		DateTime maxDate = oldChars.stream().max(Comparator.comparing(x-> x.getExaminationTime())).get().getExaminationTime();
		
		List<Characteristic> newChars = new ArrayList<Characteristic>();
		
		Characteristic minChar = new Characteristic(minDate.minusDays(1));
		float minTfAdj = oldChars.stream().min(Comparator.comparing(x->x.getTfadj())).get().getTfadj();
		minChar.setTfadj(minTfAdj);
		
		Characteristic maxChar = new Characteristic(maxDate.plusDays(1));
		float maxTfAdj = oldChars.stream().max(Comparator.comparing(x->x.getTfadj())).get().getTfadj();
		maxChar.setTfadj(maxTfAdj);
		
		System.out.println("Max tf " + maxTfAdj);
		
		newChars.add(minChar);
		newChars.addAll(oldChars);
		newChars.add(maxChar);
		
		p.setCharacteristics(newChars);
		
		String newSax = SaxPerformer.TranslateTimeSeriesToString(p, outputLength+2, alphabeatSize);
		System.out.println(oldSax);
		System.out.println(newSax);
		System.out.println(newSax.substring(1, newSax.length()-1));
		
	}

}
