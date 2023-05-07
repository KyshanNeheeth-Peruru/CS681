package edu.umb.cs681.hw17;

public class MoveSeatsRunnable implements Runnable {
	
	private Auditorium audi1;
	private Auditorium audi2;
	private volatile boolean done = false;
	
	public MoveSeatsRunnable(Auditorium audi1, Auditorium audi2) {
		this.audi1=audi1;
		this.audi2=audi2;
	}
	
	public void setDone() {
		done = true;
	}
	
	public void run() {
		if(!done) {
			audi1.moveSeats(audi2);
		}
	}

}
