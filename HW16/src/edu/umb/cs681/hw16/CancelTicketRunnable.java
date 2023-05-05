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
		while(true) {
			if(done) {
				break;
			}
			audi.cancelTicket();
		}
	}

}
