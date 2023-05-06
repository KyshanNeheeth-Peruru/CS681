package edu.umb.cs681.hw10;

public class Thread2 implements Runnable {
	
	public volatile boolean done = false;
	private final FileSystem fs;
	
	public Thread2(FileSystem fs) {
		this.fs=fs;
	}
	
	public void setDone() {
		done=true;
	}
	
	public void run() {
		while(!done) {
			System.out.println("File b is directory :"+fs.getRootDirs().get(0).getSubDirectories().get(2).getSubDirectories().get(0).getFiles().get(1).isDirectory());
        	System.out.println("File x is file :"+fs.getRootDirs().get(0).getSubDirectories().get(0).getFiles().get(0).isFile());
        	System.out.println("Link d is link :"+fs.getRootDirs().get(0).getChildren().get(3).isLink());
        	System.out.println("x size :"+fs.getRootDirs().get(0).getSubDirectories().get(0).getFiles().get(0).getSize());
        	System.out.println("c name :"+fs.getRootDirs().get(0).getSubDirectories().get(2).getFiles().get(0).getName());
		}
	}

}
