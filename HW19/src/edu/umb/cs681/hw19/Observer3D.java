package edu.umb.cs681.hw19;

public class Observer3D implements Observer<StockEvent> {

	public void update(Observable<StockEvent> sender, StockEvent event) {
		System.out.println("Thread:"+Thread.currentThread().getId()+"- 3D Observer: Ticker = "+event.ticker()+" Quote = "+event.quote());	
	}
}
