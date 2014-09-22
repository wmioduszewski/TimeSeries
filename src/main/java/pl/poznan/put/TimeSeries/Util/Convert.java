package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;

public class Convert {
	
	public static List<Float> fromCharacteristicsToFloatList(List<Characteristic> characteristics){
		List<Float> res = new ArrayList<Float>();
		for(int i=0;i<characteristics.size();i++){
			res.add(characteristics.get(i).getTfadj());
		}
		return res;
	}


}
