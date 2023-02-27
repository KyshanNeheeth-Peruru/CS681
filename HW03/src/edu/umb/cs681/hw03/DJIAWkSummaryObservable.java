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
	            List<List<Double>> csv = 
	            		lines.map(line -> {
	            			//System.out.println(Stream.of(line.split(",")).map(value->Double.parseDouble(value.substring(0,7))).collect(Collectors.toList()));
	            			return Stream.of(line.split(",")).map(value->Double.parseDouble(value.substring(0,7))).collect(Collectors.toList());
	            		}).collect(Collectors.toList());
	            DJIAWkSummaryObservable observable = new DJIAWkSummaryObservable();
	            for (List<Double> c : csv) 
	            {
	            	//System.out.println(c.get(3));
	            	DSummary ds = new DSummary(c.get(0),c.get(1),c.get(2),c.get(3));
	            	observable.addSummary(ds); 
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

}
