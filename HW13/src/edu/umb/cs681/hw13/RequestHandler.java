package edu.umb.cs681.hw13;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class RequestHandler implements Runnable {
	
	private volatile boolean done = false;
	AccessCounter accessCounter = AccessCounter.getInstance();
	
	public void setdone() {
		done = true;
	}
	public boolean isdone() {
		return done;
	}
	public void run() {
		Random rand = new Random();
		while(!isdone()) {
			char fileName = (char)(rand.nextInt(4)+'a');
			Path path = Paths.get(fileName+".html");
            accessCounter.increment(path);
            int count = accessCounter.getCount(path);
            System.out.println("Thread "+Thread.currentThread().getId()+": "+path+" was accessed total of "+count+" times.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            	System.out.println("Thread "+Thread.currentThread().getId()+" interrupted.");
	    		return;
            }
        }
	}
}
