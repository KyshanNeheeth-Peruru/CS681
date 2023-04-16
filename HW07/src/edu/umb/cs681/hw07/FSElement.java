package edu.umb.cs681.hw07;

import java.time.LocalDateTime;

public abstract class FSElement {
	private String name;
	private int size;
	private LocalDateTime creationTime;
	private Directory parent;
	protected abstract boolean isDirectory();
	protected abstract boolean isFile();
	protected abstract boolean isLink();
	
	public FSElement(Directory parent, String name, int size, LocalDateTime creationTime)
	{
		this.parent = parent;
		this.name = name;
		this.size = size;
		this.creationTime = creationTime;
	}
	
	public void setParent(Directory parent)
	{
		this.parent = parent;
	}

	public String getName()
	{
		return name;
	}

	public int getSize()
	{
		return size;
	}

	public LocalDateTime getCreationTime()
	{
		return creationTime;
	}

	public Directory getParent()
	{
		return parent;
	}

}
