package edu.umb.cs681.hw01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Car {
	private String make;
	private String model;
	private int mileage;
	private int year;
	private float price;
	private int dominationCount;
	
	public Car(String make, String model, int year, int mileage, float price) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.mileage = mileage;
		this.price = price;
	}
	
	public String getMake() {
		return make;
	}
	public String getModel() {
		return model;
	}
	public int getMileage() {
		return mileage;
	}
	public int getYear() {
		return year;
	}
	public float getPrice() {
		return price;
	}
	
	public void setDominationCount(ArrayList<Car> cars) {
		int count = 0;
        for (Car car : cars) {
            if (!car.equals(this))
            {
                if ((this.getYear() >= car.getYear()) && (this.getMileage() <= car.getMileage()) && (this.getPrice() <= car.getPrice()))
                {
                    if ((this.getYear() > car.getYear()) || (this.getMileage() < car.getMileage()) || (this.getPrice() < car.getPrice()))
                    {
                        count++;
                    }
                }
            }
        }
        this.dominationCount = count;
    }

    public int getDominationCount() {
        return this.dominationCount;
    }

    public static void main(String[] args) {
    	ArrayList<Car> CarsList = new ArrayList<Car>();
    	CarsList.add(new Car("A", "aa", 2015, 75, 875325));
    	CarsList.add(new Car("B", "bb", 2020, 50, 225000));
    	CarsList.add(new Car("C", "cc", 2022, 20, 100000));
    	CarsList.add(new Car("D", "dd", 2010, 80, 925000));
    	CarsList.add(new Car("E", "ee", 2012, 60, 175000));
    	CarsList.forEach(car -> car.setDominationCount(CarsList));
    	//CarsList.forEach(car -> System.out.println(car.getDominationCount()));
    	
    	System.out.println("Order by year:");
    	ArrayList<Car> orderByYear=(ArrayList<Car>) CarsList.stream().sorted((Car c1, Car c2)->c1.getYear()-c2.getYear()).collect(Collectors.toList());
    	orderByYear.forEach(car -> System.out.print(car.getMake()));
    	System.out.println();
    	ArrayList<Car> HighyearCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getYear()>2016).collect(Collectors.toList());
    	int countHighYearCars = (int) HighyearCars.stream().count();
    	System.out.println("Number of High year cars(>2016):"+countHighYearCars);
    	ArrayList<Car> LowyearCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getYear()<2016).collect(Collectors.toList());
    	int countLowYearCars = (int) LowyearCars.stream().count();
    	System.out.println("Number of Low year cars(<2016):"+countLowYearCars);
    	
    	Integer sumHighYears = HighyearCars.stream().map((Car car)->car.getYear()).reduce(0,(sum,year)-> sum+year);
    	int avgHighYears= sumHighYears/countHighYearCars;
    	System.out.println("Average of High years:"+avgHighYears);
    	
    	Integer sumLowYears = LowyearCars.stream().map((Car car)->car.getYear()).reduce(0,(sum,year)-> sum+year);
    	int avgLowYears= sumLowYears/countLowYearCars;
    	System.out.println("Average of Low years:"+avgLowYears);
       	
    	Optional<Integer> maxHighYear = HighyearCars.stream().map((Car car)->car.getYear()).max(Comparator.comparing(year->year));
    	if(maxHighYear.isPresent()) {
    		System.out.println("Max year in High:"+maxHighYear.get());
    	}
    	Optional<Integer> minHighYear = HighyearCars.stream().map((Car car)->car.getYear()).min(Comparator.comparing(year->year));
    	if(minHighYear.isPresent()) {
    		System.out.println("Min year in High:"+minHighYear.get());
    	}
    	Optional<Integer> maxLowYear = LowyearCars.stream().map((Car car)->car.getYear()).max(Comparator.comparing(year->year));
    	if(maxLowYear.isPresent()) {
    		System.out.println("Max year in Low:"+maxLowYear.get());
    	}
    	Optional<Integer> minLowYear = LowyearCars.stream().map((Car car)->car.getYear()).min(Comparator.comparing(year->year));
    	if(minLowYear.isPresent()) {
    		System.out.println("Min year in Low:"+minLowYear.get());
    	}
    	
    	System.out.println("================================================================================");
    	
    	System.out.println("Order by Price:");
    	//ArrayList<Car> orderByPrice=(ArrayList<Car>) CarsList.stream().map((Car car)->car.getPrice()).sorted((Float price1, Float price2)-> price1-price2).collect(Collectors.toList());
    	ArrayList<Car> orderByPrice=(ArrayList<Car>) CarsList.stream().sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toList());
    	orderByPrice.forEach(car -> System.out.print(car.getMake()));
    	System.out.println();
    	ArrayList<Car> HighPriceCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getPrice()>200000).collect(Collectors.toList());
    	int countHighPriceCars = (int) HighPriceCars.stream().count();
    	System.out.println("Number of High Price cars(>200000):"+countHighPriceCars);
    	ArrayList<Car> LowPriceCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getPrice()<200000).collect(Collectors.toList());
    	int countLowPriceCars = (int) LowPriceCars.stream().count();
    	System.out.println("Number of Low Price cars(<200000):"+countLowPriceCars);
    	
    	Float sumHighPrices = HighPriceCars.stream().map((Car car)->car.getPrice()).reduce((float) 0,(sum,price)-> sum+price);
    	Float avgHighPrices= sumHighPrices/countHighPriceCars;
    	System.out.println("Average of High Prices:"+avgHighPrices);
    	Float sumLowPrices = LowPriceCars.stream().map((Car car)->car.getPrice()).reduce((float)0,(sum,price)-> sum+price);
    	Float avgLowPrices= sumLowPrices/countLowPriceCars;
    	System.out.println("Average of Low Prices:"+avgLowPrices);
    	
    	Optional<Float> maxHighPrice = HighPriceCars.stream().map((Car car)->car.getPrice()).max(Comparator.comparing(price->price));
    	if(maxHighPrice.isPresent()) {
    		System.out.println("Max Price in High:"+maxHighPrice.get());
    	}
    	Optional<Float> minHighPrice = HighPriceCars.stream().map((Car car)->car.getPrice()).min(Comparator.comparing(price->price));
    	if(minHighPrice.isPresent()) {
    		System.out.println("Min Price in High:"+minHighPrice.get());
    	}
    	Optional<Float> maxLowPrice = LowPriceCars.stream().map((Car car)->car.getPrice()).max(Comparator.comparing(price->price));
    	if(maxLowPrice.isPresent()) {
    		System.out.println("Max Price in Low:"+maxLowPrice.get());
    	}
    	Optional<Float> minLowPrice = LowPriceCars.stream().map((Car car)->car.getPrice()).min(Comparator.comparing(price->price));
    	if(minLowPrice.isPresent()) {
    		System.out.println("Min Price in Low:"+minLowPrice.get());
    	}
    	
    	System.out.println("================================================================================");
    	
    	System.out.println("Order by Mileage:");
    	ArrayList<Car> orderByMileage=(ArrayList<Car>) CarsList.stream().sorted(Comparator.comparing((Car car)-> car.getMileage())).collect(Collectors.toList());
    	orderByMileage.forEach(car -> System.out.print(car.getMake()));
    	System.out.println();
    	
    	ArrayList<Car> HighmilCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getMileage()>55).collect(Collectors.toList());
    	int countHighMilCars = (int) HighmilCars.stream().count();
    	System.out.println("Number of High Mileage cars(>55):"+countHighMilCars);
    	ArrayList<Car> LowMilCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getMileage()<55).collect(Collectors.toList());
    	int countLowMilCars = (int) LowMilCars.stream().count();
    	System.out.println("Number of Low Mileafe cars(<55):"+countLowMilCars);
    	
    	Integer sumHighMiles = HighmilCars.stream().map((Car car)->car.getMileage()).reduce(0,(sum,miles)-> sum+miles);
    	int avgHighMiles= sumHighMiles/countHighMilCars;
    	System.out.println("Average of High Mileages:"+avgHighMiles);
    	
    	Integer sumLowMiles = LowMilCars.stream().map((Car car)->car.getMileage()).reduce(0,(sum,miles)-> sum+miles);
    	int avgLowMiles= sumLowMiles/countLowMilCars;
    	System.out.println("Average of Low Mileages:"+avgLowMiles);
       	
    	Optional<Integer> maxHighMileage = HighmilCars.stream().map((Car car)->car.getMileage()).max(Comparator.comparing(mile->mile));
    	if(maxHighMileage.isPresent()) {
    		System.out.println("Max Mileage in High:"+maxHighMileage.get());
    	}
    	Optional<Integer> minHighMileage = HighmilCars.stream().map((Car car)->car.getMileage()).min(Comparator.comparing(mile->mile));
    	if(minHighMileage.isPresent()) {
    		System.out.println("Min Mileage in High:"+minHighMileage.get());
    	}
    	Optional<Integer> maxLowMileage = LowMilCars.stream().map((Car car)->car.getMileage()).max(Comparator.comparing(mile->mile));
    	if(maxLowMileage.isPresent()) {
    		System.out.println("Max Mileage in Low:"+maxLowMileage.get());
    	}
    	Optional<Integer> minLowMileage = LowMilCars.stream().map((Car car)->car.getMileage()).min(Comparator.comparing(mile->mile));
    	if(minLowMileage.isPresent()) {
    		System.out.println("Min Mileage in Low:"+minLowMileage.get());
    	}
    	
    	
    	
    	System.out.println("================================================================================");
    	
    	System.out.println("Order by Domincation COunt:");
    	ArrayList<Car> orderByDC=(ArrayList<Car>) CarsList.stream().sorted(Comparator.comparing(Car::getDominationCount)).collect(Collectors.toList());
    	orderByDC.forEach(car -> System.out.print(car.getMake()));
    	System.out.println();
    	
    	ArrayList<Car> HighDCCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getDominationCount()>=2).collect(Collectors.toList());
    	int countHighDCCars = (int) HighDCCars.stream().count();
    	System.out.println("Number of High Domination count cars(>=2):"+countHighDCCars);
    	ArrayList<Car> LowDCCars =(ArrayList<Car>) CarsList.stream().filter((Car car)-> car.getDominationCount()<2).collect(Collectors.toList());
    	int countLowDCCars = (int) LowDCCars.stream().count();
    	System.out.println("Number of Low Domination count cars(<2):"+countLowDCCars);
    	
    	Integer sumHighDCs = HighDCCars.stream().map((Car car)->car.getDominationCount()).reduce(0,(sum,dc)-> sum+dc);
    	int avgHighDCs= sumHighDCs/countHighDCCars;
    	System.out.println("Average of High years:"+avgHighDCs);
    	
    	Integer sumLowDCs = LowDCCars.stream().map((Car car)->car.getDominationCount()).reduce(0,(sum,dc)-> sum+dc);
    	int avgLowDCs= sumLowDCs/countLowDCCars;
    	System.out.println("Average of Low years:"+avgLowDCs);
       	
    	Optional<Integer> maxHighDC = HighDCCars.stream().map((Car car)->car.getDominationCount()).max(Comparator.comparing(dc->dc));
    	if(maxHighDC.isPresent()) {
    		System.out.println("Max Domination Count in High:"+maxHighDC.get());
    	}
    	Optional<Integer> minHighDC = HighDCCars.stream().map((Car car)->car.getDominationCount()).min(Comparator.comparing(dc->dc));
    	if(minHighDC.isPresent()) {
    		System.out.println("Min Domination Count in High:"+minHighDC.get());
    	}
    	Optional<Integer> maxLowDC = LowDCCars.stream().map((Car car)->car.getDominationCount()).max(Comparator.comparing(dc->dc));
    	if(maxLowDC.isPresent()) {
    		System.out.println("Max Domination Count in Low:"+maxLowDC.get());
    	}
    	Optional<Integer> minLowDC = LowDCCars.stream().map((Car car)->car.getDominationCount()).min(Comparator.comparing(dc->dc));
    	if(minLowDC.isPresent()) {
    		System.out.println("Min Domination Count in Low:"+minLowDC.get());
    	}
    	
	}
}
