package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.text.MessageFormat;
import java.util.List;

/**
 * This class is the deploy order class. It extends the Order class. It defines
 * the specific executing behavior for deploying.
 *
 * @see Order
 * @author Yajing Liu
 */
public class Deploy extends Order {
	private Player d_player;
	private int d_countryId;
	private int d_num;
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * This is a constructor of the Deploy class
	 *
	 * @param p_player
	 *            The player who issues the order
	 * @param p_countryId
	 *            The country id that the order is issued to
	 * @param p_num
	 *            The number of armies that the order is issued to
	 */
	public Deploy(Player p_player, int p_countryId, int p_num) {
		this.d_player = p_player;
		this.d_countryId = p_countryId;
		this.d_num = p_num;
	}

	/**
	 * The function returns the country ID.
	 *
	 * @return The method is returning the value of the variable "d_countryId".
	 */
	public int getCountryId() {
		return d_countryId;
	}

	/**
	 * The function "getNum" returns the value of the variable "d_num".
	 *
	 * @return The method is returning the value of the variable "d_num".
	 */
	public int getNum() {
		return d_num;
	}

	/**
	 * This method extends the execute method in the Order class. It gets the number
	 * of armies and deploys them to the country
	 */
	public void execute() {
		// Get the list of countries owned by the player
		List<Country> l_countries = d_player.getCountriesOwned();

		// Iterate through the player's owned countries
		for (Country l_country : l_countries) {
			// Check if the country ID matches the specified ID for deployment
			if (l_country.getCountryId() == d_countryId) {
				// Retrieve the current army count of the selected country
				int l_countryArmies = l_country.getArmyCount();

				// Increase the army count of the selected country by the specified number of
				// armies
				l_country.setArmyCount(l_countryArmies + d_num);

				// Log a message indicating successful deployment of armies
				Logger.log(MessageFormat.format(Constants.DEPLOY_SUCCEED, d_player.getName(), d_num, d_countryId));
			}
		}
	}

	/**
	 * This method is used to validate the order. It checks whether the country id
	 * and the number of armies are valid
	 *
	 * @param p_player
	 *            The player who issues the order
	 * @param p_countryId
	 *            The country id that the order is issued to
	 * @param p_num
	 *            The number of armies that the order is issued to
	 * @return boolean value to show whether the order is valid
	 */
	public static boolean ValidateOrder(Player p_player, String p_countryId, String p_num) {
		// Retrieve the list of countries owned by the player
		List<Country> l_countries = p_player.getCountriesOwned();

		// Check if the specified country ID is valid
		boolean l_validCountry = CheckValidCountry(l_countries, p_countryId);

		// Convert the specified army count to an integer
		int l_num = Integer.parseInt(p_num);

		// Check if the specified number of armies is valid
		boolean l_validNum = CheckValidArmy(p_player, l_num);

		// Return true if both the country ID and the number of armies are valid, else
		// return false
		if (l_validCountry && l_validNum) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is used to check the country id for deploy command. The country
	 * id should be one of the countries owned by the player
	 *
	 * @param p_countries
	 *            the list of countries owned by the player
	 * @param p_countryId
	 *            the country id
	 * @return boolean value to show whether the country id is valid
	 */
	public static boolean CheckValidCountry(List<Country> p_countries, String p_countryId) {
		for (Country country : p_countries) {
			if (country.getCountryId() == Integer.parseInt(p_countryId)) {
				return true;
			}
		}
		Logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_COUNTRY);
		return false;
	}

	/**
	 * The function CheckValidArmy checks if the number of armies to be deployed is
	 * valid for a given player.
	 *
	 * @param p_player
	 *            The player object that represents a player in the game.
	 * @param p_num
	 *            The number of armies that the player wants to deploy.
	 * @return The method is returning a boolean value.
	 */
	public static boolean CheckValidArmy(Player p_player, int p_num) {
		// Check if the specified number of armies is zero or negative
		if (p_num <= 0) {
			// Log a message if the number of armies is zero or negative
			Logger.log(Constants.DEPLOY_INVALID_ARMIES_ZERO);
			return false; // Indicates that the specified number of armies is invalid
		}

		// Check if the specified number of armies exceeds the player's available armies
		if (p_num > p_player.getLeftoverArmies()) {
			// Log a message if the number of armies exceeds the player's available armies
			Logger.log(Constants.DEPLOY_INVALID_ARMIES);
			return false; // Indicates that the specified number of armies is invalid
		}

		// If the specified number of armies is valid, return true
		return true;
	}

	/**
	 * This function is used to check the input format for deploy command. The input
	 * should have three parts (one string and two positive integers)
	 *
	 * @param p_inputArray
	 *            the input string
	 * @return boolean value to show whether the input format is valid
	 */
	public static boolean CheckValidDeployInput(String[] p_inputArray) {
		// Step 1: Check the length of the input
		if (p_inputArray.length != 3) {
			Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "deploy",
					"three"));
			return false;
		}
		// Step 2: Check whether the country id is positive integer
		String l_countryId = p_inputArray[1];
		String l_num = p_inputArray[2];
		for (int i = 0; i < l_countryId.length(); i++) {
			if (!Character.isDigit(l_countryId.charAt(i))) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER);
				return false;
			}
		}
		// Step 3: Check whether the number of armies is positive integer
		for (int i = 0; i < l_num.length(); i++) {
			if (!Character.isDigit(l_num.charAt(i))) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_ARMIES_NOT_INTEGER);
				return false;
			}
		}
		// Step 4: Return true if the input format is valid
		return true;
	}
}
