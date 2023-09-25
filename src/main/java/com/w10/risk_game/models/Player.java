package com.w10.risk_game.models;

import java.util.List;
import java.util.Scanner;

public class Player {
	private String name;
	private List<Country> countriesOwned;
	private List<Order> orders;
	private int leftoverArmies;

	public Player(String name, List<Country> countriesOwned, List<Order> orders, int leftoverArmies) {
		this.name = name;
		this.countriesOwned = countriesOwned;
		this.orders = orders;
		this.leftoverArmies = leftoverArmies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Country> getCountriesOwned() {
		return countriesOwned;
	}

	public void setCountriesOwned(List<Country> countriesOwned) {
		this.countriesOwned = countriesOwned;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * This method is used to issue an order
	 * This method gets the command input, creates an order object and adds it to the list of orders
	 */
	public void issueOrder() {
		// todo - to add an order to the list of orders held by the player
		// todo - check the input
		System.out.println("Please enter your order in the format of \"orderType countryId num\".");
		Scanner l_scanner = new Scanner(System.in);
		String l_input = l_scanner.nextLine();
		String[] l_inputArray = l_input.split(" ");
		String l_orderType = l_inputArray[0];
		int countryId = Integer.parseInt(l_inputArray[1]);
		int num = Integer.parseInt(l_inputArray[2]);
		Order order = new Order(this, l_orderType, countryId, num);
		this.orders.add(order);
	}

	public Order nextOrder() {
		return orders.remove(0);
	}

	public int getLeftoverArmies() {
		return leftoverArmies;
	}

	public void setLeftoverArmies(int leftoverArmies) {
		this.leftoverArmies = leftoverArmies;
	}

	public void deployArmies(int num) {
		this.leftoverArmies = leftoverArmies - num;
	}

	public void addArmies(int num) {
		this.leftoverArmies = leftoverArmies + num;
	}
}
