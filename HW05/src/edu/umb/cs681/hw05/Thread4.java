package edu.umb.cs681.hw05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Thread4 implements Runnable {
	
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
            		
            System.out.println("=====================Data Processing #4=====================");
    		
    		long countDP4 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&
    				Integer.parseInt(line.get(3)) == 1).count();
    		System.out.println("Number of houses with age less than 50 and are near Charles river :"+countDP4);
    		
    		double maxPriceDP4 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&Integer.parseInt(line.get(3)) == 1)
    				.mapToDouble(line -> Double.parseDouble(line.get(13)))
    			    .max().orElse(Double.NaN);
    		System.out.println("Max price of house with age less than 50 and near Charles river:"+maxPriceDP4);
    		
    		double minPriceDP4 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&Integer.parseInt(line.get(3)) == 1)
    				.mapToDouble(line -> Double.parseDouble(line.get(13)))
    			    .min().orElse(Double.NaN);
    		System.out.println("Min price of house with age less than 50 and near Charles river:"+minPriceDP4);
    		
    		double avgPriceDP4 = matrix.stream().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&Integer.parseInt(line.get(3)) == 1)
    				.mapToDouble(line -> Double.parseDouble(line.get(13)))
    			    .average().orElse(Double.NaN);
    		System.out.println("Average prices of houses with age less than 50 and near Charles river:"+avgPriceDP4);
        } 
        catch (IOException ex) 
        {
        	System.out.println("CSV file not found");
            //ex.printStackTrace();
        }
		
	}

}
