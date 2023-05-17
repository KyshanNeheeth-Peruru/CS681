package edu.umb.cs681.hw17;

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
			System.out.println(Thread.currentThread().getId() +" (buy) Capacity: "+capacity);
			while(capacity >= 10){
				try {
					maxCapacity.await();
				} catch (InterruptedException e) {
					System.out.println("Thread "+Thread.currentThread().getId()+" interrupted.");
		    		return;
				}
			}
			capacity++;
			System.out.println(Thread.currentThread().getId() +" (buy): new Capacity: "+capacity);
			emptyAudi.signalAll();
		} finally{
			lock.unlock();
		}
	}
	
	public void cancelTicket(){
		lock.lock();
		try{
			System.out.println(Thread.currentThread().getId() +" (cancel): Capacity: "+capacity);
			while(capacity <= 0){
				try {
					emptyAudi.await();
				} catch (InterruptedException e) {
					System.out.println("Thread "+Thread.currentThread().getId()+" interrupted.");
		    		return;
				}
			}
			capacity--;
			System.out.println(Thread.currentThread().getId() + " (cancel): new Capacity: "+capacity);
			maxCapacity.signalAll();
		} finally{
			lock.unlock();
		}
	}
	
	public int getCapacity() {
		lock.lock();
		try {
			return this.capacity;
		} finally {
			lock.unlock();
		}
	}
	
	public void moveSeats(Auditorium audi2) {
		System.out.println(Thread.currentThread().getId() +" (Move): Capacity: "+getCapacity());
		cancelTicket();
		lock.lock();
	    try {
	    	audi2.buyTicket();
	    } finally {
	        lock.unlock();
	    }
	    
		System.out.println(Thread.currentThread().getId() +" (Move): New Capacity: "+getCapacity());
		
	}
	
	public static void main(String[] args){
		Auditorium audi = new Auditorium();
		Auditorium audi1 = new Auditorium();
		List<Thread> threads = new ArrayList<>();
		
		List<BuyTicketRunnable> buyThreads = new ArrayList<>();
		List<CancelTicketRunnable> cancelThreads = new ArrayList<>();
		List<MoveSeatsRunnable> moveThreads = new ArrayList<>();
		
		
		for(int i = 0; i < 10; i++){
			BuyTicketRunnable buy = new BuyTicketRunnable(audi);
			CancelTicketRunnable cancel = new CancelTicketRunnable(audi);
			MoveSeatsRunnable move = new MoveSeatsRunnable(audi,audi1);
			
			buyThreads.add(buy);
		    cancelThreads.add(cancel);
		    moveThreads.add(move);
			
			threads.add(new Thread(buy));
			threads.add(new Thread(cancel));
			threads.add(new Thread(move));
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
		for (MoveSeatsRunnable t : moveThreads) {
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
