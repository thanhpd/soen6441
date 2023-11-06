package com.w10.risk_game.utils.loggers;

import com.w10.risk_game.utils.Observer;

/**
 * The ConsoleLogger class is an implementation of the Observer interface in
 * Java that prints the given data when the update method is called.
 *
 * @author Sherwyn Dsouza
 */
public class ConsoleLogger implements Observer {

	/**
	 * The update function prints the given data.
	 *
	 * @param p_data
	 *            The parameter "p_data" is a String that represents the data that
	 *            needs to be updated.
	 */
	@Override
	public void update(String p_data) {
		System.out.println(p_data);
	}
}
