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
    	
    	System.out.println("Order by Mileage:");
    	ArrayList<Car> orderByMileage=(ArrayList<Car>) CarsList.stream().sorted(Comparator.comparing((Car car)-> car.getMileage())).collect(Collectors.toList());
    	orderByMileage.forEach(car -> System.out.print(car.getMake()));
    	System.out.println();
    	
    	System.out.println("Order by Domincation COunt:");
    	ArrayList<Car> orderByDC=(ArrayList<Car>) CarsList.stream().sorted(Comparator.comparing(Car::getDominationCount)).collect(Collectors.toList());
    	orderByDC.forEach(car -> System.out.print(car.getMake()));
    	
	}
}
