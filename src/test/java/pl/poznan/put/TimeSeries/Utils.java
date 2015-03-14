package pl.poznan.put.TimeSeries;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Patient;

public class Utils {
	
	public static List<Patient> getTestPatients(){
		
		List<Patient> patients = new ArrayList<Patient>();
		
		Patient p = new Patient(1);
		p.setSaxString("abbaaaabbbbbbababa");
		p.setSick(true);
		patients.add(p);
		p = new Patient(2);
		p.setSaxString("bbbbbbaaaaaabababa");
		p.setSick(false);
		patients.add(p);
		
		return patients;
	}

}
