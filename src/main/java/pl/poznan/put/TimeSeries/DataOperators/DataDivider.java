package pl.poznan.put.TimeSeries.DataOperators;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.Limits;
import pl.poznan.put.TimeSeries.Constants.TimeLimitPair;
import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class DataDivider {

	private List<Patient> patients;

	public DataDivider(List<Patient> patients) {
		super();
		this.patients = patients;
	}

	public void divideData() {
		for (AgeLimit limit : Limits.AgeLimits) {
			Stream<Patient> agedPatientsStream = patients.stream().filter(
					x -> x.getAge() >= limit.getLowerBound()
							&& x.getAge() <= limit.getUpperBound());
			System.out.println("AgeLimit: " + limit.getLowerBound() + " - "
					+ limit.getUpperBound());

			List<Patient> agedPatients = agedPatientsStream.collect(Collectors
					.toList());

			for (Patient patient : agedPatients) {
				System.out.println(patient.getAge());

				for (TimeLimitPair timeLimit : Limits.TimeLimits) {

					DateTime lowerBound = null;
					DateTime upperBound = null;
					if (timeLimit.getLowerOption() == TimeLimitPair.Asleep)
						lowerBound = patient.getAsleep();
					else if (timeLimit.getLowerOption() == TimeLimitPair.Awake)
						lowerBound = patient.getAwake();
					if (timeLimit.getUpperOption() == TimeLimitPair.Asleep)
						upperBound = patient.getAsleep();
					else if (timeLimit.getUpperOption() == TimeLimitPair.Awake)
						upperBound = patient.getAwake();
					lowerBound = lowerBound
							.plusHours(timeLimit.getLowerMod());
					upperBound = upperBound
							.plusHours(timeLimit.getUpperMod());
					System.out.println("from: " + lowerBound + " to: " + upperBound);
					for (Characteristic characteristic : patient
							.getCharacteristics()) {
						DateTime time = characteristic.getExaminationTime();
						if (time.isAfter(lowerBound) && time.isBefore(upperBound)) {
							System.out.println(time);
						}

					}
				}
			}

		}
	}
}
