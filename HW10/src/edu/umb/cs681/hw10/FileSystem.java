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
		
		for (int i=0;i<10;i++) {
            Thread t = new Thread(() -> {
            	System.out.println("dir Root is directory :"+root.isDirectory());
            	System.out.println("link e is file :"+e.isFile());
            	System.out.println("File y is link :"+y.isLink());
            	System.out.println("y size :"+y.getSize());
            	System.out.println("a name :"+a.getName());
            	System.out.println("b name :"+b.getName());
            });
            threads.add(t);
            //t.start();
        }
		
		for (int j=0;j<10;j++) {
            Thread th = new Thread(() -> {
            	System.out.println("File b is directory :"+b.isDirectory());
            	System.out.println("File x is file :"+x.isFile());
            	System.out.println("Link d is link :"+d.isLink());
            	System.out.println("x size :"+x.getSize());
            	System.out.println("c name :"+c.getName());
            });
            threads.add(th);
            //th.start();
        }
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		for (Thread thread : threads) {
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}	
		}	
	}
}
