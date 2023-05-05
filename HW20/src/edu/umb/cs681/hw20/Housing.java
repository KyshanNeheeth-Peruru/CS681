package edu.umb.cs681.hw20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Housing {
	public static void main(String[] args) {
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
            		//System.out.println(matrix.get(1).get(3));
            		
            		System.out.println("Data Processing #1=====================");
            		long countChas = matrix.stream().parallel().skip(1).filter((List<String> line)-> Integer.parseInt(line.get(3)) == 1).count();
            		System.out.println("Number of Houses near Charles River:"+countChas);
            		double maxpriceChas = matrix.stream().parallel().skip(1).filter(line -> Integer.parseInt(line.get(3)) == 1).mapToDouble(line -> Double.parseDouble(line.get(13))).max().orElse(Double.NaN);
            		System.out.println("Max price of house near Charles River:"+maxpriceChas);
            		double minpriceChas = matrix.stream().parallel().skip(1).filter(line -> Integer.parseInt(line.get(3)) == 1).mapToDouble(line -> Double.parseDouble(line.get(13))).min().orElse(Double.NaN);
            		System.out.println("Min price of house near Charles River:"+minpriceChas);
            		double avgpriceChas = matrix.stream().parallel().skip(1).filter(line -> Integer.parseInt(line.get(3)) == 1).mapToDouble(line -> Double.parseDouble(line.get(13))).average().orElse(Double.NaN);
            		System.out.println("Average of prices of Houses near Charles River:"+avgpriceChas);
            		
            		
            		System.out.println("Data Processing #2=====================");
            		List<String> crims = matrix.stream().parallel().skip(1).map(row -> Double.parseDouble(row.get(0))).map(Object::toString).sorted().collect(Collectors.toList());
            		int crim10index = (int) Math.ceil(0.1 * crims.size()) - 1;
            		double crims10thPercentile = Double.parseDouble(crims.get(crim10index));
            		
            		List<String> ptratios = matrix.stream().parallel().skip(1).map(row -> Double.parseDouble(row.get(10))).map(Object::toString).sorted().collect(Collectors.toList());
            		int pt10index = (int) Math.ceil(0.1 * ptratios.size()) - 1;
            		double pts10thPercentile = Double.parseDouble(ptratios.get(pt10index));     
            		
            		long countBoth = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&
                                     Double.parseDouble(line.get(10)) <= pts10thPercentile).count();
            		System.out.println("Number of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+countBoth);
            		
            		System.out.println("Prices======");
            		double maxBothPrices = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(13)))
            			    .max().orElse(Double.NaN);
            		System.out.println("Maximum price of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+maxBothPrices);
            		double minBothPrices = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(13)))
            			    .min().orElse(Double.NaN);
            		System.out.println("Minimum price of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+minBothPrices);
            		double avgBothPrices = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(13)))
            			    .average().orElse(Double.NaN);
            		System.out.println("Average price of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+avgBothPrices);
            		 		
            		System.out.println("NOX Concentration======");
            		double maxBothNox = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(4)))
            			    .max().orElse(Double.NaN);
            		System.out.println("Maximum Nox Concentration of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+maxBothNox);
            		double minBothNox = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(4)))
            			    .min().orElse(Double.NaN);
            		System.out.println("Minimum Nox Concentration of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+minBothNox);
            		double avgBothNox = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(4)))
            			    .average().orElse(Double.NaN);
            		System.out.println("Average Nox Concentration of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+avgBothNox);
            		
            		System.out.println("# of Rooms======");
            		double maxBothrm = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(5)))
            			    .max().orElse(Double.NaN);
            		System.out.println("Maximum # of Rooms of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+maxBothrm);
            		double minBothrm = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(5)))
            			    .min().orElse(Double.NaN);
            		System.out.println("Minimum # of Rooms of House in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+minBothrm);
            		double avgBothrm = matrix.stream().parallel().skip(1)
            			    .filter(line -> Double.parseDouble(line.get(0)) <= crims10thPercentile &&Double.parseDouble(line.get(10)) <= pts10thPercentile)
            			    .mapToDouble(line -> Double.parseDouble(line.get(5)))
            			    .average().orElse(Double.NaN);
            		System.out.println("Average # of Rooms of Houses in Top 10%(lowest) of Crime rate and Pupil-Teacher ratio:"+avgBothrm);
            		
            		System.out.println("Data Processing #3=====================");
            		
            		long countDP3 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 &&
                            Double.parseDouble(line.get(13)) < 20).count();
            		System.out.println("Number of houses with Price less than 20 and Accesscability to atleast 4 Highways:"+countDP3);
            		
            		double maxTaxDP3 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 && Double.parseDouble(line.get(13)) < 20)
            				.mapToDouble(line -> Double.parseDouble(line.get(9)))
            			    .max().orElse(Double.NaN);
            		System.out.println("Max tax for House price less than 20 and access to atleast 4 Highways:"+maxTaxDP3);
            		
            		double minTaxDP3 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 && Double.parseDouble(line.get(13)) < 20)
            				.mapToDouble(line -> Double.parseDouble(line.get(9)))
            			    .min().orElse(Double.NaN);
            		System.out.println("Min tax for House price less than 20 and access to atleast 4 Highways:"+minTaxDP3);
            		
            		double avgTaxDP3 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(8)) >= 4 && Double.parseDouble(line.get(13)) < 20)
            				.mapToDouble(line -> Double.parseDouble(line.get(9)))
            			    .average().orElse(Double.NaN);
            		System.out.println("Average tax for Houses with price less than 20 and access to atleast 4 Highways:"+avgTaxDP3);
            		
            		
            		System.out.println("Data Processing #4=====================");
            		
            		long countDP4 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&
            				Integer.parseInt(line.get(3)) == 1).count();
            		System.out.println("Number of houses with age less than 50 and are near Charles river :"+countDP4);
            		
            		double maxPriceDP4 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&Integer.parseInt(line.get(3)) == 1)
            				.mapToDouble(line -> Double.parseDouble(line.get(13)))
            			    .max().orElse(Double.NaN);
            		System.out.println("Max price of house with age less than 50 and near Charles river:"+maxPriceDP4);
            		
            		double minPriceDP4 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&Integer.parseInt(line.get(3)) == 1)
            				.mapToDouble(line -> Double.parseDouble(line.get(13)))
            			    .min().orElse(Double.NaN);
            		System.out.println("Min price of house with age less than 50 and near Charles river:"+minPriceDP4);
            		
            		double avgPriceDP4 = matrix.stream().parallel().skip(1).filter(line -> Double.parseDouble(line.get(6)) < 50 &&Integer.parseInt(line.get(3)) == 1)
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
