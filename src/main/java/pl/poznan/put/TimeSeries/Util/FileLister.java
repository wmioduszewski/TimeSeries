package pl.poznan.put.TimeSeries.Util;

import java.io.File;

public class FileLister {

	
	public static String[] getDatasetPaths(String datasetFolder){
		File folder = new File(datasetFolder);
		File[] filelist = folder.listFiles();
		String[] datasetList = new String[filelist.length];
		for(int i=0;i<filelist.length;i++){
			datasetList[i] = filelist[i].getName();
		}
		return datasetList;
	}
}
