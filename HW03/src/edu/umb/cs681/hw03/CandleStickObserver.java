package edu.umb.cs681.hw03;


public class CandleStickObserver implements Observer<WkSummary> {

	public void update(Observable<WkSummary> w, WkSummary wksum) {	
        System.out.println("===================Weekly summary==========================");
        System.out.println("Open:"+wksum.getopen()+"\nHigh:"+wksum.gethigh()+"\nLow:"+wksum.getlow()+"\nClose:"+wksum.getclose());
        System.out.println("===========================================================");    		
	}
}
