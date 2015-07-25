package pl.poznan.put.TimeSeries.Model;

import java.util.List;

public interface IRecord extends IAssignedClass{
	List<Float> getValues(); 
	void addValueAtTheBeginning(Float value);
	void addValueAtTheEnd(Float value);
	String getSaxString();
	void setSaxString(String saxString);
}
