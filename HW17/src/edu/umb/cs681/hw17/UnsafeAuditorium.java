package edu.umb.cs681.hw17;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UnsafeAuditorium {
	
	private int capacity = 0;
	private ReentrantLock lock = new ReentrantLock();
	private ReentrantLock newlock = new ReentrantLock();
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
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	public double getCapacity() { 
		return this.capacity; 
	}
	
	public void moveSeats(UnsafeAuditorium audi1, UnsafeAuditorium audi2) {
		lock.lock();
		newlock.lock();
		try {
			audi1.cancelTicket();
			audi2.buyTicket();
		} finally {
			newlock.unlock();
			lock.unlock();
		}
		
	}
}
