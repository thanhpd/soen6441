package com.w10.risk_game.command;

/**
 * This class is an abstract class for order. It defines the basic behavior
 * (execute) of an order.
 *
 * @author Yajing Liu
 */
public abstract class Order {
	public Order() {
	}
	/**
	 * This is an abstract method to execute the order
	 */
	public abstract void execute();
}
