package pl.poznan.put.TimeSeries.Util;

import java.io.File;

public class FileLister {

	public static String[] getDirectoryFiles(String directoryPath) {
		File[] filelist = listFiles(directoryPath);
		String[] datasetList = new String[filelist.length];
		for (int i = 0; i < filelist.length; i++) {
			datasetList[i] = filelist[i].getName();
		}
		return datasetList;
	}

	public static String[] getDirectories(String directoryPath) {
		File[] filelist = listFiles(directoryPath);
		String[] datasetList = new String[filelist.length];
		for (int i = 0; i < filelist.length; i++) {
			datasetList[i] = filelist[i].getAbsolutePath();
		}
		return datasetList;
	}

	private static File[] listFiles(String path) {
		File folder = new File(path);
		return folder.listFiles();
	}
}
