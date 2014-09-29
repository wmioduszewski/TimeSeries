package pl.poznan.put.TimeSeries;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.SaxString;

public class Utils {
	
	public static List<Patient> getTestPatients(){
		
		List<Patient> patients = new ArrayList<Patient>();
		
		Patient p = new Patient(1);
		p.setSaxString(new SaxString("abbaaaabbbbbbababa", 0, 0));
		p.setSick(true);
		patients.add(p);
		p = new Patient(2);
		p.setSaxString(new SaxString("bbbbbbaaaaaabababa", 0, 0));
		p.setSick(false);
		patients.add(p);
		
		return patients;
	}

}
