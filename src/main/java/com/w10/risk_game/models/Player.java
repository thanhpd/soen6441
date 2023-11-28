package com.w10.risk_game.models;

import java.util.ArrayList;

import com.w10.risk_game.commands.*;
import com.w10.risk_game.models.strategies.HumanPlayerStrategy;
import com.w10.risk_game.models.strategies.PlayerStrategy;

import java.util.List;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The Player class represents a player in this game, with properties such as
 * name, countries owned, orders, and leftover armies, as well as methods to
 * manipulate these properties.
 *
 * @author Darlene-Naz, Omnia Alam, Yajing Liu
 */
public class Player {
	private String d_name;
	private List<Country> d_countriesOwned;
	private List<Order> d_orders;
	private int d_leftoverArmies;
	private List<CardType> d_playerCards = new ArrayList<>();
	private boolean d_hasCommitted = false;

	private boolean d_hasConqueredNewCountry = false;

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * The behavioral strategy of the player. Default is Human. Must not be null.
	 */
	protected PlayerStrategy d_strategy;

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
	 * @param p_strategy
	 *            the strategy of the player
	 */

	public Player(String p_name, List<Country> p_countriesOwned, List<Order> p_orders, int p_leftoverArmies,
			PlayerStrategy p_strategy) {
		this.d_name = p_name;
		this.d_countriesOwned = p_countriesOwned;
		this.d_orders = p_orders;
		this.d_leftoverArmies = p_leftoverArmies;
		this.d_strategy = p_strategy;
	}

	public Player(String p_name, List<Country> p_countriesOwned, List<Order> p_orders, int p_leftoverArmies) {
		this.d_name = p_name;
		this.d_countriesOwned = p_countriesOwned;
		this.d_orders = p_orders;
		this.d_leftoverArmies = p_leftoverArmies;
		this.setStrategy(new HumanPlayerStrategy(this));
	}

	/**
	 * The `Player` constructor is initializing a new instance of the `Player` class
	 *
	 * @param d_name
	 *            player name
	 * @param d_countriesOwned
	 *            player owned countries
	 * @param d_orders
	 *            player orders
	 * @param d_leftoverArmies
	 *            player leftover armies
	 * @param d_playerCards
	 *            player cards
	 * @param d_hasCommitted
	 *            player has committed
	 */
	public Player(String d_name, List<Country> d_countriesOwned, List<Order> d_orders, int d_leftoverArmies,
			List<CardType> d_playerCards, boolean d_hasCommitted) {
		this.d_name = d_name;
		this.d_countriesOwned = d_countriesOwned;
		this.d_orders = d_orders;
		this.d_leftoverArmies = d_leftoverArmies;
		this.d_playerCards = d_playerCards;
		this.d_hasCommitted = d_hasCommitted;
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
	 * The function returns a boolean value indicating whether a player has
	 * committed
	 *
	 * @return boolean value indicating whether a player has committed
	 */
	public boolean getHasCommitted() {
		return d_hasCommitted;
	}

	/**
	 * The function sets the boolean value indicating whether a player has committed
	 *
	 * @param hasCommitted
	 *            boolean value indicating whether a player has committed
	 */
	public void setHasCommitted(boolean hasCommitted) {
		this.d_hasCommitted = hasCommitted;
	}

	/**
	 * The function adds a card to the player's list of cards.
	 *
	 * @param p_card
	 *            The parameter "p_card" is an object of type CardType.
	 */
	public void addCard(CardType p_card) {
		this.d_playerCards.add(p_card);
	}

	/**
	 * The function adds a order to the player's list of orders.
	 *
	 * @param p_order
	 *            The parameter "p_order" is an object of type Order.
	 */
	public void addOrder(Order p_order) {
		this.d_orders.add(p_order);
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
		for (Country country : d_countriesOwned) {
			if (country.getCountryId() == p_CountryId) {
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
	 * The function "nextOrder" returns and removes the first element from a list of
	 * player's orders.
	 *
	 * @return The method is returning an object of type Order.
	 */
	public Order nextOrder() {
		return d_orders.remove(0);
	}

	/**
	 * This function is used to check the input format for order.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public boolean checkValidOrderInput(String[] p_inputArray) {
		// Get the type of order from the input array
		String l_orderType = p_inputArray[0];
		// Based on the order type, validate the input and return a boolean result
		switch (l_orderType) {
			// Validate Deploy order input
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
				return Deploy.CheckValidDeployInput(p_inputArray);
			// Validate Advance order input
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE :
				return Advance.CheckValidAdvanceInput(p_inputArray);
			// Validate Bomb order input
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :
				return Bomb.CheckValidBombInput(p_inputArray);
			// Validate Blockade order input
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :
				return Blockade.CheckValidBlockadeInput(p_inputArray);
			// Validate Airlift order input
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :
				return Airlift.CheckValidAirliftInput(p_inputArray);
			// Validate Negotiate order input
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
				return Negotiate.CheckValidNegotiateInput(p_inputArray);
			// If the order is Commit, it's always valid
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT :
				return true;
			default :
				Logger.log(Constants.PLAYER_ISSUE_ORDER_INVALID_INPUT_TYPE);
				return false;
		}
	}

	/**
	 * The function issueOrder() calls the issueOrder() method of the d_strategy
	 * object.
	 */
	public void issueOrder() {
		d_strategy.issueOrder();
	}

	/**
	 * The function checks whether a player has a card of a given type.
	 *
	 * @param p_cardType
	 *            cart type
	 * @return boolean value to show whether the player has specific card
	 */
	public boolean hasCard(CardType p_cardType) {
		if (d_playerCards.contains(p_cardType)) {
			return true;
		}

		Logger.log(Constants.PLAYER_ISSUE_ORDER_NO_CARD);
		return false;
	}

	/**
	 * The function removes a card of a given type from a player's list of cards.
	 *
	 * @param p_cardType
	 *            card type
	 */
	public void removeCard(CardType p_cardType) {
		d_playerCards.remove(p_cardType);
	}

	/**
	 * The function removes a country of a given type from a player's list of
	 * countries owned.
	 *
	 * @param p_country
	 *            country
	 */
	public void removeCountry(Country p_country) {
		this.d_countriesOwned.remove(p_country);
	}

	/**
	 * The function adds a country into a player's list of countries owned.
	 *
	 * @param p_country
	 *            country
	 */
	public void addCountry(Country p_country) {
		this.d_countriesOwned.add(p_country);
	}

	public PlayerStrategy getStrategy() {
		return d_strategy;
	}

	public void setStrategy(PlayerStrategy p_strategy) {
		this.d_strategy = p_strategy;
	}

	/**
	 * The function returns a boolean value indicating whether the player has
	 * conquered a new country.
	 *
	 * @return The method is returning a boolean value.
	 */
	public boolean hasConqueredNewCountry() {
		return d_hasConqueredNewCountry;
	}

	/**
	 * The function sets the value of a boolean variable indicating whether a
	 * country has been conquered.
	 *
	 * @param d_hasConqueredNewCountry
	 *            This parameter is a boolean variable that represents whether or
	 *            not a player has conquered a new country.
	 */
	public void setHasConqueredNewCountry(boolean d_hasConqueredNewCountry) {
		this.d_hasConqueredNewCountry = d_hasConqueredNewCountry;
	}

}
