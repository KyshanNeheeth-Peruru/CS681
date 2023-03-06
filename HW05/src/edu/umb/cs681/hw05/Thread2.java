package edu.umb.cs681.hw05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Thread2 implements Runnable {
	
	public void run(){
		
		Path path = Paths.get("C:\\Users\\kisha\\Downloads\\bos-housing.csv");
		
		try (Stream<String> lines = Files.lines(path)) 
        {
            List<List<String>> matrix = 
            		lines.map(line -> {
            			return Stream.of(line.split(","))
            					.map(value->value.trim())
            					.map(value -> value.replaceAll("^\"|\"$", ""))
            					.collect(Collectors.toList());})
            		.collect(Collectors.toList());
            		
    		System.out.println("Data Processing #2=====================");
    		List<String> crims = matrix.stream().skip(1).map(row -> Double.parseDouble(row.get(0))).map(Object::toString).sorted().collect(Collectors.toList());
    		int crim10index = (int) Math.ceil(0.1 * crims.size()) - 1;
    		double crims10thPercentile = Double.parseDouble(crims.get(crim10index));
    		
    		List<String> ptratios = matrix.stream().skip(1).map(row -> Double.parseDouble(row.get(10))).map(Object::toString).sorted().collect(Collectors.toList());
    		int pt10index = (int) Math.ceil(0.1 * ptratios.size()) - 1;
    		double pts10thPercentile = Double.parseDouble(ptratios.get(pt10index));     
    		
    		long countBoth = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&
                             Double.parseDouble(line.get(10)) <= pts10thPercentile).count();
    		System.out.println("Number of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+countBoth);
    		
    		System.out.println("Prices======");
    		double maxBothPrices = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(13)))
    			    .max().orElse(Double.NaN);
    		System.out.println("Maximum price of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+maxBothPrices);
    		double minBothPrices = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(13)))
    			    .min().orElse(Double.NaN);
    		System.out.println("Minimum price of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+minBothPrices);
    		double avgBothPrices = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(13)))
    			    .average().orElse(Double.NaN);
    		System.out.println("Average price of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+avgBothPrices);
    		 		
    		System.out.println("NOX Concentration======");
    		double maxBothNox = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(4)))
    			    .max().orElse(Double.NaN);
    		System.out.println("Maximum Nox Concentration of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+maxBothNox);
    		double minBothNox = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(4)))
    			    .min().orElse(Double.NaN);
    		System.out.println("Minimum Nox Concentration of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+minBothNox);
    		double avgBothNox = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(4)))
    			    .average().orElse(Double.NaN);
    		System.out.println("Average Nox Concentration of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+avgBothNox);
    		
    		System.out.println("# of Rooms======");
    		double maxBothrm = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(5)))
    			    .max().orElse(Double.NaN);
    		System.out.println("Maximum # of Rooms of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+maxBothrm);
    		double minBothrm = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(5)))
    			    .min().orElse(Double.NaN);
    		System.out.println("Minimum # of Rooms of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+minBothrm);
    		double avgBothrm = matrix.stream().skip(1)
    			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
    			    .mapToDouble(line -> Double.parseDouble(line.get(5)))
    			    .average().orElse(Double.NaN);
    		System.out.println("Average # of Rooms of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+avgBothrm);
        } 
        catch (IOException ex) 
        {
        	System.out.println("CSV file not found");
            //ex.printStackTrace();
        }
		
	}

}
