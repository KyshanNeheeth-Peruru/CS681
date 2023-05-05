package edu.umb.cs681.hw19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class StockQuoteObservable extends Observable<StockEvent> {
	
	private ReentrantLock lockTQ = new ReentrantLock();
	
	Map<String, Double> tq = new HashMap<String,Double>();
	public void changeQuote(String t, double q) {
		lockTQ.lock();
		tq.put(t, q);
		lockTQ.unlock();
		notifyObservers(new StockEvent(t,q));
	}
	
	public static void main(String[] args) {
		List<Thread> threads = new ArrayList<>();
		
		StockQuoteObservable observable = new StockQuoteObservable();
		LineChartObserver lcobs = new LineChartObserver();
		Observer3D obs3D = new Observer3D();
		TableObserver tableobs = new TableObserver();
		observable.addObserver(lcobs);
		observable.addObserver(obs3D);
		observable.addObserver(tableobs);
		
		for(int i=0;i<10;i++) {
			Thread thread = new Thread(() -> {
	            for (int j = 0; j < 10; j++) {
	            	observable.changeQuote("Tick", (int) (Math.random() * 10) + 1);
	                try {
	                    Thread.sleep(100);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        threads.add(thread);	
		}
		
		for (Thread thread : threads) {
	        thread.start();
	    }
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (Thread thread : threads) {
	        thread.interrupt();
	    }
		
		for (Thread thread : threads) {
	        try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
		
		System.out.println("Done");
	}
}
