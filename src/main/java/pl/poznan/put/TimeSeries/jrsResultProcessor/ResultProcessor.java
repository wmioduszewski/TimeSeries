package pl.poznan.put.TimeSeries.jrsResultProcessor;

public class ResultProcessor {

	

	public static void main(String[] args) {
		FileCrawler crawler = new FileCrawler();
		
		try {
			crawler.wholeExperiment();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}

}
