package edu.umb.cs681.hw05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Thread1 implements Runnable {
	
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
            		
            		System.out.println("=====================Data Processing #1=====================");
            		long countChas = matrix.stream().skip(1).filter((List<String> line)-> Integer.parseInt(line.get(3)) == 1).count();
            		System.out.println("Number of Houses near Charles River:"+countChas);
            		double maxpriceChas = matrix.stream().skip(1).filter(line -> Integer.parseInt(line.get(3)) == 1).mapToDouble(line -> Double.parseDouble(line.get(13))).max().orElse(Double.NaN);
            		System.out.println("Max price of house near Charles River:"+maxpriceChas);
            		double minpriceChas = matrix.stream().skip(1).filter(line -> Integer.parseInt(line.get(3)) == 1).mapToDouble(line -> Double.parseDouble(line.get(13))).min().orElse(Double.NaN);
            		System.out.println("Min price of house near Charles River:"+minpriceChas);
            		double avgpriceChas = matrix.stream().skip(1).filter(line -> Integer.parseInt(line.get(3)) == 1).mapToDouble(line -> Double.parseDouble(line.get(13))).average().orElse(Double.NaN);
            		System.out.println("Average of prices of Houses near Charles River:"+avgpriceChas);
        } 
        catch (IOException ex) 
        {
        	System.out.println("CSV file not found");
            //ex.printStackTrace();
        }	
	}

}
