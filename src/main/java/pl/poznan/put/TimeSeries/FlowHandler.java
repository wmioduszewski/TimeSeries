package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Util.Configuration;
import pl.poznan.put.TimeSeries.Util.FileLister;

public class FlowHandler {
	
	private String path;
	private boolean isFolder;

	public FlowHandler() {
		this.isFolder = Configuration.getProperty("isFolderDataUsed")=="1" ? true : false;
		this.path = Configuration.getProperty("folderDataPath");
	}
	
	public void startProcessing(){
		if(isFolder)
		FileLister.getDatasetPaths(path);
	}
	
	
	

}
