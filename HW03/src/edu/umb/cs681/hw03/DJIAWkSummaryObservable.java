package edu.umb.cs681.hw03;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DJIAWkSummaryObservable extends Observable<WkSummary> {
	 public void addSummary(WkSummary wksum) {
	        notifyObservers(wksum);
	 }
	 
	 public static void main(String[] args) {
	        Path path = Paths.get("HistoricalPrices.csv");
	        try (Stream<String> lines = Files.lines(path))
	        {
	             
	        	List<List<Double>> csv = lines.skip(1)
	                    .map(line -> Stream.of(line.split(",")).skip(1)
	                            .map(value -> Double.parseDouble(value.trim()))
	                            .collect(Collectors.toList()))
	                    .collect(Collectors.toList());
	        	
	        	List<DSummary> DSummaries=new ArrayList<>();
	        	
	        	DJIAWkSummaryObservable ob = new DJIAWkSummaryObservable();
	        	CandleStickObserver cso = new CandleStickObserver();
	        	ob.addObserver(cso);
	        	
	        	for(int i=0;i<csv.size();i++) {
	        		DSummaries.add(new DSummary(csv.get(i).get(0),csv.get(i).get(1),csv.get(i).get(2),csv.get(i).get(3)));
	        		System.out.println("===Day Summary added===");
	        		System.out.println("Open:"+DSummaries.get(i).getopen()+"\tHigh:"+DSummaries.get(i).gethigh()+"\tLow:"+DSummaries.get(i).getlow()+"\tClose:"+DSummaries.get(i).getclose());
	        		
	        		if (DSummaries.size() == 5) {
		        		WkSummary wksum = new WkSummary(DSummaries.get(DSummaries.size()-1).getopen(),DSummaries.stream().mapToDouble(summary -> summary.gethigh()).max().orElse(0.0),DSummaries.stream().mapToDouble(summary -> summary.getlow()).min().orElse(0.0),DSummaries.get(0).getclose());
		        		ob.addSummary(wksum);
		        		DSummaries.clear();
		        	}
	        	}
	        	
 
	        } catch (Exception ex) {
	        	System.out.println("Csv file not Found");        	
	            //ex.printStackTrace();
	        }
	    }

}
