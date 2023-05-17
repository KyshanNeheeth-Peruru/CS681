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
		try {
			tq.put(t, q);
			notifyObservers(new StockEvent(t,q));
		} finally {
			lockTQ.unlock();
		}
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
		
		Thread1 t1 = new Thread1(observable);
		Thread2 t2= new Thread2(observable);
		
		for(int i=0;i<10;i++) {
			threads.add(new Thread(t1));
			threads.add(new Thread(t2));
		}
		
		for (Thread thread : threads) {
	        thread.start();
	    }
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t1.setDone();
		t2.setDone();
		
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
