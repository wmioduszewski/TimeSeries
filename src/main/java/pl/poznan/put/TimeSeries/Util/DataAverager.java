package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import pl.poznan.put.TimeSeries.Model.Characteristic;
import pl.poznan.put.TimeSeries.Model.Patient;
import pl.poznan.put.TimeSeries.Model.PatientGroup;

public class DataAverager {
	
	public static List<Float> averageData(PatientGroup patientGroup) throws Exception{
		
		List<Patient> patients =patientGroup.getPatients(); 
		List<MyPair> summary = new ArrayList<MyPair>();		 
		
		for (Patient patient : patientGroup.getPatients()) {
			List<Characteristic> chars = patient.getCharacteristics();
			for(int j=0;j<chars.size();j++){
				//if(summary.size()>j) throw new Exception("This should never occur.");
				
				if(summary.size()==j){
					MyPair pair;
					pair = new MyPair() ;//{1,2};
					pair.count = 1;
					pair.sum = chars.get(j).getTfadj();
					summary.add(pair);
				}
				else
				{
					MyPair pair = summary.get(j);
					pair.count++;
					pair.sum += chars.get(j).getTfadj();					
				}				
			}
		}
		List<Float> result = new ArrayList<Float>();
		for(int i=0;i<summary.size();i++){
			MyPair elem = summary.get(i);
			Float res = elem.sum / elem.count;
			result.add(res);
		}
		
		return result;
	}
	
	static class MyPair
	{
		float sum;
		int count;
	}
}