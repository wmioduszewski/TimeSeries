package pl.poznan.put.TimeSeries.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	static final String path = "src/main/resources/config.properties";

	public static String getProperty(String propertyName) {
		Properties prop = new Properties();
		InputStream input = null;
		String res = null;
		try {
			input = new FileInputStream(path);
			prop.load(input);
			res = prop.getProperty(propertyName);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
}
