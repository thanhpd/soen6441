package com.w10.risk_game.commands;

/**
 * This class is an abstract class for order. It defines the basic behavior
 * (execute) of an order.
 *
 * @author Yajing Liu
 */
public abstract class Order {
	/**
	 * This is the constructor of Order class
	 */
	protected Order() {
	}
	/**
	 * This is an abstract method to execute the order
	 */
	public abstract void execute();
}
