package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;
import java.util.List;

public class Patient {

	int id;
	List<Characteristic> characteristics;

	public Patient(int id) {
		super();
		this.id = id;
		characteristics = new ArrayList<Characteristic>();
	}

	public void AddCharacteristic(Characteristic c) {
		characteristics.add(c);
	}

	public void RemoveCharacteristic(Characteristic c) {
		characteristics.remove(c);
	}

	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}

	public int getId() {
		return id;
	}

}
