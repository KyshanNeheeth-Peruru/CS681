package edu.umb.cs681.hw14;

public class StatsHandler implements Runnable {
	
	private AdmissionMonitor monitor;
	
	private volatile boolean done = false;
	
	public void setDone() {
		done=true;
	}
	
	public StatsHandler(AdmissionMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void run() {
		while(!done) {
			System.out.println(Thread.currentThread().getId()+" Current number of visitors : "+monitor.countCurrentVisitors());
		}
	}

}
