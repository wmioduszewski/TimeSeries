package pl.poznan.put.TimeSeries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		processFiles("output/arffOutput/DOMINANT");
		
		System.out.println("ok");
	}

	static void processFiles(String folderPath) {
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		List<File> fileList = Arrays.asList(files);
		//fileList = fileList.subList(0, 1);
		for (File file : fileList) {

			 removeSpaceFromName(file);
			 try {
			 updateDestinationClass(file);
			 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
		}
	}

	private static void updateDestinationClass(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		StringBuilder content= new StringBuilder();
		
		while (line!=null) {
			if(line.startsWith("@attribute destClass {")){
				String patt = "\\.0";
				line = line.replaceAll(patt, "");
			}
			content.append(line + "\n"); //System.getProperty("line.separator")
			line = br.readLine();
		}
		br.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content.toString());
		writer.close();
	}

	private static void removeSpaceFromName(File file) {
		String pattern = " ";
		String oldFilePath = file.getAbsolutePath();
		String oldFileName = oldFilePath.substring(oldFilePath.lastIndexOf("\\"));
		String newFileName = oldFileName.replaceAll(pattern, "");
		String filePath = oldFilePath.substring(0, oldFilePath.lastIndexOf("\\") + 1);
		File newFile = new File(filePath + newFileName);
		file.renameTo(newFile);
	}

}
