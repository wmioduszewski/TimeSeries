package pl.poznan.put.TimeSeries.Util;

import java.io.File;
import java.io.FilenameFilter;

public class FileLister {

	public static String[] getDirectoryFiles(String directoryPath, String prefix, String extension) {
		File[] filelist = listFiles(directoryPath, prefix, extension);
		String[] datasetList = new String[filelist.length];
		for (int i = 0; i < filelist.length; i++) {
			datasetList[i] = filelist[i].getName();
		}
		return datasetList;
	}

	public static String[] getDirectoryFiles(String directoryPath) {
		File[] filelist = listFiles(directoryPath, null, null);
		String[] datasetList = new String[filelist.length];
		for (int i = 0; i < filelist.length; i++) {
			datasetList[i] = filelist[i].getName();
		}
		return datasetList;
	}

	public static String[] getDirectories(String directoryPath) {
		File[] filelist = listFiles(directoryPath, null, null);
		String[] datasetList = new String[filelist.length];
		for (int i = 0; i < filelist.length; i++) {
			datasetList[i] = filelist[i].getAbsolutePath();
		}
		return datasetList;
	}

	private static File[] listFiles(String path, String prefix, String extension) {
		File folder = new File(path);
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (extension != null && !name.endsWith(extension))
					return false;
				if (prefix != null && !name.startsWith(prefix))
					return false;
				return true;
			}
		};
		return folder.listFiles(filter);
	}
}
