package edu.umb.cs681.hw02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    	
		/*
		 * double averagePrice = CarsList.stream().map(car->car.getPrice()).reduce(new
		 * CarPriceResultHolder(), (result, price)-> { result.accumulate(price); return
		 * result; },(finalResult, intermediateResult)-> {
		 * finalResult.combine(intermediateResult);return finalResult; }).getaverage();
		 */
    	
    	CarPriceResultHolder resultHolder = CarsList.stream()
                .map(car -> car.getPrice())
                .reduce(new CarPriceResultHolder(), (result, price) -> {
                            result.accumulate(price);
                            return result;
                        },(finalResult, intermediateResult) -> {
                            finalResult.combine(intermediateResult);
                            return finalResult;
                        });    	
    	
        System.out.println("Average of all cars:"+resultHolder.getaverage());	
	}
}
