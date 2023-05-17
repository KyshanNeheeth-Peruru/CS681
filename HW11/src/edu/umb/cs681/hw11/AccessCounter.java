package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
	private static AccessCounter instance = null;
	private HashMap<Path, Integer> accessCountMap = new HashMap<>();
	private static ReentrantLock slock = new ReentrantLock();
	private ReentrantLock lock = new ReentrantLock();
	
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
	
	private AccessCounter() {}
	
	public void increment(Path path) {
		lock.lock();
		try {
			if(accessCountMap.containsKey(path)) {
				int count = accessCountMap.get(path);
				accessCountMap.put(path, count+1);
			}
			else {
				accessCountMap.put(path, 1);
			}
		} finally {
			lock.unlock();
		}
	}
	
	public int getCount(Path path) {
		lock.lock();
		try {
			if(accessCountMap.containsKey(path)) {
				return accessCountMap.get(path);
			}
			else {
				return 0;
			}
		} finally {
			lock.unlock();
		}
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
			Thread.sleep(5000);
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
