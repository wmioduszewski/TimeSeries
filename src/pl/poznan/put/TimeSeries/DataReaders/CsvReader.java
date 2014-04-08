package pl.poznan.put.TimeSeries.DataReaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.time.Minute;
import org.joda.time.LocalTime;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;

public class CsvReader {
	// ID,D,TIME,TIMEFF,INTERVAL,TIMEFBI,SAP,DAP,HRBP,MAP,TF,TFADJ,TFGAT,DPP,BODYPOSITION,ACOMMODATION
	public static List<Patient> ReadData(String filepath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filepath));

		List<Patient> patientList = new ArrayList<Patient>();

		// skip first line
		String currLine = br.readLine();
		currLine = br.readLine();

		int lastId = -1;// Integer.parseInt(currLine.split(",")[0]);
		Patient lastPatient = null;
		boolean isFirstLine=true;
		
		while (currLine != null) {
			String[] fields = currLine.split(",");
			int currentId = Integer.parseInt(fields[0]);

			if (currentId != lastId && !isFirstLine) {
				patientList.add(lastPatient);
				lastPatient = new Patient(currentId);
			}

			if(isFirstLine)
			{
				lastPatient = new Patient(currentId);
				isFirstLine = false;
			}
			String [] minuteElems = fields[2].trim().split(":");
			
			int minutes = Integer.parseInt(minuteElems[1]);
			int hours = Integer.parseInt(minuteElems[0]);
			int days = 2;
			
			if(hours>=12)
				days = 1;
			
			Minute exTime = new Minute(minutes, hours,days,1,1900);
			float tfadj;
			try {
				tfadj = Float.parseFloat(fields[11]);
			} catch (NumberFormatException e) {
				System.out.println("Nie udało się dla pacjenta "
						+ lastPatient.getId() + " o godzinie " + exTime);
				tfadj = 0;
			}
			Characteristic c = new Characteristic(exTime, tfadj);

			lastPatient.AddCharacteristic(c);
			lastId = currentId;
			currLine = br.readLine();
		}
		br.close();

		return patientList;

	}
}
