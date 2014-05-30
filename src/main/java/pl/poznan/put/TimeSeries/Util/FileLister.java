package pl.poznan.put.TimeSeries.Util;

import java.io.File;

public class FileLister {

	
	public static String[] getFolderFiles(String folderPath){
		File folder = new File(folderPath);
		File[] filelist = folder.listFiles();
		String[] datasetList = new String[filelist.length];
		for(int i=0;i<filelist.length;i++){
			datasetList[i] = filelist[i].getName();
		}
		return datasetList;
	}
}
