package edu.umb.cs681.hw03;

import java.util.ArrayList;
import java.util.List;


public class CandleStickObserver implements Observer<DSummary> {
	
	
    private List<DSummary> DSummaries=new ArrayList<>();


    public void update(Observable<DSummary> sender, DSummary event) {
    	
        DSummaries.add(event);
        System.out.println("New day summary added===");
        System.out.println("Open:"+event.getopen()+"\nHigh:"+event.gethigh()+"\nLow:"+event.getlow()+"\nClose:"+event.getclose());
    
        
        if (DSummaries.size() == 5) {   
            WkSummary wksum = new WkSummary(DSummaries.get(0).getopen(),DSummaries.stream().mapToDouble(summary -> summary.gethigh()).max().orElse(0.0),DSummaries.stream().mapToDouble(summary -> summary.getlow()).min().orElse(0.0),DSummaries.get(DSummaries.size()-1).getclose());
            System.out.println("===================Weekly summary==========================");
            System.out.println("Open:"+wksum.getopen()+"\nHigh:"+wksum.gethigh()+"\nLow:"+wksum.getlow()+"\nClose:"+wksum.getclose());
            System.out.println("===========================================================");
            DSummaries.clear();
        }
        
        
    }
    
    

    

	
		

}
