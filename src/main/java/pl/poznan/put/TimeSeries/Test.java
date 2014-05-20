package pl.poznan.put.TimeSeries;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Test {

	public static void main(String[] args) {
		double a = -12343.4356546000;
		BigDecimal bd = new BigDecimal(45);
		System.out.println(String.format("number: %15.7e",a));		
		
		DecimalFormat formatone = new DecimalFormat("#####.e000");
		System.out.println(formatone.format(a));
	}

}
