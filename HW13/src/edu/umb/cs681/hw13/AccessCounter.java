package edu.umb.cs681.hw13;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccessCounter {
	private AccessCounter() {}
	private static AccessCounter instance = null;
	private HashMap<Path, Integer> accessCountMap = new HashMap<>();
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public static AccessCounter getInstance() {
		rwLock.readLock().lock();
		try {
		if (instance == null)
			instance = new AccessCounter();
		return instance;
		} finally {
			rwLock.readLock().unlock();
		}
    }
	
	public void increment(Path path) {
		rwLock.writeLock().lock();
		try {
			if(accessCountMap.containsKey(path)) {
				int count = accessCountMap.get(path);
				accessCountMap.put(path, count+1);
			}
			else {
				accessCountMap.put(path, 1);
			}
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public int getCount(Path path) {
		rwLock.readLock().lock();
		try {
			if(accessCountMap.containsKey(path)) {
				return accessCountMap.get(path);
			}
			else {
				return 0;
			}
		} finally {
			rwLock.readLock().unlock();
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
