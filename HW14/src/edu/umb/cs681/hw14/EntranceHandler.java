package edu.umb.cs681.hw14;

public class EntranceHandler implements Runnable {
	
	private AdmissionMonitor monitor;
	private volatile boolean done = false;
	
	public void setDone() {
		done=true;
	}
	
	public EntranceHandler(AdmissionMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void run() {
		while(!done) {
			monitor.enter();
		}
	}

}
