package pl.poznan.put.TimeSeries.Model;

import org.joda.time.LocalTime;

public class Characteristic {

	LocalTime examinationTime;
	float tfadj;

	public Characteristic(LocalTime examinationTime, float tfadj) {
		super();
		this.examinationTime = examinationTime;
		this.tfadj = tfadj;
	}

	public LocalTime getExaminationTime() {
		return examinationTime;
	}

	public float getTfadj() {
		return tfadj;
	}
	
	

}
