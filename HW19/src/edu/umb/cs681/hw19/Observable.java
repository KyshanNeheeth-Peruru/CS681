package edu.umb.cs681.hw19;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<T> {
	//private LinkedList<Observer<T>> observers = new LinkedList<>();
	private Queue<Observer<T>> observers = new ConcurrentLinkedQueue<>();
	
	public void addObserver(Observer<T> o) {
		observers.add(o);
	}

	public void clearObservers() {
		observers.clear();
	}
	public List<Observer<T>> getObservers(){
		return new ArrayList<>(observers);
	}
	
	public int countObservers() {
		return observers.size();
	}
	public void removeObserver(Observer<T> o) {
		observers.remove(o);
	}

	public void notifyObservers(T event) {
		observers.forEach( (observer)->{observer.update(this, event);} );
	}
	
}
