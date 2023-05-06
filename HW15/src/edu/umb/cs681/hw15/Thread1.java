package edu.umb.cs681.hw15;

public class Thread1 implements Runnable {
	
	public volatile boolean done = false;
	private final StockQuoteObservable ob;
	
	public Thread1(StockQuoteObservable obs) {
		this.ob=obs;
	}
	
	public void setDone() {
		done=true;
	}
	
	public void run() {
		while(!done) {
			ob.changeQuote("TickOne", (int)(Math.random()*10) + 1);
		}
	}
}
