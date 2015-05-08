package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;

import pl.poznan.put.TimeSeries.Utils;

public class CommonCase {

	public static boolean isExporterCorrect(
			ArffExporterBase exporter, String patternPath,
			String exportPath) {
		
		exporter.buildInstances();
		exporter.saveArff(exportPath);
		boolean result = Utils.checkIfFilesContentIsTheSame(exportPath, patternPath);
		File f = new File(exportPath);
		f.delete();
		return result;
	}

}
