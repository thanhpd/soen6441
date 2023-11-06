package com.w10.risk_game.utils;

/**
 * An interface for implementation of Observable with notifyObservers, attach
 * and detach functions.
 *
 * @author Sherwyn Dsouza
 */
public interface Observable {

	/**
	 * The function notifies the observers with the given data.
	 *
	 * @param p_data
	 *            The data that will be passed to the observers when they are
	 *            notified.
	 */
	public void notifyObservers(String p_data);

	/**
	 * The attach function is used to add an observer to a list of observers.
	 *
	 * @param p_observer
	 *            The parameter "p_observer" is an object of a class that implements
	 *            the Observer interface. This object is the observer that you want
	 *            to attach to the subject.
	 */
	public void attach(Observer p_observer);

	/**
	 * The detach() function is used to detach an object from its parent object or
	 * container.
	 */
	public void detach();

}
