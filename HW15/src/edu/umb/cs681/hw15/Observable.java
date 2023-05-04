package edu.umb.cs681.hw15;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<T> {
	private LinkedList<Observer<T>> observers = new LinkedList<>();
	private ReentrantLock lockobs = new ReentrantLock();
	
	public void addObserver(Observer<T> o) {
		lockobs.lock();
		try {
			observers.add(o);
		} finally {
			lockobs.unlock();
		}
	}

	public void clearObservers() {
		lockobs.lock();
		try {
			observers.clear();
		} finally {
			lockobs.unlock();
		}
		
	}
	public List<Observer<T>> getObservers(){
		lockobs.lock();
		try {
			return observers;
		} finally {
			lockobs.unlock();
		}
	}
	
	public int countObservers() {
		lockobs.lock();
		try {
			return observers.size();
		} finally {
			lockobs.unlock();
		}
		
	}
	public void removeObserver(Observer<T> o) {
		lockobs.lock();
		try {
			observers.remove(o);
		} finally {
			lockobs.unlock();
		}
	}

	public void notifyObservers(T event) {
		lockobs.lock();
		try {
			observers.forEach( (observer)->{observer.update(this, event);} );
		} finally {
			lockobs.unlock();
		}
	}
	
}
