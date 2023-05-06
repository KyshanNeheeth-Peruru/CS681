package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
	private static FileSystem instance = null;
	private LinkedList<Directory> rootD = new LinkedList<Directory>();
	private static ReentrantLock lock = new ReentrantLock();
	public static FileSystem getFileSystem()
	{
		lock.lock();
		try {
		if (instance == null)
			instance = new FileSystem();
		return instance;
		} finally {
			lock.unlock();
		}
	}
	
	private FileSystem() {}

	public LinkedList<Directory> getRootDirs()
	{
		lock.lock();
		try {
			return this.rootD;
		} finally {
			lock.unlock();
		}
	}

	public void addRootDir(Directory root)
	{
		lock.lock();
		try {
			rootD.add(root);
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) {
		System.out.println();
		LocalDateTime localTime = LocalDateTime.now();
		
		Directory root = new Directory(null, "root", 0, localTime);
		Directory apps = new Directory(root, "apps", 0, localTime);
		Directory bin = new Directory(root, "bin", 0, localTime);
		Directory home = new Directory(root, "home", 0, localTime);
		Directory pictures = new Directory(home, "pictures", 0, localTime);
		
		File x = new File(apps, "x", 1, localTime);
		File y = new File(bin, "y", 2, localTime);
		File a = new File(pictures, "a", 3, localTime);
		File b = new File(pictures, "b", 4, localTime);
		File c = new File(home, "c", 4, localTime);
		
		Link d = new Link(root, "d", 0, localTime, pictures);
		Link e = new Link(root, "e", 0, localTime, x);
		
		FileSystem fs = FileSystem.getFileSystem();
		fs.addRootDir(root);
		List<Thread> threads = new ArrayList<>();
		
		Thread1 t1 = new Thread1(fs);
		Thread2 t2= new Thread2(fs);
		
		for(int i = 0; i < 5; i++){
			threads.add(new Thread(t1));
			threads.add(new Thread(t2));
		}
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		t1.setDone();
		t2.setDone();
		
		for (Thread thread : threads) {
			thread.interrupt();
		}
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}	
		}
		
		System.out.println("Done");
	}
}
