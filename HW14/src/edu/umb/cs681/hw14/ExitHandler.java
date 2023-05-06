package edu.umb.cs681.hw14;

public class ExitHandler implements Runnable {
	
	private AdmissionMonitor monitor;
	private volatile boolean done = false;
	
	public void setDone() {
		done=true;
	}
	
	public ExitHandler(AdmissionMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void run() {
		while(!done) {
			monitor.exit();
		}
	}

}
