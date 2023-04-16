package edu.umb.cs681.hw07;

import java.time.LocalDateTime;
import java.util.LinkedList;
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
		return this.rootD;
	}

	public void addRootDir(Directory root)
	{
		rootD.add(root);
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
		
		System.out.println("File system before:");
        for(FSElement ele:fs.getRootDirs().get(0).getChildren()) {
            System.out.println(ele.getName());
        }
		
		Thread thread1 = new Thread(() -> {
            FileSystem fs1 = FileSystem.getFileSystem();
            Directory dir1 = new Directory(null, "t1dir", 0, localTime);
            fs1.getRootDirs().get(0).appendChild(dir1);
        });

        Thread thread2 = new Thread(() -> {
            FileSystem fs2 = FileSystem.getFileSystem();
            Directory dir2 = new Directory(null, "t2dir", 0, localTime);
            fs2.getRootDirs().get(0).appendChild(dir2);
        });
        
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        System.out.println("File system after:");
        for(FSElement ele:fs.getRootDirs().get(0).getChildren()) {
            System.out.println(ele.getName());
        }	
	}
}
