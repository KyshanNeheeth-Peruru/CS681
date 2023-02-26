package edu.umb.cs681.hw02;

public class CarPriceResultHolder {
	private int numCarExamined;
	private double average;
	
	public CarPriceResultHolder() {
		this.numCarExamined = 0;
		this.average = 0;
	}
	
	public void accumulate(double price) {
        this.numCarExamined++;
        this.average = ((this.numCarExamined-1)*this.average+price)/this.numCarExamined;
    }
	
	public void combine(CarPriceResultHolder res) {
        int numofCarsExamined = this.numCarExamined+res.numCarExamined;
        this.average = (this.numCarExamined*this.average+res.numCarExamined*res.average)/numofCarsExamined;
        this.numCarExamined = numofCarsExamined;
    }
	
	public int getnumCarExamined() {
		return this.numCarExamined;
	}
	
	public double getaverage() {
		return this.average;
	}

}
