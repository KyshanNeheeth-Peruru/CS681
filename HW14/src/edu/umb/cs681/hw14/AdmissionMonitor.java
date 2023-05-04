package edu.umb.cs681.hw14;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AdmissionMonitor {
	
	private int currentVisitors = 0;
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	private Condition maxPplCondition = rwLock.writeLock().newCondition();
	private Condition noPplCondition = rwLock.writeLock().newCondition();
	
	public void enter() {
		rwLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getId()+" (Before enter): Current visitors: " + currentVisitors);
			while(currentVisitors >= 10) {
				maxPplCondition.await();
			}
			currentVisitors++;
			System.out.println(Thread.currentThread().getId()+" (After enter): Current visitors: " + currentVisitors);
			noPplCondition.signalAll();
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
			while(currentVisitors == 0) {
				noPplCondition.await();
			}
			currentVisitors--;
			System.out.println(Thread.currentThread().getId()+" (After exit): Current visitors: " + currentVisitors);
			maxPplCondition.signalAll();
		} catch(InterruptedException exception){
			exception.printStackTrace();
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
		
		for(int i=0;i<10;i++) {
			Thread ent = new Thread(new EntranceHandler(monitor));
			Thread ext = new Thread(new ExitHandler(monitor));
			Thread sta = new Thread(new StatsHandler(monitor));
			threads.add(ent);
			threads.add(ext);
			threads.add(sta);
		}
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		try {
			Thread.sleep(5000);
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
