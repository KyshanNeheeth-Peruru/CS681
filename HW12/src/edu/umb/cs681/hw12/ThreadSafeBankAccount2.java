package edu.umb.cs681.hw12;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
public class ThreadSafeBankAccount2 {
	
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition sufficientFundsCondition = lock.newCondition();
	private Condition belowUpperLimitFundsCondition = lock.newCondition();
	
	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (d): current balance: " + balance);
			while(balance >= 300){
				System.out.println(Thread.currentThread().getId() + 
						" (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().getId() + 
					" (d): new balance: " + balance);
			sufficientFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			System.out.println("Thread "+Thread.currentThread().getId()+" interrupted");
			return;
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	
	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (w): current balance: " + balance);
			while(balance <= 0){
				System.out.println(Thread.currentThread().getId() + 
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			System.out.println("Thread "+Thread.currentThread().getId()+" interrupted");
			return;
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public double getBalance() { return this.balance; }

	public static void main(String[] args){
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
		List<Thread> threads = new ArrayList<>();
		DepositRunnable depositRunnable = new DepositRunnable(bankAccount);
	    WithdrawRunnable withdrawRunnable = new WithdrawRunnable(bankAccount);
		
		for(int i = 0; i < 5; i++){
			threads.add(new Thread(depositRunnable));
			threads.add(new Thread(withdrawRunnable));
		}
		
		for(Thread t:threads) {
			t.start();
		}
		
		try {
	        Thread.sleep(5000);
	    } catch (InterruptedException exception) {
	    	exception.printStackTrace();
	    }
		
		depositRunnable.setDone();
		withdrawRunnable.setDone();

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
	}

}
