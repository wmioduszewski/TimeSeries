package pl.poznan.put.TimeSeries.Model;

import org.jfree.data.time.Minute;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class Characteristic {

	DateTime examinationTime;
	float tfadj;
	Minute freeChartExTime;

	public Minute getFreeChartExTime() {
		return freeChartExTime;
	}

	public Characteristic(DateTime examinationTime, float tfadj) {
		super();
		this.examinationTime = examinationTime;
		this.tfadj = tfadj;
		this.freeChartExTime = new Minute(examinationTime.getMinuteOfHour(),
				examinationTime.getHourOfDay(),
				examinationTime.getDayOfMonth(), examinationTime.getMonthOfYear(),
				examinationTime.getYear());
	}

	public DateTime getExaminationTime() {
		return examinationTime;
	}

	public float getTfadj() {
		return tfadj;
	}

	public void setTfadj(float tfadj) {
		this.tfadj = tfadj;
	}

}
