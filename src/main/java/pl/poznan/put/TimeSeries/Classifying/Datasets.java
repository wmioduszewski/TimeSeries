package pl.poznan.put.TimeSeries.Classifying;

import pl.poznan.put.TimeSeries.Util.Config;

public enum Datasets {
	ECG200, ECGFIVEDAYS, SAMPLEUNITTEST, TWOLEADECG, YOGA, MOTESTRAIN, ITALYPOWERDEMAND, CHLORINECONCENTRATION, TWOPATTERNS, WAFER, INLINESKATE, PATIENTS;

	public void setAsCurrent() {
		String path = null;
		Config cfg = Config.getInstance();
		boolean isGlaucoma = false;

		switch (this) {
		case ECG200:
			path = "dataset1/ECG200";
			break;
		case CHLORINECONCENTRATION:
			path = "dataset2/ChlorineConcentration";
			break;
		case ECGFIVEDAYS:
			path = "dataset2/ECGFiveDays";
			break;
		case INLINESKATE:
			path = "dataset2/InlineSkate";
			break;
		case ITALYPOWERDEMAND:
			path = "dataset2/ItalyPowerDemand";
			break;
		case MOTESTRAIN:
			path = "dataset2/MoteStrain";
			break;
		case TWOLEADECG:
			path = "dataset2/TwoLeadECG";
			break;
		case TWOPATTERNS:
			path = "dataset1/Two_Patterns";
			break;
		case WAFER:
			path = "dataset1/wafer";
			break;
		case YOGA:
			path = "dataset1/yoga";
			break;
		case SAMPLEUNITTEST:
			path = "SAMPLEDATASET";
			break;
		case PATIENTS:
			isGlaucoma = true;
			break;
		}

		cfg.setGlaucoma(isGlaucoma);
		if (path != null) {
			cfg.setSingleDataPath(path);
		}

	}

}
