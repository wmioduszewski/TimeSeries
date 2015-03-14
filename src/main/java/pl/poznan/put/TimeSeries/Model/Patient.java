package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Patient implements Cloneable, IRecord {

	int id;
	float age;
	boolean isSick;
	DateTime awake;
	DateTime asleep;
	String saxString;

	String chartCaption;
	List<Characteristic> characteristics;

	public boolean isSick() {
		return isSick;
	}

	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}

	public Patient(int id) {
		super();
		this.id = id;
		characteristics = new ArrayList<Characteristic>();
		chartCaption = "" + id;
	}

	public void addCharacteristic(Characteristic c) {
		characteristics.add(c);
	}

	public void RemoveCharacteristic(Characteristic c) {
		characteristics.remove(c);
	}

	public String getSaxString() {
		return saxString;
	}

	public void setSaxString(String saxString) {
		this.saxString = saxString;
	}

	public String getChartCaption() {
		return chartCaption;
	}

	public void setChartCaption(String chartCaption) {
		this.chartCaption = chartCaption;
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

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public DateTime getAwake() {
		return awake;
	}

	public void setAwake(DateTime awake) {
		this.awake = awake;
	}

	public DateTime getAsleep() {
		return asleep;
	}

	public void setAsleep(DateTime asleep) {
		this.asleep = asleep;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public double getDestinationClass() {
		return isSick() ? 1d : 0d;
	}

	@Override
	public List<Float> getValues() {
		int size = characteristics.size();
		List<Float> vals = new ArrayList<Float>();
		for (int i = 0; i < size; i++) {
			vals.add(characteristics.get(i).tfadj);
		}
		return vals;
	}

}
