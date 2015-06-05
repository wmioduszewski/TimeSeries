package pl.poznan.put.TimeSeries.Util;

public class CommonConfig {

	private static CommonConfig _instance = null;
	private static Object mutex = new Object();

	public static CommonConfig getInstance() {
		if (_instance == null)
			synchronized (mutex) {
				if (_instance == null)
					_instance = new CommonConfig();
			}
		return _instance;
	}

	private float attributesToCutRatio = 0.5f;
	private int divisionPartsAmount = 3;
	private boolean isFolderDataUsed = true;
	private int ngramSize = 4;
	private String pureDataSet = "dataset glaucoma/csv2/dane20140812";
	private int saxAlphabeatSize = 4;
	private int saxAttributeLength = 3;

	public void setSingleDataPath(String singleDataPath) {
		this.singleDataPath = singleDataPath;
	}

	private int saxOutputLength = 96;
	private String singleDataPath = "dataset2/ItalyPowerDemand";

	private float trainToTestRatio = 0.5f;

	private String xlsReportPath = "output/reports/Result report glaucoma.xls";

	private CommonConfig() {
	}

	public float getAttributesToCutRatio() {
		return attributesToCutRatio;
	}

	public int getDivisionPartsAmount() {
		return divisionPartsAmount;
	}

	public int getNgramSize() {
		return ngramSize;
	}

	public String getPureDataSet() {
		return SpecificConfig.getProperty("dataFolderPath") + pureDataSet;
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
		return SpecificConfig.getProperty("dataFolderPath") + singleDataPath;
	}

	public float getTrainToTestRatio() {
		return trainToTestRatio;
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

	public void setDivisionPartsAmount(int divisionPartsAmount) {
		this.divisionPartsAmount = divisionPartsAmount;
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

	public void setTrainToTestRatio(float trainToTestRatio) {
		this.trainToTestRatio = trainToTestRatio;
	}

	public void setXlsReportPath(String xlsReportPath) {
		this.xlsReportPath = xlsReportPath;
	}

}
