package edu.umb.cs681.hw16;

public class UnsafeAuditorium {
	
	private int capacity = 0;
	
	public void buyTicket(){
		System.out.println(Thread.currentThread().getId() +" (buy) Capacity: "+capacity);
		capacity++;
		System.out.println(Thread.currentThread().getId() +" (buy): new Capacity: "+capacity);
	}
	
	public void cancelTicket(){
		System.out.println(Thread.currentThread().getId() +" (cancel): Capacity: "+capacity);
		capacity--;
		System.out.println(Thread.currentThread().getId() + " (cancel): new Capacity: "+capacity);
	}
	
	public double getCapacity() { 
		return this.capacity; 
	}
}
