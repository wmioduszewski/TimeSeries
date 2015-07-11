package pl.poznan.put.TimeSeries;

import com.fastdtw.dtw.FastDTW;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.util.Distances;


public class Test {
	
	public static void main(String[] args) throws Exception {
		foreignDtwTest();
	}
	
	
	private static void foreignDtwTest(){
		TimeSeries ts1 = TimeSeriesBase.builder()
                .add(0, 1)
                .add(1, 2)
                .add(2, 5)
                .add(2, 4)
                .add(2, 3)
                .add(2, 7)
                .build();
		
		TimeSeries ts2 = TimeSeriesBase.builder()
                .add(0, 2)
                .add(1, 3)
                .add(2, 2)
                .add(2, 1)
                .add(2, 3)
                .add(2, 4)
                .build();
		
		double distance = FastDTW.compare(ts1, ts2, 10, Distances.EUCLIDEAN_DISTANCE)
        .getDistance();
		System.out.println(distance);
	}
	
	private static void DtwTest(){
		double[] a = {1,2,5,4,3,7};
		double[] b = {2,3,2,1,3,4};
		
		DtwDraft dtw = new DtwDraft(a, b);
		double[][] res = dtw.getDtw();
		printTable(res);
	}
	
	private static void printTable(double[][] table){
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table[i].length;j++){
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	private static void test1()
	{
		double[] a = {2};
		double[] b = {5};
		double res=0;
		for(int i=0;i<a.length;i++){
			res+= Math.pow(Math.abs(a[i]-b[i]), 2);
		}
		res=Math.sqrt(res);
		
		System.out.println(res);
	}

}
