package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.TimeLimitPair;

public class PatientGroup {
	
	private TimeLimitPair timeLimit;
	private AgeLimit ageLimit;
	private List<Patient> patients;
	
	public PatientGroup(TimeLimitPair timeLimit, AgeLimit ageLimit) {
		super();
		this.timeLimit = timeLimit;
		this.ageLimit = ageLimit;
		patients = new ArrayList<Patient>();
	}

	public TimeLimitPair getTimeLimit() {
		return timeLimit;
	}

	public AgeLimit getAgeLimit() {
		return ageLimit;
	}

	public List<Patient> getPatients() {
		return patients;
	}
	
	public void addPatient(Patient patient){
		patients.add(patient);
	}
	
	public void removePatient(Patient patient){
		patients.remove(patient);
	}
	
	
	
	
	

}
