package edu.umb.cs681.primes;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
	private boolean done = false;
	private ReentrantLock lock = new ReentrantLock();

	public RunnableCancellablePrimeGenerator(long from, long to) {
		super(from, to);
	}

	public void setDone() {
		lock.lock();
		try {
			done = true;
		} finally {
			lock.unlock();
		}	
	}
	
	public void generatePrimes() {
		for(long n = from;n<=to; n++) {
			lock.lock();
			try {	
				if(done) {
					System.out.println("Stopped....");
					this.primes.clear();
					break;
				}
				if(isPrime(n)) {
					this.primes.add(n);
					//System.out.println(n);
				}
			} finally {
				lock.unlock();
			}
		}
	}

	public void run() {
		generatePrimes();
	}

	public static void main(String[] args) {
		System.out.println("==========Runnable Cancellable Prime Generator==========");
		RunnableCancellablePrimeGenerator gen1 = new RunnableCancellablePrimeGenerator(1, 100);
		RunnableCancellablePrimeGenerator gen2 = new RunnableCancellablePrimeGenerator(101, 200);

		Thread t1 = new Thread(gen1);
		Thread t2 = new Thread(gen2);

		t1.start();
		t2.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen1.setDone();
		gen2.setDone();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.print("Primes found by thread 1: ");
		gen1.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
		System.out.print("\nPrimes found by thread 2: ");
		gen2.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));

		long primeNum = gen1.getPrimes().size() + gen2.getPrimes().size();
		System.out.println("\n" + primeNum + " prime numbers are found in total.");
	}
}
