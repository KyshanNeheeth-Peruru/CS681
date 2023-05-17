package edu.umb.cs681.hw15;

public class Thread2 implements Runnable {
	
	public volatile boolean done = false;
	private final StockQuoteObservable ob;
	
	public Thread2(StockQuoteObservable obs) {
		this.ob=obs;
	}
	
	public void setDone() {
		done=true;
	}
	
	public void run() {
		while(!done) {
			ob.changeQuote(" TickTwo", (int)(Math.random()*10) + 1);
			try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            	System.out.println("Thread "+Thread.currentThread().getId()+" interrupted.");
	    		return;
            }
		}
	}

}
