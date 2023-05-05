package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHandler implements Runnable {
	
	private volatile boolean done = false;
	AccessCounter accessCounter = AccessCounter.getInstance();
	private ReentrantLock lock = new ReentrantLock();
	
	public void setdone() {
		done = true;
	}
	public boolean isdone() {
		return done;
	}
	public void run() {
		Random rand = new Random();
		while (!isdone()) {
			lock.lock();
			try {
			char fileName = (char)(rand.nextInt(4)+'a');
			Path path = Paths.get(fileName+".html");
            accessCounter.increment(path);
            int count = accessCounter.getCount(path);
            System.out.println("Thread "+Thread.currentThread().getId()+" accessed "+path+" "+count+" times.");
			}finally {
				lock.unlock();
			}
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            	System.out.println("Thread "+Thread.currentThread().getId()+" interrupted");
                break;
            }
        }
	}
}
