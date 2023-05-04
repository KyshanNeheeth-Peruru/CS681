package edu.umb.cs681.hw14;

public class EntranceHandler implements Runnable {
	
	private AdmissionMonitor monitor;
	
	public EntranceHandler(AdmissionMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void run() {
		monitor.enter();
	}

}
