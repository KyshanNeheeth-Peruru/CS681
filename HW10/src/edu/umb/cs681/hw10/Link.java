package edu.umb.cs681.hw10;

import java.time.LocalDateTime;

public class Link extends FSElement {
	
	private FSElement target;
	public Link(Directory parent, String name, int size, LocalDateTime creationTime, FSElement target)
	{
		super(parent, name, size, creationTime);
		this.target = target;
		parent.appendChild(this);
	}
	public FSElement getTarget()
	{
		lock.lock();
		try {
			return target;
		} finally {
			lock.unlock();
		}
	}

	public boolean isDirectory()
	{
		lock.lock();
		try {
			return false;
		} finally {
			lock.unlock();
		}
	}
	
	public boolean isFile()
	{
		lock.lock();
		try {
			return false;
		} finally {
			lock.unlock();
		}
	}
	
	public boolean isLink()
	{
		lock.lock();
		try {
			return true;
		} finally {
			lock.unlock();
		}
	}
}
