package edu.umb.cs681.hw10;

public class Thread1 implements Runnable {
	
	public volatile boolean done = false;
	private final FileSystem fs;
	
	public Thread1(FileSystem fs) {
		this.fs=fs;
	}
	
	public void setDone() {
		done=true;
	}
	
	public void run() {
		while(!done) {
        	System.out.println("dir Root is directory :"+fs.getRootDirs().get(0).isDirectory());
        	System.out.println("link e is file :"+fs.getRootDirs().get(0).getChildren().get(4).isFile());
        	System.out.println("File y is link :"+fs.getRootDirs().get(0).getSubDirectories().get(1).getFiles().get(0).isLink());
        	System.out.println("y size :"+fs.getRootDirs().get(0).getSubDirectories().get(1).getFiles().get(0).getSize());
        	System.out.println("a name :"+fs.getRootDirs().get(0).getSubDirectories().get(2).getSubDirectories().get(0).getFiles().get(0).getName());
        	System.out.println("b name :"+fs.getRootDirs().get(0).getSubDirectories().get(2).getSubDirectories().get(0).getFiles().get(1).getName());
        	
        	try {
	    		Thread.sleep(1000);
	    	}catch(InterruptedException e) {
	    		System.out.println(Thread.currentThread().getId()+" interrupted.");
	    		return;
	    	}
		}
	}

}
