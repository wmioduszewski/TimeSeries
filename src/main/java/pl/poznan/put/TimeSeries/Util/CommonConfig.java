package pl.poznan.put.TimeSeries.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonConfig {

	private static CommonConfig _instance = null;
	private static Object mutex = new Object();

	static final String path = "src/main/resources/config.properties";

	public static CommonConfig getInstance() {
		if (_instance == null)
			synchronized (mutex) {
				if (_instance == null)
					_instance = new CommonConfig();
			}
		return _instance;
	}

	private static float getFloatProperty(String propertyName) {
		return Float.parseFloat(getProperty(propertyName));
	}

	private static int getIntProperty(String propertyName) {
		return Integer.parseInt(getProperty(propertyName));
	}

	private static String getProperty(String propertyName) {
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

	private float attributesToCutRatio = getFloatProperty("attributesToCutRatio");
	private int crossValidationFolds = getIntProperty("crossValidationFolds");
	private int crossValidationRepetitions = getIntProperty("crossValidationRepetitions");;
	private String dataFolderPath = getProperty("dataFolderPath");
	private int divisionPartsAmount = getIntProperty("divisionPartsAmount");

	private int dtwSearchRadius = getIntProperty("dtwSearchRadius");

	private String glaucomaDataSet = getProperty("glaucomaDataSet");
	private boolean isFolderDataUsed = getIntProperty("isFolderDataUsed") == 1;
	private int ngramSize = getIntProperty("ngramSize");
	private int saxAlphabeatSize = getIntProperty("saxAlphabeatSize");
	private int saxAttributeLength = getIntProperty("saxAttributeLength");
	private int saxOutputLength = getIntProperty("saxOutputLength");
	private String singleDataPath = getProperty("singleDataPath");

	private String xlsReportPath = getProperty("xlsReportPath");

	private CommonConfig() {
	}

	public float getAttributesToCutRatio() {
		return attributesToCutRatio;
	}

	public int getCrossValidationFolds() {
		return crossValidationFolds;
	}

	public int getCrossValidationRepetitions() {
		return crossValidationRepetitions;
	}

	public String getDataFolderPath() {
		return dataFolderPath;
	}

	public int getDivisionPartsAmount() {
		return divisionPartsAmount;
	}

	public int getDtwSearchRadius() {
		return dtwSearchRadius;
	}

	public String getGlaucomaDataSet() {
		return dataFolderPath + glaucomaDataSet;
	}

	public int getNgramSize() {
		return ngramSize;
	}

	public int getSaxAlphabeatSize() {
		return saxAlphabeatSize;
	}

	public int getSaxAttributeLength() {
		return saxAttributeLength;
	}

	public int getSaxOutputLength() {
		return saxOutputLength;
	}

	public String getSingleDataPath() {
		return dataFolderPath + singleDataPath;
	}

	public String getXlsReportPath() {
		return xlsReportPath;
	}

	public boolean isFolderDataUsed() {
		return isFolderDataUsed;
	}

	public void setAttributesToCutRatio(float attributesToCutRatio) {
		this.attributesToCutRatio = attributesToCutRatio;
	}

	public void setCrossValidationFolds(int crossValidationFolds) {
		this.crossValidationFolds = crossValidationFolds;
	}

	public void setCrossValidationRepetitions(int crossValidationRepetitions) {
		this.crossValidationRepetitions = crossValidationRepetitions;
	}

	public void setDataFolderPath(String dataFolderPath) {
		this.dataFolderPath = dataFolderPath;
	}

	public void setDivisionPartsAmount(int divisionPartsAmount) {
		this.divisionPartsAmount = divisionPartsAmount;
	}

	public void setDtwSearchRadius(int dtwSearchRadius) {
		this.dtwSearchRadius = dtwSearchRadius;
	}

	public void setFolderDataUsed(boolean isFolderDataUsed) {
		this.isFolderDataUsed = isFolderDataUsed;
	}

	public void setNgramSize(int ngramSize) {
		this.ngramSize = ngramSize;
	}

	public void setSaxAlphabeatSize(int saxAlphabeatSize) {
		this.saxAlphabeatSize = saxAlphabeatSize;
	}

	public void setSaxAttributeLength(int saxAttributeLength) {
		this.saxAttributeLength = saxAttributeLength;
	}

	public void setSaxOutputLength(int saxOutputLength) {
		this.saxOutputLength = saxOutputLength;
	}

	public void setSingleDataPath(String singleDataPath) {
		this.singleDataPath = singleDataPath;
	}

	public void setXlsReportPath(String xlsReportPath) {
		this.xlsReportPath = xlsReportPath;
	}

}
