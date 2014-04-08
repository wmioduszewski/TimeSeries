package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;

public class Smoother {

	public static List<Characteristic> SmoothCharacteristics(
			List<Characteristic> characteristics, int size) {
		List<Characteristic> smoothed = new ArrayList<Characteristic>();
		
		int count = characteristics.size();
		if(size>count) size = count;
		
		for (int i = size; i < count; i++) {
			float sum = 0;
			Characteristic current = null; 
			for (int j = i - size; j < i; j++) {
				current = characteristics.get(j);
				sum+=current.getTfadj();
			}
			current.setTfadj(sum / (float) size);
			smoothed.add(current);
		}
		return smoothed;
	}

}
