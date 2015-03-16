package pl.poznan.put.TimeSeries.Util;

public class CommonConfig {

	private int saxAlphabeatSize = 4;
	private int saxOutputLength = 96;
	private int saxAttributeLength = 3;
	private int ngramSize = 4;
	private float trainToTestRatio = 0.5f;
	private String xlsReportPath = "output/reports/Result report glaucoma.xls";
	private String pureArffOutput="data/";
	private int divisionPartsAmount = 3;
	private float attributesToCutRatio = 0.5f;
	private static Object mutex = new Object();
	private boolean isFolderDataUsed = true;

	public boolean isFolderDataUsed() {
		return isFolderDataUsed;
	}

	public void setFolderDataUsed(boolean isFolderDataUsed) {
		this.isFolderDataUsed = isFolderDataUsed;
	}

	private CommonConfig() {
	}

	private static CommonConfig _instance = null;

	public static CommonConfig getInstance() {
		if (_instance == null)
			synchronized (mutex) {
				if (_instance == null)
					_instance = new CommonConfig();
			}
		return _instance;
	}

	public int getSaxAlphabeatSize() {
		return saxAlphabeatSize;
	}

	public void setSaxAlphabeatSize(int saxAlphabeatSize) {
		this.saxAlphabeatSize = saxAlphabeatSize;
	}

	public int getSaxOutputLength() {
		return saxOutputLength;
	}

	public void setSaxOutputLength(int saxOutputLength) {
		this.saxOutputLength = saxOutputLength;
	}

	public int getSaxAttributeLength() {
		return saxAttributeLength;
	}

	public void setSaxAttributeLength(int saxAttributeLength) {
		this.saxAttributeLength = saxAttributeLength;
	}

	public int getNgramSize() {
		return ngramSize;
	}

	public void setNgramSize(int ngramSize) {
		this.ngramSize = ngramSize;
	}

	public float getTrainToTestRatio() {
		return trainToTestRatio;
	}

	public void setTrainToTestRatio(float trainToTestRatio) {
		this.trainToTestRatio = trainToTestRatio;
	}

	public String getXlsReportPath() {
		return xlsReportPath;
	}

	public void setXlsReportPath(String xlsReportPath) {
		this.xlsReportPath = xlsReportPath;
	}

	public int getDivisionPartsAmount() {
		return divisionPartsAmount;
	}

	public void setDivisionPartsAmount(int divisionPartsAmount) {
		this.divisionPartsAmount = divisionPartsAmount;
	}

	public float getAttributesToCutRatio() {
		return attributesToCutRatio;
	}

	public void setAttributesToCutRatio(float attributesToCutRatio) {
		this.attributesToCutRatio = attributesToCutRatio;
	}
}
