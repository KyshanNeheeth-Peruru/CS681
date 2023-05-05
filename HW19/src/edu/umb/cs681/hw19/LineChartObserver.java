package edu.umb.cs681.hw19;

public class LineChartObserver implements Observer<StockEvent> {

	public void update(Observable<StockEvent> sender, StockEvent event) {

		System.out.println("LineChart Observer: Ticker = "+event.ticker()+" Quote = "+event.quote());

	}
}
