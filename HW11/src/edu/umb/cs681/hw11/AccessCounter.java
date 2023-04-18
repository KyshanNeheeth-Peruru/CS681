package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
	private static AccessCounter instance = null;
	private HashMap<Path, Integer> accessCountMap = new HashMap<>();
	private static ReentrantLock lock = new ReentrantLock();
	
	public static AccessCounter getInstance() {
		lock.lock();
		try {
		if (instance == null)
			instance = new AccessCounter();
			return instance;
		} finally {
			lock.unlock();
		}
    }
	
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
		 for(int i = 0; i < 10; i++) {
			 RequestHandler handler = new RequestHandler();
		     Thread thread = new Thread(handler);
		     thread.start();
		     try {
				 Thread.sleep(1000);
			 } catch (InterruptedException e) {
				 e.printStackTrace(); 
			 }
		     handler.setdone();
		     try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}
}
