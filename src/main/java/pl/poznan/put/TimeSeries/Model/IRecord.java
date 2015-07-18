package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public interface IRecord {
	double getDestinationClass();
	List<Float> getValues(); 
	void addValueAtTheBeginning(Float value);
	void addValueAtTheEnd(Float value);
	void setSaxString(String saxString);
}
