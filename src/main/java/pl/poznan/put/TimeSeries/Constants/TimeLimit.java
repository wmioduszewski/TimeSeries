package pl.poznan.put.TimeSeries.Constants;

public class TimeLimit {
	private char lowerOption;
	private int lowerMod;
	private char upperOption;
	private int upperMod;
	public static final char Awake = 'w';
	public static final char Asleep = 's';

	public TimeLimit(char lowerOption, int lowerMod, char upperOption,
			int upperMod) {
		this.lowerOption = lowerOption;
		this.lowerMod = lowerMod;
		this.upperOption = upperOption;
		this.upperMod = upperMod;
	}

	public char getLowerOption() {
		return lowerOption;
	}

	public int getLowerMod() {
		return lowerMod;
	}

	public char getUpperOption() {
		return upperOption;
	}

	public int getUpperMod() {
		return upperMod;
	}
}
