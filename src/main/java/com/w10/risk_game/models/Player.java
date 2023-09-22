package com.w10.risk_game.models;

import java.util.List;

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

	public void issueOrder() {
		// todo - to add an order to the list of orders held by the player
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
