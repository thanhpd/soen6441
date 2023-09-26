package com.w10.risk_game.models;

import java.util.List;
import java.util.Scanner;

/**
 * The Player class represents a player in this game, with properties such as
 * name, countries owned, orders, and leftover armies, as well as methods to
 * manipulate these properties.
 *
 * @author Darlene-Naz, Omnia Alam
 */
public class Player {
	private String d_name;
	private List<Country> d_countriesOwned;
	private List<Order> d_orders;
	private int d_leftoverArmies;

	/**
	 * The `Player` constructor is initializing a new instance of the `Player` class
	 * with the provided parameters. It sets the player's name (`d_name`), the list
	 * of countries owned by the player (`d_countriesOwned`), the list of orders for
	 * the player (`d_orders`), and the number of leftover armies of the player
	 * (`d_leftoverArmies`).
	 *
	 * @param p_name
	 * @param p_countriesOwned
	 * @param p_orders
	 * @param p_leftoverArmies
	 */
	public Player(String p_name, List<Country> p_countriesOwned, List<Order> p_orders, int p_leftoverArmies) {
		this.d_name = p_name;
		this.d_countriesOwned = p_countriesOwned;
		this.d_orders = p_orders;
		this.d_leftoverArmies = p_leftoverArmies;
	}

	/**
	 * The function returns the name of the player.
	 *
	 * @return The method is returning the player's name.
	 */
	public String getName() {
		return d_name;
	}

	/**
	 * The function sets the name of the player.
	 *
	 * @param p_name
	 *            The parameter "p_name" is a String that represents the name of the
	 *            player.
	 */
	public void setName(String p_name) {
		this.d_name = p_name;
	}

	/**
	 * The getCountriesOwned function returns a list of countries owned by the
	 * player.
	 *
	 * @return A List of Country objects is being returned.
	 */
	public List<Country> getCountriesOwned() {
		return d_countriesOwned;
	}

	/**
	 * The function sets the list of countries owned by a player.
	 *
	 * @param p_countriesOwned
	 *            This parameter is a List of objects of type Country. It represents
	 *            the countries that are owned by a player.
	 */
	public void setCountriesOwned(List<Country> p_countriesOwned) {
		this.d_countriesOwned = p_countriesOwned;
	}

	/**
	 * The function returns the player's list of orders.
	 *
	 * @return A List of Order objects is being returned.
	 */
	public List<Order> getOrders() {
		return d_orders;
	}
	/**
	 *
	 * @param p_CountryId
	 * @return boolan based on if the country id is associated with player or not
	 */
	public boolean hasCountry(int p_CountryId) {
		if (d_countriesOwned == null) {
			return false;
		}
		for (int l_country = 0; l_country < d_countriesOwned.size(); l_country++) {
			if (d_countriesOwned.get(l_country).getCountryId() == p_CountryId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The function sets the list of orders for a given player.
	 *
	 * @param p_orders
	 *            The parameter "p_orders" is a List of Order of a player.
	 */
	public void setOrders(List<Order> p_orders) {
		this.d_orders = p_orders;
	}

	/**
	 * The function returns the number of leftover armies.
	 *
	 * @return The method is returning an integer value, specifically the value of
	 *         the variable "d_leftoverArmies".
	 */
	public int getLeftoverArmies() {
		return d_leftoverArmies;
	}

	/**
	 * The function sets the value of the "leftoverArmies" variable.
	 *
	 * @param p_leftoverArmies
	 *            The parameter "p_leftoverArmies" is an integer that represents the
	 *            number of leftover armies.
	 */
	public void setLeftoverArmies(int p_leftoverArmies) {
		this.d_leftoverArmies = p_leftoverArmies;
	}

	/**
	 * The function "deployArmies" subtracts the specified number of armies from the
	 * leftover armies.
	 *
	 * @param p_num
	 *            The parameter "p_num" represents the number of armies that are
	 *            being deployed.
	 */
	public void deployArmies(int p_num) {
		this.d_leftoverArmies -= p_num;
	}

	/**
	 * The function adds a specified number of armies to a player's leftoverArmies.
	 *
	 * @param p_num
	 *            The parameter "p_num" represents the number of armies that will be
	 *            added to the existing number of leftover armies.
	 */
	public void addArmies(int p_num) {
		this.d_leftoverArmies += p_num;
	}

	/**
	 * This method is used to issue an order This method gets the command input,
	 * creates an order object and adds it to the list of orders
	 */
	public void issueOrder() {
		// todo - to add an order to the list of orders held by the player
		// todo - check the input
		int l_army = this.getLeftoverArmies();
		List<Country> l_countries = this.getCountriesOwned();
		boolean l_again = true;
		Scanner l_scanner = new Scanner(System.in);
		while (l_again) {
			boolean l_isValidFormat = true;
			boolean l_isValidOrder = true;
			boolean l_isValidCountry = false;
			boolean l_isValidNum = true;
			System.out.println("Please enter your order in the format of \"orderType countryId num\".");
			String l_input = l_scanner.nextLine();
			String[] l_inputArray = l_input.split(" ");
			// check the input format
			if (l_inputArray.length != 3) {
				l_isValidFormat = false;
				System.out.println("Invalid input! The command should contain three parts. Please try again.");
				continue;
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
			if (!l_isValidFormat) {
				System.out.println(
						"Invalid input! The country id and the number of armies should be integers. Please try again.");
				continue;
			}
			// check the order
			if (!l_orderType.equals("deploy")) {
				l_isValidOrder = false;
			}
			// check the country
			for (Country country : l_countries) {
				if (country.getCountryId() == Integer.parseInt(l_countryId)) {
					l_isValidCountry = true;
				}
			}
			// check the num
			if (Integer.parseInt(l_num) > l_army) {
				l_isValidNum = false;
			}
			if (l_isValidFormat && l_isValidOrder && l_isValidCountry && l_isValidNum) {
				Order order = new Order(this, l_orderType, Integer.parseInt(l_countryId), Integer.parseInt(l_num));
				d_orders.add(order);
				l_army = l_army - Integer.parseInt(l_num);
				if (l_army <= 0) {
					l_again = false;
				} else {
					l_again = true;
				}
			} else {
				l_again = true;
				System.out.print("Invalid input! ");
				if (!l_isValidOrder) {
					System.out.print("The order type should be \"deploy\". ");
				} else if (!l_isValidCountry) {
					System.out.print("The country id should be one of the countries owned by the player. ");
				} else {
					System.out.print("The number of leftover armies should be more than the number of armies. ");
				}
				System.out.println("Please try again.");
			}
		}
	}

	/**
	 * The function "nextOrder" returns and removes the first element from a list of
	 * player's orders.
	 *
	 * @return The method is returning an object of type Order.
	 */
	public Order nextOrder() {
		return d_orders.remove(0);
	}
}
