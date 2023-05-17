package edu.umb.cs681.hw16;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Auditorium {
	
	private int capacity = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition maxCapacity = lock.newCondition();
	private Condition emptyAudi = lock.newCondition();
	
	public void buyTicket(){
		lock.lock();
		try{
			System.out.println(Thread.currentThread().getId() +" (before buy) Capacity: "+capacity);
			while(capacity >= 10){
				maxCapacity.await();
			}
			capacity++;
			System.out.println(Thread.currentThread().getId() +" (after buy): new Capacity: "+capacity);
			emptyAudi.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	public void cancelTicket(){
		lock.lock();
		try{
			System.out.println(Thread.currentThread().getId() +" (before cancel): Capacity: "+capacity);
			while(capacity <= 0){
				emptyAudi.await();
			}
			capacity--;
			System.out.println(Thread.currentThread().getId() + " (after cancel): new Capacity: "+capacity);
			maxCapacity.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	public double getCapacity() {
		lock.lock();
		try {
			return this.capacity;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args){
		Auditorium audi = new Auditorium();
		List<Thread> threads = new ArrayList<>();
		
		List<BuyTicketRunnable> buyThreads = new ArrayList<>();
		List<CancelTicketRunnable> cancelThreads = new ArrayList<>();
		
		for(int i = 0; i < 15; i++){
			BuyTicketRunnable buy = new BuyTicketRunnable(audi);
			CancelTicketRunnable cancel = new CancelTicketRunnable(audi);
			buyThreads.add(buy);
		    cancelThreads.add(cancel);
			threads.add(new Thread(buy));
			threads.add(new Thread(cancel));
		}
		
		for(Thread t:threads) {
			t.start();
		}
		
		try {
	        Thread.sleep(5000);
	    } catch (InterruptedException exception) {
	    	exception.printStackTrace();
	    }
		
		for (BuyTicketRunnable t : buyThreads) {
		    t.setDone();
		}
		for (CancelTicketRunnable t : cancelThreads) {
		    t.setDone();
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
