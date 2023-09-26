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
	 * This method is used to issue an order This method gets the command input,
	 * creates an order object and adds it to the list of orders
	 */
	public void issueOrder() {
		// todo - to add an order to the list of orders held by the player
		// todo - check the input
		int l_army = this.leftoverArmies;
		boolean l_again = true;
		while (l_again) {
			boolean l_isValidFormat = true;
			boolean l_isValidOrder = true;
			boolean l_isValidCountry = false;
			boolean l_isValidNum = true;
			System.out.println("Please enter your order in the format of \"orderType countryId num\".");
			Scanner l_scanner = new Scanner(System.in);
			String l_input = l_scanner.nextLine();
			String[] l_inputArray = l_input.split(" ");
			// check the input format
			if (l_inputArray.length != 3) {
				l_isValidFormat = false;
			}
			String l_orderType = l_inputArray[0];
			String l_countryId = l_inputArray[1];
			String l_num = l_inputArray[2];
			for (int i = 0; i < l_countryId.length(); i++) {
				if (!Character.isDigit(l_countryId.charAt(i))) {
					l_isValidFormat = false;
				}
			}
			for (int i = 0; i < l_num.length(); i++) {
				if (!Character.isDigit(l_num.charAt(i))) {
					l_isValidFormat = false;
				}
			}
			// check the order
			if (!l_orderType.equals("deploy")) {
				l_isValidOrder = false;
			}
			// check the country
			for (Country country : countriesOwned) {
				if (country.getCountryId() == Integer.parseInt(l_countryId)) {
					l_isValidCountry = true;
				}
			}
			// check the num
			if (Integer.parseInt(l_num) > leftoverArmies) {
				l_isValidNum = false;
			}
			if (l_isValidFormat && l_isValidOrder && l_isValidCountry && l_isValidNum) {
				l_again = false;
				Order order = new Order(this, l_orderType, Integer.parseInt(l_countryId), Integer.parseInt(l_num));
				this.orders.add(order);
				l_army = l_army - Integer.parseInt(l_num);
			} else {
				l_again = true;
				System.out.println("Invalid input! Please try again.");
			}
			if (l_army <= 0) {
				l_again = false;
			}
		}
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
