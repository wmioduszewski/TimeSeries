package pl.poznan.put.TimeSeries.Model;

public class SaxString {
	
	String output;
	int outputLength;
	int alphabeatSize;
	
	public SaxString(String content, int outputLength, int alphabeatSize) {
		super();
		this.output = content;
		this.outputLength = outputLength;
		this.alphabeatSize = alphabeatSize;
	}

	public String getContent() {
		return output;
	}

	public int getOutputLength() {
		return outputLength;
	}

	public int getAlphabeatSize() {
		return alphabeatSize;
	}
	
	

}
