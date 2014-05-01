package pl.poznan.put.TimeSeries;

import org.jfree.data.time.Minute;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.Date;

public class Main {

	public static void main(String[] args) {

		Minute m = new Minute(11, 20, 9, 4, 2014);
		System.out.println("ms: " + m.getMiddleMillisecond());
		
		LocalTime lt = new LocalTime(22,4,50);
		//lt.getMillisOfDay()
		DateTime dt = new DateTime(2014,4,9,9,22);
		dt.getMillis();
	}

}
