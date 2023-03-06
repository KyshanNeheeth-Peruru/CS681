package edu.umb.cs681.hw03;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DJIAWkSummaryObservable extends Observable<DSummary> {
	 public void addSummary(DSummary dSummary) {
	        notifyObservers(dSummary);
	 }
	 
	 public static void main(String[] args) {
	        Path path = Paths.get("C:\\Users\\kisha\\Downloads\\HistoricalPrices.csv");
	        try (Stream<String> lines = Files.lines(path))
	        {
	             
	        	List<List<Double>> csv = lines.skip(1)
	                    .map(line -> Stream.of(line.split(",")).skip(1)
	                            .map(value -> Double.parseDouble(value.trim()))
	                            .collect(Collectors.toList()))
	                    .collect(Collectors.toList());
	        	
	        	DJIAWkSummaryObservable ob = new DJIAWkSummaryObservable();
	        	CandleStickObserver cso = new CandleStickObserver();
	        	ob.addObserver(cso);
	        	
	        	DSummary dsum1=new DSummary(csv.get(0).get(0),csv.get(0).get(1),csv.get(0).get(2),csv.get(0).get(3));
	        	DSummary dsum2=new DSummary(csv.get(1).get(0),csv.get(1).get(1),csv.get(1).get(2),csv.get(1).get(3));
	        	DSummary dsum3=new DSummary(csv.get(2).get(0),csv.get(2).get(1),csv.get(2).get(2),csv.get(2).get(3));
	        	DSummary dsum4=new DSummary(csv.get(3).get(0),csv.get(3).get(1),csv.get(3).get(2),csv.get(3).get(3));
	        	DSummary dsum5=new DSummary(csv.get(4).get(0),csv.get(4).get(1),csv.get(4).get(2),csv.get(4).get(3));
	        	
	        	ob.addSummary(dsum1);
	        	ob.addSummary(dsum2);
	        	ob.addSummary(dsum3);
	        	ob.addSummary(dsum4);
	        	ob.addSummary(dsum5);
 
	        } catch (Exception ex) {
	        	System.out.println("Csv file not Found");        	
	            //ex.printStackTrace();
	        }
	    }

}
