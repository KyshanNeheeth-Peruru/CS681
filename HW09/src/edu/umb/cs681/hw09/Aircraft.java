package edu.umb.cs681.hw09;

import java.util.concurrent.locks.ReentrantLock;

public class Aircraft {
	
	private Position position;
	private final ReentrantLock lock = new ReentrantLock();
	
	public Aircraft(Position pos){ this.position = pos; }
	public void setPosition(double newLat,double newLong,double newAlt) {
		lock.lock();
		try {
			this.position = this.position.change(newLat, newLong, newAlt);
		}finally {
			lock.unlock();
		}
	}
	
	public Position getPosition() {
		lock.lock();
		try {
			return position;
		}finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		Aircraft aircraft1 = new Aircraft(new Position(1,2,3));
        Aircraft aircraft2 = new Aircraft(new Position(2,4,6));
        Aircraft aircraft3 = new Aircraft(new Position(3,6,9));
        
        Thread thread1 = new Thread(() -> {
            System.out.println("Aircraft 1 Initial position: " + aircraft1.getPosition().coordinate());
            aircraft1.setPosition(10,20,30);
            System.out.println("Aircraft 1 Final position: " + aircraft1.getPosition().coordinate());
        });
        thread1.start();
        
        Thread thread2 = new Thread(() -> {
            System.out.println("Aircraft 2 Initial position:  " + aircraft2.getPosition().coordinate());
            aircraft2.setPosition(20,40,60);
            System.out.println("Aircraft 2 Final position: " + aircraft2.getPosition().coordinate());
        });
        thread2.start();
        
        Thread thread3 = new Thread(() -> {
            System.out.println("Aircraft 3 Initial position: " + aircraft3.getPosition().coordinate());
            aircraft3.setPosition(30,60,90);
            System.out.println("Aircraft 3 Final position: " + aircraft3.getPosition().coordinate());
        });
        thread3.start();
        
        try {
			thread1.join();
			thread2.join();
	        thread3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
