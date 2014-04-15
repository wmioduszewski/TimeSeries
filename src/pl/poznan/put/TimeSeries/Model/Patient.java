package pl.poznan.put.TimeSeries.Model;

import java.util.ArrayList;
import java.util.List;

public class Patient implements Cloneable {

	int id;
	List<Characteristic> characteristics;
	String chartCaption;
	List<SaxString> saxStrings;

	public Patient(int id) {
		super();
		this.id = id;
		characteristics = new ArrayList<Characteristic>();
		saxStrings = new ArrayList<SaxString>();
		chartCaption = ""+id;
	}

	public void AddCharacteristic(Characteristic c) {
		characteristics.add(c);
	}

	public void RemoveCharacteristic(Characteristic c) {
		characteristics.remove(c);
	}
	
	public void AddSaxString(SaxString s) {
		saxStrings.add(s);
	}

	public void RemoveSaxString(SaxString s) {
		saxStrings.remove(s);
	}

	public List<SaxString> getSaxStrings() {
		return saxStrings;
	}

	public void setSaxStrings(List<SaxString> saxStrings) {
		this.saxStrings = saxStrings;
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
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}

}
