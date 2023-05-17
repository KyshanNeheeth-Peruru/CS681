package edu.umb.cs681.hw16;

public class CancelTicketRunnable implements Runnable {
	
	private Auditorium audi;
	private volatile boolean done = false;
	
	public CancelTicketRunnable(Auditorium audi) {
		this.audi=audi;
	}
	
	public void setDone() {
		done = true;
	}
	
	public void run() {
		if(!done) {
			audi.cancelTicket();
		}
		try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        	System.out.println("Thread "+Thread.currentThread().getId()+" interrupted.");
    		return;
        }
	}

}
