package pl.poznan.put.TimeSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	
	public static boolean checkIfFilesContentIsTheSame(String pathA, String pathB) {
		String contentA = "a";
		String contentB = "b";
		try {
			contentA = new String(Files.readAllBytes(Paths.get(pathA)));
			contentB = new String(Files.readAllBytes(Paths.get(pathB)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		contentA = contentA.replaceAll("\r\n", "\n");
		contentB = contentB.replaceAll("\r\n", "\n");

		return contentA.equals(contentB);
	}

}
