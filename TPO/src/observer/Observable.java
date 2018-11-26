package observer;

import java.util.LinkedList;
import java.util.List;

import exceptions.InvalidObserverException;

public abstract class Observable {

	protected List<Observer> observerList = new LinkedList<>();
	
	public void addObserver(Observer o) throws InvalidObserverException {
		if(o != null) {
			observerList.add(o);
		} else {
			throw new InvalidObserverException("Observer is null");
		}
	}
	
	public void removeObserver(Observer o) throws InvalidObserverException {
		if(o != null) {
			observerList.remove(o);
		} else {
			throw new InvalidObserverException("Observer is null");
		}
	}
	
	public void updateObservers() {
		for (Observer observer : observerList) {
			observer.update();
		}
	}

}
