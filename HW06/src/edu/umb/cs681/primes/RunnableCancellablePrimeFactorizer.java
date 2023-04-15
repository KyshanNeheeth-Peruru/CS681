package edu.umb.cs681.primes;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {
	private boolean done = false;
	private ReentrantLock lock = new ReentrantLock();

	public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
		super(dividend, from, to);
	}
	
	public void setDone() {
		lock.lock();
		try {
			done = true;
		} finally {
			lock.unlock();
		}	
	}
	
	public void generatePrimeFactors() {
		long divisor = 2;
	    while( dividend != 1 && divisor <= to ){
	    	lock.lock();
	    	try {
	    		if(done) {
	    			System.out.println("Stopped....");
	    			break;
	    		}
	    		if(dividend % divisor == 0) {
			        factors.add(divisor);
			        dividend /= divisor;
			    }else {
			    	if(divisor==2){ divisor++; }
			    	else{ divisor += 2; }
			    }
	    	} finally {
	    		lock.unlock();
	    	}
		}
	}
	
	public void run() {
		generatePrimeFactors();
	}
	public static void main(String[] args) {
		System.out.println("==========Runnable Cancellable Prime Factorizer==========");
		RunnablePrimeFactorizer runnable36 = new RunnablePrimeFactorizer(36, 2, (long)Math.sqrt(36));
		RunnablePrimeFactorizer runnable84 = new RunnablePrimeFactorizer(84, 2, (long)Math.sqrt(84));
		Thread thread1 = new Thread(runnable36);
		Thread thread2 = new Thread(runnable84);
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Final result: " + runnable36.getPrimeFactors() + "\n");
		System.out.println("Final result: " + runnable84.getPrimeFactors() + "\n");	
	}

}
