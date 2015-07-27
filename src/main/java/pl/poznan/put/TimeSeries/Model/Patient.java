package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Patient implements Cloneable, IRecord {

	float age;
	DateTime asleep;
	DateTime awake;
	List<Characteristic> characteristics;
	String chartCaption;
	int id;

	boolean isSick;
	String saxString;

	public Patient(int id) {
		super();
		this.id = id;
		characteristics = new ArrayList<Characteristic>();
		chartCaption = "" + id;
	}

	public void addCharacteristic(Characteristic c) {
		characteristics.add(c);
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	public float getAge() {
		return age;
	}

	public DateTime getAsleep() {
		return asleep;
	}

	public DateTime getAwake() {
		return awake;
	}

	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}

	public String getChartCaption() {
		return chartCaption;
	}

	@Override
	public double getDestinationClass() {
		return isSick() ? 1d : 0d;
	}

	public int getId() {
		return id;
	}

	public String getSaxString() {
		return saxString;
	}

	@Override
	public List<Float> getValues() {
		int size = characteristics.size();
		List<Float> vals = new ArrayList<Float>(size);
		for (int i = 0; i < size; i++) {
			vals.add(characteristics.get(i).tfadj);
		}
		return vals;
	}

	public boolean isSick() {
		return isSick;
	}

	public void removeCharacteristic(Characteristic c) {
		characteristics.remove(c);
	}

	public void setAge(float age) {
		this.age = age;
	}

	public void setAsleep(DateTime asleep) {
		this.asleep = asleep;
	}

	public void setAwake(DateTime awake) {
		this.awake = awake;
	}

	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}

	public void setChartCaption(String chartCaption) {
		this.chartCaption = chartCaption;
	}

	public void setSaxString(String saxString) {
		this.saxString = saxString;
	}

	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}

	@Override
	public void addValueAtTheBeginning(Float value) {
		Characteristic c = new Characteristic(new DateTime());
		c.setTfadj(value);
		characteristics.add(0,c);
	}

	@Override
	public void addValueAtTheEnd(Float value) {
		Characteristic c = new Characteristic(new DateTime());
		c.setTfadj(value);
		characteristics.add(c);
		
	}

}
