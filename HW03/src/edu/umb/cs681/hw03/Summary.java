package edu.umb.cs681.hw03;

public abstract class Summary {
	private double open;
	private double close;
	private double high;
	private double low;
	
	public Summary(double open, double high, double low, double close) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }
	
	public void setopen(double open) {
		this.open=open;
	}
	
	public void setclose(double close) {
		this.close=close;
	}
	
	public void sethigh(double high) {
		this.high=high;
	}
	
	public void setlow(double low) {
		this.low=low;
	}
	
	public double getopen() {
		return this.open;
	}
	
	public double getclose() {
		return this.close;
	}
	
	public double gethigh() {
		return this.high;
	}
	
	public double getlow() {
		return this.low;
	}
	

}
