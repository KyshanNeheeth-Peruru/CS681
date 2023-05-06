package edu.umb.cs681.hw19;

public class TableObserver implements Observer<StockEvent> {

	public void update(Observable<StockEvent> sender, StockEvent event) {
		System.out.println("Thread:"+Thread.currentThread().getId()+"- Table Observer: Ticker = "+event.ticker()+" Quote = "+event.quote());	
	}
}
