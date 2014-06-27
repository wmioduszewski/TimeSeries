package pl.poznan.put.TimeSeries.Model;

import org.jfree.data.time.Minute;
import org.joda.time.DateTime;

public class Characteristic {

	DateTime examinationTime;
	float tfadj;
	float tf;

	Minute freeChartExTime;

	public Minute getFreeChartExTime() {
		return freeChartExTime;
	}

	public Characteristic(DateTime examinationTime) {
		this.examinationTime = examinationTime;
		this.freeChartExTime = new Minute(examinationTime.getMinuteOfHour(),
				examinationTime.getHourOfDay(),
				examinationTime.getDayOfMonth(),
				examinationTime.getMonthOfYear(), examinationTime.getYear());
	}

	public DateTime getExaminationTime() {
		return examinationTime;
	}

	public float getTfadj() {
		return tfadj;
	}

	public float getTf() {
		return tf;
	}

	public void setTf(float tf) {
		this.tf = tf;
	}

	public void setTfadj(float tfadj) {
		this.tfadj = tfadj;
	}

}
