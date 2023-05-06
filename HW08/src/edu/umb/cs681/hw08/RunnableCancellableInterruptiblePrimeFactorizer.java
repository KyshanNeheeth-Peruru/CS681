package edu.umb.cs681.hw08;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer extends RunnableCancellablePrimeFactorizer {
	private boolean done = false;
	private final ReentrantLock lock = new ReentrantLock();

	public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
		super(dividend, from, to);
	}
	
	public void generatePrimeFactors() {
		long divisor = 2;
	    while( dividend != 1 && divisor <= to ){
	    	lock.lock();
	    	try {
                if(done) {
                    System.out.println("Stopped generating prime factors");
                    this.factors.clear();
                    break;
                }
                if(dividend%divisor==0) {
                    factors.add(divisor);
                    System.out.println(divisor);
                    dividend /= divisor;
                } else {
                    if (divisor == 2) {
                        divisor++;
                    } else {
                        divisor += 2;
                    }
                }
            } finally {
                lock.unlock();
            }
	    	try {
	    		Thread.sleep(1000);
	    	}catch(InterruptedException e) {
	    		System.out.println(e.toString());
	    		continue;
	    	}
		}
	}
	
	public static void main(String[] args) {
		RunnableCancellableInterruptiblePrimeFactorizer gen = new RunnableCancellableInterruptiblePrimeFactorizer(84, 2, (long)Math.sqrt(84));
		Thread thread = new Thread(gen);
		thread.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen.setDone();
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen.getPrimeFactors().forEach( (Long primefactor)-> System.out.print(primefactor + " is prime factor. ") );
	}

}
