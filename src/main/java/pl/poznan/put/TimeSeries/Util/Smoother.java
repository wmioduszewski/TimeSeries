package pl.poznan.put.TimeSeries.Util;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.TimeSeries.Model.Characteristic;

public class Smoother {

	public static List<Characteristic> SmoothCharacteristics(
			List<Characteristic> characteristics, int size) {
		List<Characteristic> smoothed = new ArrayList<Characteristic>();

		int count = characteristics.size();
		if (size > count)
			size = count;

		for (int i = size; i < count; i++) {
			float sum = 0;
			Characteristic current = null;
			for (int j = i - size; j < i; j++) {
				current = characteristics.get(j);
				sum += current.getTfadj();
			}
			current.setTfadj(sum / (float) size);
			smoothed.add(current);
		}
		return smoothed;
	}

	public static List<Characteristic> SmoothEWMACharacteristics(
			List<Characteristic> characteristics, float omega) {
		List<Characteristic> smoothed = new ArrayList<Characteristic>();

		int count = characteristics.size();

		for (int i = 1; i < count; i++) {
			Characteristic current = characteristics.get(i);
			Characteristic previous = characteristics.get(i - 1);
			float newValue = omega * current.getTfadj() + (1 - omega)
					* previous.getTfadj();
			current.setTfadj(newValue);
			smoothed.add(current);
		}
		return smoothed;
	}

}
