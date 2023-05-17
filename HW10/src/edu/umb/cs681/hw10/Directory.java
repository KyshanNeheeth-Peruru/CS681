package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Directory extends FSElement {	
	private LinkedList<FSElement> children = new LinkedList<FSElement>();
	private LinkedList<File> fileList = new LinkedList<File>();
	private LinkedList<Directory> directoryList = new LinkedList<Directory>();
	private LinkedList<Link> linkList = new LinkedList<Link>();
	
	public Directory(Directory parent, String name, int size, LocalDateTime createdTime)
	{
		super(parent, name, size, createdTime);
		if (parent != null)
			parent.appendChild(this);
	}
	
	public LinkedList<FSElement> getChildren()
	{
		lock.lock();
		try {
			return children;
		} finally {
			lock.unlock();
		}
	}
	
	public void appendChild(FSElement child)
	{	
		lock.lock();
		try {
			this.children.add(child);
			child.setParent(this);
		} finally {
			lock.unlock();
		}
	}
	
	public int countChildren()
	{
		lock.lock();
		try {
			return getChildren().size();
		} finally {
			lock.unlock();
		}
	}
		
	public LinkedList<Directory> getSubDirectories()
	{
		lock.lock();
		try {
			for (FSElement e : getChildren()) {
				if (e instanceof Directory)
					directoryList.add((Directory) e);
			}
			return directoryList;
		} finally {
			lock.unlock();
		}
	}
	
	public LinkedList<File> getFiles()
	{
		lock.lock();
		try {
			for (FSElement e : getChildren()) {
				if (e instanceof File) {
					fileList.add((File) e);
				}
			}
			return fileList;
		} finally {
			lock.unlock();
		}
	}
	
	public LinkedList<Link> getLinks()
	{
		lock.lock();
		try {
			for(FSElement element : getChildren())
			{
				if(element.isLink())
				{
					linkList.add((Link) element);
				}
			}
			return linkList;
		} finally {
			lock.unlock();
		}
	}

	public int getTotalSize()
	{
		lock.lock();
		try {
			int totalSize = 0;
			for (FSElement e : getChildren()) {
				if (e instanceof Directory)
					totalSize += ((Directory) e).getTotalSize();
				else
					totalSize += e.getSize();
			}
			return totalSize;
		} finally {
			lock.unlock();
		}
	}
	
	public boolean isDirectory()
	{
		return true;
	}
	
	public  boolean isFile()
	{
		return false;
	}
	
	public boolean isLink()
	{
		return false;
	}

}
