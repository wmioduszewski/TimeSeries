package pl.poznan.put.TimeSeries.Model;

import org.jfree.data.time.Minute;
import org.joda.time.LocalTime;

public class Characteristic {

	Minute examinationTime;
	float tfadj;

	public Characteristic(Minute examinationTime, float tfadj) {
		super();
		this.examinationTime = examinationTime;
		this.tfadj = tfadj;
	}

	public Minute getExaminationTime() {
		return examinationTime;
	}

	public float getTfadj() {
		return tfadj;
	}

	public void setTfadj(float tfadj) {
		this.tfadj = tfadj;
	}
	
	

}
