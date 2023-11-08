package com.w10.risk_game.models;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.w10.risk_game.commands.*;

import java.util.List;
import java.util.Scanner;

import com.w10.risk_game.GameEngine;
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

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

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
	 * @param card
	 *            The parameter "card" is an object of type CardType.
	 */
	public void addCard(CardType card) {
		this.d_playerCards.add(card);
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
	 * This method is used to issue an order This method gets the command input,
	 * creates an order object and adds it to the list of orders
	 */
	public void issueOrder() {
		setHasCommitted(false);
		String l_input = GameEngine.Command;
		String[] l_inputArray = l_input.split(" ");
		boolean l_again = true;
		boolean l_failed = false;
		while (l_again) {
			Scanner l_scanner = new Scanner(System.in);
			// Step 1: Handle invalid input
			if (l_failed) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_RESTART);
				Logger.log(Constants.USER_INPUT_REQUEST);
				l_input = l_scanner.nextLine();
				Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + l_input);

				// Check if user enters quit after an invalid order
				if (l_input.trim().equals(Constants.USER_INPUT_COMMAND_QUIT)) {
					GameEngine.Command = Constants.USER_INPUT_COMMAND_QUIT;
					break;
				}
				// Check if user enters showmap after an invalid order
				if (l_input.trim().equals(Constants.USER_INPUT_COMMAND_SHOWMAP)) {
					GameEngine.Phase.showMap();
					continue;
				}
				l_inputArray = l_input.split(" ");
			}
			// Step 2: Check the input format
			boolean isValidOrderInput = checkValidOrderInput(l_inputArray);
			if (!isValidOrderInput) {
				l_failed = true;
				continue;
			}
			String l_orderType = l_inputArray[0];
			switch (l_orderType) {
				// Step 3: Create order object and add it to the list of orders
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
					l_failed = !issueDeployOrder(l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE :
					l_failed = !issueAdvanceOrder(l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :
					l_failed = !issueBombOrder(l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :
					l_failed = !issueBlockadeOrder(l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :
					l_failed = !issueAirliftOrder(l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
					l_failed = !issueDiplomacyOrder(l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT :
					if (d_leftoverArmies == 0) {
						setHasCommitted(true);
						Logger.log(Constants.PLAYER_ISSUE_ORDER_COMMIT_SUCCEED);
						l_failed = false;
					} else {
						Logger.log(Constants.PLAYER_ISSUE_ORDER_COMMIT_INVALID);
						l_failed = true;
					}
					break;
				default :
					Logger.log(Constants.PLAYER_ISSUE_ORDER_INVALID_INPUT_TYPE);
					l_failed = true;
			}
			if (l_failed) {
				l_again = true;
			} else {
				l_again = false;
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
	 * This function is used to check the input format for order.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public boolean checkValidOrderInput(String[] p_inputArray) {
		String l_orderType = p_inputArray[0];
		switch (l_orderType) {
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
				return Deploy.CheckValidDeployInput(p_inputArray);
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE :
				return Advance.CheckValidAdvanceInput(p_inputArray);
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :
				return Bomb.CheckValidBombInput(p_inputArray);
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :
				return Blockade.CheckValidBlockadeInput(p_inputArray);
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :
				return Airlift.CheckValidAirliftInput(p_inputArray);
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
				return Negotiate.CheckValidNegotiateInput(p_inputArray);
			case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT :
				return true;
			default :
				Logger.log(Constants.PLAYER_ISSUE_ORDER_INVALID_INPUT_TYPE);
				return false;
		}
	}

	/**
	 * The function checks whether a player can advance X armies from a country Y
	 * onto a country Z or not
	 *
	 * @param p_noOfArmiesToAdvance
	 *            number of armies to advance
	 * @param p_currentArmiesOnCountry
	 *            current armies on country
	 * @param p_advanceFromCountryId
	 *            country id to advance from
	 * @return boolean value to show whether the player can advance
	 */
	private boolean checkValidAdvanceOrder(int p_noOfArmiesToAdvance, int p_currentArmiesOnCountry,
			int p_advanceFromCountryId) {
		int l_totalArmiesDeployed = p_currentArmiesOnCountry;
		for (Order l_order : this.getOrders()) {
			if ((l_order instanceof Deploy) && ((Deploy) l_order).getCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed += ((Deploy) l_order).getNum();
			} else if ((l_order instanceof Airlift)
					&& ((Airlift) l_order).getTargetCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed += ((Airlift) l_order).getArmyToAirlift();
			} else if ((l_order instanceof Airlift)
					&& ((Airlift) l_order).getSourceCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed -= ((Airlift) l_order).getArmyToAirlift();
			} else if ((l_order instanceof Advance)
					&& ((Advance) l_order).getCountryNameTo().getCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed += ((Advance) l_order).getNumOfArmies();
			} else if ((l_order instanceof Advance)
					&& ((Advance) l_order).getCountryNameFrom().getCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed -= ((Advance) l_order).getNumOfArmies();
			}
		}
		return l_totalArmiesDeployed >= p_noOfArmiesToAdvance;
	}

	/**
	 * The function try to add deploy order to the player's order list
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public boolean issueDeployOrder(String[] p_inputArray) {
		String l_countryId = p_inputArray[1];
		String l_num = p_inputArray[2];
		if (Deploy.ValidateOrder(this, l_countryId, l_num)) {
			Order order = new Deploy(this, Integer.parseInt(p_inputArray[1]), Integer.parseInt(p_inputArray[2]));
			d_orders.add(order);
			deployArmies(Integer.parseInt(p_inputArray[2]));
			Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
			return true;
		} else {
			Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
					Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY));
			return false;
		}
	}

	/**
	 * The function try to add advance order to the player's order list
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public boolean issueAdvanceOrder(String[] p_inputArray) {
		if (Advance.CheckValidAdvanceInput(p_inputArray)) {
			// Step 1: Get the variables needed to create an advance order
			String l_countryNameFrom = p_inputArray[1];
			String l_countryNameTo = p_inputArray[2];
			Country l_countryFrom = this.d_countriesOwned.stream()
					.filter(l_c -> l_c.getCountryName().equals(l_countryNameFrom)).findAny().orElse(null);
			Country l_countryTo = l_countryFrom != null
					? l_countryFrom.getNeighbors().values().stream()
							.filter(c -> c.getCountryName().equals(l_countryNameTo)).findAny().orElse(null)
					: null;
			int d_advanceArmies = Integer.parseInt(p_inputArray[3]);
			// Step 2: Check whether the order is valid
			if (l_countryFrom != null && l_countryTo != null && d_advanceArmies > 0 && checkValidAdvanceOrder(
					d_advanceArmies, l_countryFrom.getArmyCount(), l_countryFrom.getCountryId())) {
				Order l_order = new Advance(l_countryFrom, l_countryTo, d_advanceArmies);
				d_orders.add(l_order);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				if (l_countryFrom == null || l_countryTo == null) {
					if (l_countryFrom == null)
						Logger.log(MessageFormat.format(Constants.ADVANCE_INVALID_COUNTRY_NOT_OWNED, l_countryNameFrom,
								this.getName()));
					if (l_countryTo == null)
						Logger.log(MessageFormat.format(Constants.ADVANCE_INVALID_COUNTRY_NOT_NEIGHBOR, l_countryNameTo,
								l_countryNameFrom));
				} else if (d_advanceArmies <= 0)
					Logger.log(Constants.ADVANCE_INVALID_ARMY_LESS);
				else
					Logger.log(Constants.ADVANCE_INVALID_ARMY_MORE);
				return false;
			}
		}
		return false;
	}

	/**
	 * The function try to add bomb order to the player's order list
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public boolean issueBombOrder(String[] p_inputArray) {
		String l_countryIdToBomb = p_inputArray[1];
		if (hasCard(CardType.BOMB)) {
			if (Bomb.ValidateOrder(this, l_countryIdToBomb)) {
				Order order = new Bomb(this, l_countryIdToBomb);
				d_orders.add(order);
				removeCard(CardType.BOMB);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB));
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * The function try to add blockade order to the player's order list
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public boolean issueBlockadeOrder(String[] p_inputArray) {
		String l_countryIdToBlockade = p_inputArray[1];
		if (hasCard(CardType.BLOCKADE)) {
			if (Blockade.ValidateOrder(this, l_countryIdToBlockade)) {
				Order order = new Blockade(this, l_countryIdToBlockade);
				d_orders.add(order);
				removeCard(CardType.BLOCKADE);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE));
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * The function try to add diplomacy order to the player's order list
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public boolean issueDiplomacyOrder(String[] p_inputArray) {
		String l_playerId = p_inputArray[1];
		String l_playerName = l_playerId;
		if (hasCard(CardType.DIPLOMACY)) {
			if (Negotiate.ValidateOrder(this, l_playerName)) {
				Order order = new Negotiate(this, l_playerId);
				d_orders.add(order);
				removeCard(CardType.DIPLOMACY);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE));
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * The function issueAirliftOrder checks if the player has an airlift card and
	 * validates the order before creating a new airlift order and adding it to the
	 * list of orders.
	 *
	 * @param p_inputArray
	 *            An array of strings that represents the input command. The first
	 *            element is the command itself, and the following elements are the
	 *            parameters for the command.
	 * @return The method is returning a boolean value.
	 */
	public boolean issueAirliftOrder(String[] p_inputArray) {
		// airlift countryIdToAirliftFrom countryIdToAirlift NumberOfArmiesToAirlift
		String l_countryIdToAirliftFrom = p_inputArray[1];
		String l_countryIdToAirlift = p_inputArray[2];
		String l_airliftArmies = p_inputArray[3];
		if (hasCard(CardType.AIRLIFT)) {
			if (Airlift.ValidateOrder(this, l_countryIdToAirliftFrom, l_countryIdToAirlift, l_airliftArmies)) {
				Order order = new Airlift(this, l_countryIdToAirliftFrom, l_countryIdToAirlift, l_airliftArmies);
				d_orders.add(order);
				removeCard(CardType.AIRLIFT);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT));
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * The function checks whether a player has a card of a given type.
	 *
	 * @param p_cardType
	 *            cart type
	 * @return boolean value to show whether the player has specific card
	 */
	private boolean hasCard(CardType p_cardType) {
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
	private void removeCard(CardType p_cardType) {
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

}
