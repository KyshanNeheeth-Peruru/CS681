package edu.umb.cs681.hw12;

public class DepositRunnable implements Runnable {
	
	private ThreadSafeBankAccount2 account;
	private volatile boolean done = false;

	public DepositRunnable(ThreadSafeBankAccount2 bankAccount) {
		this.account = bankAccount;
	}
	
	public void setDone() {
		done = true;
	}

	public void run() {
		while(!done) {
			account.deposit(100);
			try {
				Thread.sleep(500);
			} catch(InterruptedException exception) {
				System.out.println("Thread "+Thread.currentThread().getId()+" interrupted");
				return;
			}
		}    
    }

}
