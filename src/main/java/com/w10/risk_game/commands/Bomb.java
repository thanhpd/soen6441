package com.w10.risk_game.commands;

import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The Bomb class represents a player's order to bomb a specific country,
 * reducing its army count by half.
 */
public class Bomb extends Order {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	private final Player d_player;
	private final String d_countryIdToBomb;

	/**
	 * Constructor for Bomb class.
	 *
	 * @param p_player
	 *            The player who is issuing the order.
	 * @param p_countryId
	 *            The country id of the country to bomb.
	 */
	public Bomb(Player p_player, String p_countryId) {
		this.d_player = p_player;
		this.d_countryIdToBomb = p_countryId;
	}

	/**
	 * The function executes a bombing action on a specific country by reducing its
	 * army count by half.
	 */
	public void execute() {
		// Get the country to bomb based on the given country ID
		Country l_countryToBomb = GetCountryToBomb(d_player, d_countryIdToBomb);

		if (l_countryToBomb != null) {
			// Retrieve the initial army count of the country to be bombed
			int l_initArmyCount = l_countryToBomb.getArmyCount();
			int l_newArmyCount = (int) Math.floor((double) l_initArmyCount / 2);
			l_countryToBomb.setArmyCount(l_newArmyCount);
			Logger.log(MessageFormat.format(Constants.BOMB_SUCCEED, d_player.getName(),
					l_countryToBomb.getCountryName(), l_countryToBomb.getCountryName()));
		}
	}

	/**
	 * Checks if a player can bomb a specific country.
	 *
	 * @param p_player
	 *            The player object that represents the player who is trying to
	 *            validate the order.
	 * @param p_countryId
	 *            The country ID identifying the country that the player wants to
	 *            bomb.
	 * @return The method is returning a boolean value.
	 */
	public static boolean ValidateOrder(Player p_player, String p_countryId) {
		Country l_countryToBomb = GetCountryToBomb(p_player, p_countryId);

		return l_countryToBomb != null;
	}

	/**
	 * The function takes a player and a country ID as input and returns the country
	 * object that the player can bomb, based on the given country ID.
	 *
	 * @param p_player
	 *            The player object for which we want to find a country to bomb.
	 * @param p_countryId
	 *            The parameter "p_countryId" is a String representing the ID of the
	 *            country to be bombed.
	 * @return The method is returning a Country object.
	 */
	public static Country GetCountryToBomb(Player p_player, String p_countryId) {
		// Check if the given country ID is valid
		if (p_countryId == null) {
			// Log a message if the country ID is null
			Logger.log(MessageFormat.format(Constants.BOMB_CARD_NO_VALID_COUNTRY, p_countryId));
			return null;
		}

		// Attempt to find the country to bomb based on the provided country ID
		Country l_countryToBomb = null;

		try {
			int l_countryId = Integer.parseInt(p_countryId);
			// Get a list of foreign neighboring countries
			List<Country> l_neighbors = GetForeignNeighbors(p_player);

			// Find the country to bomb among the foreign neighboring countries
			l_countryToBomb = l_neighbors.stream().filter(neighbor -> neighbor.getCountryId() == l_countryId)
					.findFirst().orElse(null);

			// Log a message if the country to bomb is not found among the neighbors
			if (l_countryToBomb == null) {
				Logger.log(MessageFormat.format(Constants.BOMB_CARD_NO_VALID_COUNTRY, p_countryId));
			}
		} catch (Exception e) {
			// Log a message if an exception occurs during the process
			Logger.log(MessageFormat.format(Constants.BOMB_CARD_NO_VALID_COUNTRY, p_countryId));
		}

		return l_countryToBomb;
	}

	/**
	 * The function returns a list of countries that are owned by other players and
	 * are neighbors of the countries owned by the given player.
	 *
	 * @param p_player
	 *            The parameter "p_player" is of type Player and represents the
	 *            player for whom we want to find the foreign neighbors.
	 * @return The method is returning a list of countries that are owned by other
	 *         players and are neighbors of the countries owned by the given player.
	 */
	private static List<Country> GetForeignNeighbors(Player p_player) {
		// Get the list of countries owned by the player
		List<Country> l_countries = p_player.getCountriesOwned();
		// Initialize a list to store foreign neighboring countries
		List<Country> l_neighbors = new ArrayList<>();

		// Iterate through each country owned by the player
		for (Country l_country : l_countries) {
			// Retrieve the neighbors of the current country
			for (Country l_neighbor : l_country.getNeighbors().values()) {
				// Check if the neighbor is owned by a different player
				if (l_neighbor.getOwner() != p_player) {
					// Add the neighbor to the list of foreign neighboring countries
					l_neighbors.add(l_neighbor);
				}
			}
		}

		// Return the list of neighboring countries not owned by the player
		return l_neighbors;
	}

	/**
	 * This function is used to check the input format for airlift command.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public static boolean CheckValidBombInput(String[] p_inputArray) {
		// Step 1: Check the length of the input
		if (p_inputArray.length != 2) {
			Logger.log(
					MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "bomb", "two"));
			return false;
		}
		// Step 2: Check whether the country id is positive integer
		String l_countryId = p_inputArray[1];
		for (int i = 0; i < l_countryId.length(); i++) {
			if (!Character.isDigit(l_countryId.charAt(i))) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER);
				return false;
			}
		}
		return true;
	}

	/**
	 * The function validates and executes a bomb order for a player in a game.
	 *
	 * @param p_player
	 *            The player object that is issuing the bomb order.
	 * @param p_inputArray
	 *            An array of strings containing the user input for the bomb order
	 *            command. The first element of the array is the command itself, and
	 *            the second element is the country ID to bomb.
	 * @return The method returns a boolean value. If the bomb order is successfully
	 *         validated and executed, it returns true. If the bomb order is
	 *         incorrect or invalid, it returns false. If the player does not have a
	 *         bomb card, it also returns false.
	 */
	public static boolean ValidateIssueBombOrder(Player p_player, String[] p_inputArray) {
		String l_countryIdToBomb = p_inputArray[1];

		// Check if the player has a bomb card
		if (p_player.hasCard(CardType.BOMB)) {
			// Validate the bomb order
			if (ValidateOrder(p_player, l_countryIdToBomb)) {
				// If the order is valid, create a Bomb order and add it to the list of orders
				Order order = new Bomb(p_player, l_countryIdToBomb);
				p_player.addOrder(order);

				// Remove the bomb card from the player's hand
				p_player.removeCard(CardType.BOMB);

				// Log a success message for the bomb order execution
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true; // Return true for a successful order execution
			} else {
				// Log if the bomb order was incorrect or invalid
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB));
				return false; // Return false for an unsuccessful order execution
			}
		} else {
			return false; // Return false if the player does not have a bomb card
		}
	}
}
