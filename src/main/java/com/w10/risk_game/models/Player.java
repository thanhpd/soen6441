package com.w10.risk_game.models;

import java.util.ArrayList;

import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Blockade;
import com.w10.risk_game.commands.Bomb;
import java.util.List;
import java.util.Scanner;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

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
	private List<CardType> d_playerCards = new ArrayList<>();

	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * The `Player` constructor is initializing a new instance of the `Player` class
	 * with the provided parameters. It sets the player's name (`d_name`), the list
	 * of countries owned by the player (`d_countriesOwned`), the list of orders for
	 * the player (`d_orders`), and the number of leftover armies of the player
	 * (`d_leftoverArmies`).
	 *
	 * @param p_name
	 *            the player name
	 * @param p_countriesOwned
	 *            the list of countries owned by the player
	 * @param p_orders
	 *            the list of orders to issue
	 * @param p_leftoverArmies
	 *            the number of current army in possess by the player
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
	 * The function returns a list of CardType objects representing the cards held
	 * by a player.
	 *
	 * @return The method is returning a List of CardType objects.
	 */
	public List<CardType> getPlayerCards() {
		return d_playerCards;
	}

	/**
	 * The function sets the player's cards to the given list of card types.
	 *
	 * @param playerCards
	 *            The parameter "playerCards" is a List of objects of type
	 *            "CardType".
	 */
	public void setPlayerCards(List<CardType> playerCards) {
		this.d_playerCards = playerCards;
	}

	/**
	 * The function checks if a given country ID exists in a list of owned
	 * countries.
	 *
	 * @param p_CountryId
	 *            An integer representing the ID of a country.
	 * @return The method is returning a boolean value.
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
		String l_input = GameEngine.Command;
		String[] l_inputArray = l_input.split(" ");
		String l_orderType = l_inputArray[0];
		switch (l_orderType) {
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
				boolean l_again = true;
				boolean l_failed = false;
				while (l_again) {
					boolean l_isValidForm;
					boolean l_isValidOrder;
					boolean l_isValidCountry;
					boolean l_isValidArmy;
					Scanner l_scanner = new Scanner(System.in);
					if (l_failed) {
						d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_START);
						d_logger.log(Constants.USER_INPUT_REQUEST);
						l_input = l_scanner.nextLine();
						l_inputArray = l_input.split(" ");
					}
					l_isValidForm = checkValidDeployInput(l_inputArray);
					if (!l_isValidForm) {
						l_failed = true;
						continue;
					}
					String l_countryId = l_inputArray[1];
					String l_num = l_inputArray[2];
					l_isValidOrder = checkValidDeployOrder(l_orderType);
					l_isValidCountry = checkValidDeployCountry(this.getCountriesOwned(), l_countryId);
					l_isValidArmy = checkValidDeployArmy(Integer.parseInt(l_num));
					if (l_isValidOrder && l_isValidCountry && l_isValidArmy) {
						Order order = new Deploy(this, Integer.parseInt(l_inputArray[1]),
								Integer.parseInt(l_inputArray[2]));
						d_orders.add(order);
						deployArmies(Integer.parseInt(l_inputArray[2]));
						l_again = false;
						l_failed = false;
					} else {
						l_again = true;
						l_failed = true;
					}
				}
				break;
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE :
				// TODO: add advance object to d_orders
				break;
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :
				if (hasCard(CardType.BOMB)) {
					String l_countryIdToBomb = l_inputArray[1];
					if (Bomb.validateOrder(this, l_countryIdToBomb)) {
						Order order = new Bomb(this, l_countryIdToBomb);
						d_orders.add(order);
						removeCard(CardType.BOMB);
					}
				}
				break;
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :
				if (hasCard(CardType.BLOCKADE)) {
					String l_countryIdToBlockade = l_inputArray[1];
					if (Blockade.validateOrder(this, l_countryIdToBlockade)) {
						Order order = new Blockade(this, l_countryIdToBlockade);
						d_orders.add(order);
						removeCard(CardType.BLOCKADE);
					}
				}
				break;
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :
				if (hasCard(CardType.AIRLIFT)) {
					// airlift countryIdToAirliftFrom countryIdToAirlift NumberOfArmiesToAirlift
					String l_countryIdToAirliftFrom = l_inputArray[1];
					String l_countryIdToAirlift = l_inputArray[2];
					String l_airliftArmies = l_inputArray[3];
					if (Airlift.validateOrder(this, l_countryIdToAirlift)) {
						Order order = new Airlift(this, l_countryIdToAirliftFrom, l_countryIdToAirlift,
								l_airliftArmies);
						d_orders.add(order);
						removeCard(CardType.AIRLIFT);
					}
				}
				break;
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
				// TODO add negotiate object to d_orders
				break;
			default :
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_INVALID_ORDER_TYPE);
				break;
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
	 * The function "checkValidDeployInput" checks whether the input array has a
	 * length of 3, whether the country id is a positive integer, and whether the
	 * number of armies is a positive integer.
	 *
	 * @param p_inputArray
	 *            An array of strings representing the input for the method. The
	 *            expected format of the input is ["command", "countryId",
	 *            "numOfArmies"].
	 * @return The method is returning a boolean value.
	 */
	public boolean checkValidDeployInput(String[] p_inputArray) {
		// Step 1: Check the length of the input
		if (p_inputArray.length != 3) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INPUT_NOT_THREE_PARTS);
			return false;
		}
		// Step 2: Check whether the country id is positive integer
		String l_countryId = p_inputArray[1];
		String l_num = p_inputArray[2];
		for (int i = 0; i < l_countryId.length(); i++) {
			if (!Character.isDigit(l_countryId.charAt(i))) {
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_COUNTRY_ID_NOT_INTEGER);
				return false;
			}
		}
		// Step 3: Check whether the number of armies is positive integer
		for (int i = 0; i < l_num.length(); i++) {
			if (!Character.isDigit(l_num.charAt(i))) {
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_ARMIES_NOT_INTEGER);
				return false;
			}
		}
		// Step 4: Return true if the input format is valid
		return true;
	}

	/**
	 * The function checks if the given order type is valid for deployment and logs
	 * an error message if it is not.
	 *
	 * @param p_orderType
	 *            The parameter p_orderType is a String that represents the type of
	 *            order being checked.
	 * @return The method is returning a boolean value.
	 */
	public boolean checkValidDeployOrder(String p_orderType) {
		String l_orderType = p_orderType;
		if (!l_orderType.equals("deploy")) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_ORDER_TYPE);
			return false;
		}
		return true;
	}

	/**
	 * The function checks if a given country ID is valid by comparing it to a list
	 * of countries, and returns true if it is valid, otherwise it logs an error
	 * message and returns false.
	 *
	 * @param p_countries
	 *            A list of Country objects representing the countries available for
	 *            deployment.
	 * @param p_countryId
	 *            The p_countryId parameter is a String representing the ID of the
	 *            country that needs to be checked for validity.
	 * @return The method is returning a boolean value.
	 */
	public boolean checkValidDeployCountry(List<Country> p_countries, String p_countryId) {
		for (Country country : p_countries) {
			if (country.getCountryId() == Integer.parseInt(p_countryId)) {
				return true;
			}
		}
		d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_COUNTRY);
		return false;
	}

	/**
	 * The function checks if the number of armies to be deployed is valid.
	 *
	 * @param p_num
	 *            The parameter p_num represents the number of armies that a player
	 *            wants to deploy.
	 * @return The method is returning a boolean value.
	 */
	public boolean checkValidDeployArmy(int p_num) {
		if (p_num <= 0) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_ARMIES_ZERO);
			return false;
		}
		if (p_num > d_leftoverArmies) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_ARMIES);
			return false;
		}
		return true;
	}

	private boolean hasCard(CardType p_cardType) {
		if (d_playerCards.contains(p_cardType)) {
			return true;
		}

		d_logger.log(Constants.PLAYER_ISSUE_ORDER_NO_CARD);
		return false;
	}

	private void removeCard(CardType p_cardType) {
		d_playerCards.remove(p_cardType);
	}
}
