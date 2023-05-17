package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


public class AccessCounter {
	
	private static AccessCounter instance = null;
	private static ReentrantLock slock = new ReentrantLock();
	private ConcurrentHashMap<Path, Integer> accessCountMap = new ConcurrentHashMap<>();
	
	public static AccessCounter getInstance() {
		slock.lock();
		try {
		if (instance == null)
			instance = new AccessCounter();
		return instance;
		} finally {
			slock.unlock();
		}
    }
	
	public void increment(Path path) {
		accessCountMap.compute(path, (key, value) -> (value == null) ? 1 : value + 1);
	}
	
	public int getCount(Path path) {
		return accessCountMap.get(path);
	}
	
	public static void main(String[] args) {
		List<Thread> threads = new ArrayList<>();
		List<RequestHandler> handlers = new ArrayList<>();
		for(int i=0;i<10;i++) {
			RequestHandler handler = new RequestHandler();
			Thread thread = new Thread(handler);
			thread.start();
			threads.add(thread);
			handlers.add(handler);
		}
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	    for (RequestHandler handler : handlers) {
	    	handler.setdone();
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
