package edu.umb.cs681.hw14;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AdmissionMonitor {
	
	private int currentVisitors = 0;
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	private Condition maxPplCondition = rwLock.writeLock().newCondition();
	
	public void enter() {
		rwLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getId()+" (Before enter): Current visitors: " + currentVisitors);
			while(currentVisitors >= 10) {
				maxPplCondition.await();
			}
			currentVisitors++;
			System.out.println(Thread.currentThread().getId()+" (After enter): Current visitors: " + currentVisitors);
		} catch(InterruptedException exception){
			exception.printStackTrace();
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public void exit() {
		rwLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getId()+" (Before exit): Current visitors: " + currentVisitors);

			currentVisitors--;
			System.out.println(Thread.currentThread().getId()+" (After exit): Current visitors: " + currentVisitors);
			maxPplCondition.signalAll();
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public int countCurrentVisitors() {
		rwLock.readLock().lock();
		try {
			return currentVisitors;
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	public static void main(String[] args) {
		AdmissionMonitor monitor = new AdmissionMonitor();
		List<Thread> threads = new ArrayList<>();
		
		EntranceHandler ent = new EntranceHandler(monitor);
		ExitHandler ext = new ExitHandler(monitor);
		StatsHandler sta = new StatsHandler(monitor);
		
		for(int i=0;i<10;i++) {
			threads.add(new Thread(ent));
			threads.add(new Thread(ext));
			threads.add(new Thread(sta));
		}
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		ent.setDone();
		ext.setDone();
		sta.setDone();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(Thread t:threads) {
	        t.interrupt();
	    }

	    for(Thread t:threads) {
	        try {
	            t.join();
	        } catch (InterruptedException exception) {
	        	exception.printStackTrace();
	        }
	    }
	    
	    System.out.println("Done");
		
	}

}
