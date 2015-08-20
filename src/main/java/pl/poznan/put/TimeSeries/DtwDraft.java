package pl.poznan.put.TimeSeries;

public class DtwDraft {

	private double[] tsA;
	private double[] tsB;
	private double[][] distances;
	private double[][] dtwTable;

	public DtwDraft(double[] timeSeriesA, double[] timeSeriesB) {
		tsA = timeSeriesA;
		tsB = timeSeriesB;
	}

	public double[][] getDtw() {
		createDistanceTable();
		createDtwTable();
		return dtwTable;
	}

	private void createDtwTable() {
		dtwTable = new double[tsA.length][tsB.length];

		dtwTable[0][0] = distances[0][0];

		for (int i = 1; i < tsA.length; i++) {
			dtwTable[i][0] = dtwTable[i - 1][0] + distances[i][0];
		}

		for (int j = 1; j < tsB.length; j++) {
			dtwTable[0][j] = dtwTable[0][j - 1] + distances[0][j];
		}

		for (int i = 1; i < tsA.length; i++) {
			for (int j = 1; j < tsB.length; j++) {
				double min = Math.min(
						Math.min(dtwTable[i - 1][j], dtwTable[i][j - 1]),
						dtwTable[i - 1][j - 1]);
				dtwTable[i][j] = min + distances[i][j];
			}
		}
	}

	private void createDistanceTable() {
		distances = new double[tsA.length][tsB.length];
		for (int i = 0; i < tsA.length; i++) {
			for (int j = 0; j < tsB.length; j++) {
				distances[i][j] = getDistance(tsA[i], tsB[j]);
			}
		}
	}

	private static double getDistance(double a, double b) {
		return Math.pow(a - b, 2);
	}

}
