package com.w10.risk_game.utils;

/**
 * An interface is used to implement the Observer design pattern, which allows
 * objects to be notified of changes in the state of another object (observable
 * object).
 *
 * @author Sherwyn Dsouza
 */
public interface Observer {
	/**
	 * This function is used to update the state of an observable object.
	 *
	 * @param p_observableState
	 *            The parameter "p_observableState" is an object of type Observable.
	 *            It represents the state of the observable object that has changed
	 *            and needs to be updated.
	 */
	public void update(String p_observableState);
}
