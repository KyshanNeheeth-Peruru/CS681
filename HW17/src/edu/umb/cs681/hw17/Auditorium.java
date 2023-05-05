package edu.umb.cs681.hw17;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Auditorium {
	
	private int capacity = 0;
	private ReentrantLock lock = new ReentrantLock();
	private static final ReentrantLock newlock = new ReentrantLock();
	private Condition maxCapacity = lock.newCondition();
	private Condition emptyAudi = lock.newCondition();
	
	public void buyTicket(){
		lock.lock();
		try{
			System.out.println(Thread.currentThread().getId() +" (buy) Capacity: "+capacity);
			while(capacity >= 10){
				maxCapacity.await();
			}
			capacity++;
			System.out.println(Thread.currentThread().getId() +" (buy): new Capacity: "+capacity);
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
			System.out.println(Thread.currentThread().getId() +" (cancel): Capacity: "+capacity);
			while(capacity <= 0){
				emptyAudi.await();
			}
			capacity--;
			System.out.println(Thread.currentThread().getId() + " (cancel): new Capacity: "+capacity);
			maxCapacity.signalAll();
		}
		catch (InterruptedException exception){
			System.out.println(" Thread Interrupted");
		}
		finally{
			lock.unlock();
		}
	}
	
	public double getCapacity() { 
		return this.capacity; 
	}
	
	public void moveSeats(Auditorium audi1, Auditorium audi2) {
		System.out.println(Thread.currentThread().getId() +" (Move): Capacity: "+capacity);
		synchronized(lock) {
	        audi1.cancelTicket();
	        audi2.buyTicket();
	    }
		System.out.println(Thread.currentThread().getId() +" (Move): New Capacity: "+capacity);
		
	}
	
	public static void main(String[] args){
		Auditorium audi = new Auditorium();
		Auditorium audi1 = new Auditorium();
		List<Thread> threads = new ArrayList<>();
		
		BuyTicketRunnable buy = new BuyTicketRunnable(audi);
		CancelTicketRunnable cancel = new CancelTicketRunnable(audi);
		MoveSeatsRunnable move = new MoveSeatsRunnable(audi,audi1);
		
		for(int i = 0; i < 5; i++){
			threads.add(new Thread(buy));
			threads.add(new Thread(cancel));
			threads.add(new Thread(move));
		}
		
		for(Thread t:threads) {
			t.start();
		}
		
		try {
	        Thread.sleep(10);
	    } catch (InterruptedException exception) {
	    	exception.printStackTrace();
	    }
		
		buy.setDone();
		cancel.setDone();
		
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
