package pl.poznan.put.TimeSeries.Model;

public class SaxString {
	
	String output;
	int alphabeatLength;
	int outputLength;
	
	public SaxString(String content, int alphabeatLength, int outputLength) {
		super();
		this.output = content;
		this.alphabeatLength = alphabeatLength;
		this.outputLength = outputLength;
	}

	public String getContent() {
		return output;
	}

	public int getAlphabeatLength() {
		return alphabeatLength;
	}

	public int getOutputLength() {
		return outputLength;
	}
	
	

}
