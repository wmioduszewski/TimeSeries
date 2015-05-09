package pl.poznan.put.Deprecated;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Constants.AgeLimit;
import pl.poznan.put.TimeSeries.Constants.TimeLimit;
import pl.poznan.put.TimeSeries.Model.Patient;

@Deprecated
public class PatientGroup {

	private TimeLimit timeLimit;
	private AgeLimit ageLimit;
	private List<Patient> patients;
	private boolean isSick;

	public PatientGroup(TimeLimit timeLimit, AgeLimit ageLimit) {
		super();
		this.timeLimit = timeLimit;
		this.ageLimit = ageLimit;
		patients = new ArrayList<Patient>();
	}

	public TimeLimit getTimeLimit() {
		return timeLimit;
	}

	public AgeLimit getAgeLimit() {
		return ageLimit;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void addPatient(Patient patient) {
		patients.add(patient);
	}

	public void removePatient(Patient patient) {
		patients.remove(patient);
	}

	public boolean isSick() {
		return isSick;
	}

	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}

}
