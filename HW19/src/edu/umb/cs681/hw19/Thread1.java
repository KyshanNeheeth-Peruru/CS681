package edu.umb.cs681.hw19;

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
			try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            	System.out.println(Thread.currentThread().getId()+" interrupted.");
	    		return;
            }
		}
	}
}
