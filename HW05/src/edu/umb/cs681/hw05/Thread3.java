package edu.umb.cs681.hw05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Thread3 implements Runnable {
	
	public void run(){
		
		Path path = Paths.get("bos-housing.csv");
		
		try (Stream<String> lines = Files.lines(path)) 
        {
            List<List<String>> matrix = 
            		lines.map(line -> {
            			return Stream.of(line.split(","))
            					.map(value->value.trim())
            					.map(value -> value.replaceAll("^\"|\"$", ""))
            					.collect(Collectors.toList());})
            		.collect(Collectors.toList());
            		
    		System.out.println("=====================Data Processing #3=====================");
    		
    		long countDP3 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 &&
                    Double.parseDouble(line.get(13)) < 20).count();
    		System.out.println("Number of houses with Price less than 20 and Accesscability to atleast 4 Highways:"+countDP3);
    		
    		double maxTaxDP3 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 && Double.parseDouble(line.get(13)) < 20)
    				.mapToDouble(line -> Double.parseDouble(line.get(9)))
    			    .max().orElse(Double.NaN);
    		System.out.println("Max tax for House price less than 20 and access to atleast 4 Highways:"+maxTaxDP3);
    		
    		double minTaxDP3 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 && Double.parseDouble(line.get(13)) < 20)
    				.mapToDouble(line -> Double.parseDouble(line.get(9)))
    			    .min().orElse(Double.NaN);
    		System.out.println("Min tax for House price less than 20 and access to atleast 4 Highways:"+minTaxDP3);
    		
    		double avgTaxDP3 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 && Double.parseDouble(line.get(13)) < 20)
    				.mapToDouble(line -> Double.parseDouble(line.get(9)))
    			    .average().orElse(Double.NaN);
    		System.out.println("Average tax for Houses with price less than 20 and access to atleast 4 Highways:"+avgTaxDP3);
        } 
        catch (IOException ex) 
        {
        	System.out.println("CSV file not found");
            //ex.printStackTrace();
        }
		
	}
	

}
