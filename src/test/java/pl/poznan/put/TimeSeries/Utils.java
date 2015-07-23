package pl.poznan.put.TimeSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	public static boolean checkIfFilesContentIsTheSame(String pathA,
			String pathB) {
		String contentA = "a";
		String contentB = "b";
		try {
			contentA = new String(Files.readAllBytes(Paths.get(pathA)));
			contentB = new String(Files.readAllBytes(Paths.get(pathB)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		contentA = contentA.replaceAll("\r\n", "\n");
		contentB = contentB.replaceAll("\r\n", "\n");

		return contentA.equals(contentB);
	}

}
