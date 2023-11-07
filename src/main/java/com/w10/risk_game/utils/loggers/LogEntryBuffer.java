package com.w10.risk_game.utils.loggers;

import java.util.ArrayList;
import java.util.List;

import com.w10.risk_game.utils.Observable;
import com.w10.risk_game.utils.Observer;

/**
 * The LogEntryBuffer class is a singleton implementation that acts as a buffer
 * for log entries and notifies observers when new log entries are added.
 *
 * @author Sherwyn Dsouza
 */
public class LogEntryBuffer implements Observable {

	private static LogEntryBuffer Logger;

	List<Observer> d_observers = new ArrayList<>();

	private LogEntryBuffer() {
	}

	/**
	 * The function returns an instance of the LogEntryBuffer class, creating a new
	 * instance if one does not already exist.
	 *
	 * @return The method is returning an instance of the LogEntryBuffer class.
	 */
	public static LogEntryBuffer GetInstance() {
		if (Logger == null)
			Logger = new LogEntryBuffer();
		return Logger;
	}

	/**
	 * The function notifies all observers with the given data.
	 *
	 * @param p_data
	 *            The parameter "p_data" is a string that represents the data that
	 *            will be passed to the observers.
	 */
	@Override
	public void notifyObservers(String p_data) {
		this.d_observers.forEach(l_observer -> l_observer.update(p_data));
	}

	/**
	 * The function attaches an observer to a list of observers.
	 *
	 * @param p_observer
	 *            The parameter "p_observer" is an object of a class that implements
	 *            the Observer interface.
	 */
	@Override
	public void attach(Observer p_observer) {
		this.d_observers.add(p_observer);
	}

	/**
	 * The detach() function clears the list of observers.
	 */
	@Override
	public void detach() {
		this.d_observers.clear();
	}

	/**
	 * The log function notifies observers with the given data.
	 *
	 * @param p_data
	 *            The parameter "p_data" is a string that represents the data that
	 *            needs to be logged.
	 */
	public void log(String p_data) {
		notifyObservers(p_data);
	}
}
