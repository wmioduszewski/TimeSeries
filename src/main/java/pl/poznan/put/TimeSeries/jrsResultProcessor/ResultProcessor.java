package pl.poznan.put.TimeSeries.jrsResultProcessor;

public class ResultProcessor {

	static String folderPath = "output/results/regression-vcdomlem-clean/";

	public static void main(String[] args) {
		FileCrawler crawler = new FileCrawler(folderPath);
		try {
			crawler.wholeExperiment();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}

}
