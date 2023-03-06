package edu.umb.cs681.hw05;

public class MainThread {
	public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Thread1());
        Thread t2 = new Thread(new Thread2());
        Thread t3 = new Thread(new Thread3());
        Thread t4 = new Thread(new Thread4());

        t1.start();
        t1.join();
        
        t2.start();
        t2.join();
        
        t3.start();
        t3.join();
        
        t4.start();
        t4.join();
        
        
    }

}
