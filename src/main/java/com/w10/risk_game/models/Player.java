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
		int l_army = this.getLeftoverArmies();
		List<Country> l_countries = this.getCountriesOwned();
		boolean l_again = true;
		Scanner l_scanner = new Scanner(System.in);
		while (l_again) {
			boolean l_isValidFormat;
			boolean l_isValidOrder;
			boolean l_isValidCountry = false;
			boolean l_isValidNum = true;
			System.out.println("Please enter your order in the format of \"<OrderType> <CountryId> <No. of armies>\".");
			String l_input = l_scanner.nextLine();
			String[] l_inputArray = l_input.split(" ");
			if (l_inputArray[0].contains("done"))
				break;
			// check the input format
			l_isValidFormat = checkValidForm(l_inputArray);
			if (!l_isValidFormat) {
				continue;
			}
			String l_orderType = l_inputArray[0];
			String l_countryId = l_inputArray[1];
			String l_num = l_inputArray[2];
			// check the order type
			l_isValidOrder = checkValidOrder(l_orderType);
			// check the country
			l_isValidCountry = checkValidCountry(l_countries, l_countryId);
			// check the num
			l_isValidNum = checkValidNum(Integer.parseInt(l_num), l_army);
			if (l_isValidFormat && l_isValidOrder && l_isValidCountry && l_isValidNum) {
				Order order = new Order(this, l_orderType, Integer.parseInt(l_countryId), Integer.parseInt(l_num));
				d_orders.add(order);
				l_army = l_army - Integer.parseInt(l_num);
				this.setLeftoverArmies(l_army);
				l_again = false;
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

	/**
	 * This function is used to check the input format. The input should have three
	 * parts (one string and two integers)
	 *
	 * @param p_inputArray
	 *            the input string
	 * @return boolean value to show whether the input format is valid
	 */
	public boolean checkValidForm(String[] p_inputArray) {
		if (p_inputArray.length != 3) {
			System.out.println("Invalid input! The command should contain three parts. Please try again.");
			return false;
		}
		String l_countryId = p_inputArray[1];
		String l_num = p_inputArray[2];
		for (int i = 0; i < l_countryId.length(); i++) {
			if (!Character.isDigit(l_countryId.charAt(i))) {
				System.out.println("Invalid input! The country id should be integers. Please try again.");
				return false;
			}
		}
		for (int i = 0; i < l_num.length(); i++) {
			if (!Character.isDigit(l_num.charAt(i))) {
				System.out.println("Invalid input! The number of armies should be integers. Please try again.");
				return false;
			}
		}
		return true;
	}

	/**
	 * This function is used to check the order type. The order type should be
	 * "deploy"
	 *
	 * @param p_orderType
	 *            the order type
	 * @return boolean value to show whether the order type is valid
	 */
	public boolean checkValidOrder(String p_orderType) {
		String l_orderType = p_orderType;
		if (!l_orderType.equals("deploy")) {
			System.out.println("Invalid input! The order type should be \"deploy\". Please try again.");
			return false;
		}
		return true;
	}

	/**
	 * This function is used to check the country id. The country id should be one
	 * of the countries owned by the player
	 *
	 * @param p_countries
	 *            the list of countries owned by the player
	 * @param p_countryId
	 *            the country id
	 * @return boolean value to show whether the country id is valid
	 */
	public boolean checkValidCountry(List<Country> p_countries, String p_countryId) {
		for (Country country : p_countries) {
			if (country.getCountryId() == Integer.parseInt(p_countryId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This function is used to check the number of armies. The number of armies
	 * should be less than the number of leftover armies
	 *
	 * @param p_num
	 *            the number of armies
	 * @param p_army
	 *            the number of leftover armies
	 * @return boolean value to show whether the number of armies is valid
	 */
	public boolean checkValidNum(int p_num, int p_army) {
		if (p_num > p_army) {
			System.out.println(
					"Invalid input! The number of leftover armies should be more than the number of armies. Please try again.");
			return false;
		}
		return true;
	}
}
