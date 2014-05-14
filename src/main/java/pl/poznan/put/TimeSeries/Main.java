package pl.poznan.put.TimeSeries;

import org.jfree.data.time.Minute;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) {

//		Minute m = new Minute(11, 20, 9, 4, 2014);
//		System.out.println("ms: " + m.getMiddleMillisecond());
//		
//		LocalTime lt = new LocalTime(22,4,50);
//		//lt.getMillisOfDay()
//		DateTime dt = new DateTime(2014,4,9,9,22);
//		dt.getMillis();
		
		double a = -12343.4356546000;
		BigDecimal bd = new BigDecimal(45);
		System.out.println(String.format("number: %15.7e",a));		
		
		DecimalFormat formatone = new DecimalFormat("#####.e000");
		System.out.println(formatone.format(a));
	}

}
