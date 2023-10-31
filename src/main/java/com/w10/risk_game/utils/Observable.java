package com.w10.risk_game.utils;

/**
 * An interface for implementation of Observable with a notifyObservers
 * function.
 *
 * @author Sherwyn Dsouza
 */
public interface Observable {

	public void notifyObservers(String p_data);

	public void attach(Observer p_observer);

	public void detach();

}
