package com.w10.risk_game.commands;

import java.text.MessageFormat;
import java.util.List;

import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The Airlift class represents an order to move armies from one country to
 * another in a game.
 *
 * @author Tazin Morshed
 */
public class Airlift extends Order {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	private final Player d_player;
	private final String d_sourceCountryId;
	private final String d_targetCountryId;
	private String d_armyToAirlift;

	/**
	 * Constructor for Airlift class.
	 *
	 * @param p_player
	 *            The player who is issuing the order.
	 * @param d_countryIdToAirFrom
	 *            The country id of the country to intiate th airlift from.
	 * @param d_countryIdToAir
	 *            The country id of the country to intiate th airlift to.
	 * @param d_armyToAirlift
	 *            The number to armies to airlift.
	 */
	public Airlift(Player p_player, String d_countryIdToAirFrom, String d_countryIdToAir, String d_armyToAirlift) {
		this.d_player = p_player;
		this.d_sourceCountryId = d_countryIdToAirFrom;
		this.d_targetCountryId = d_countryIdToAir;
		this.d_armyToAirlift = d_armyToAirlift;
	}

	/**
	 * The function executes an airlift card action by transferring a specified
	 * number of armies from one country to another, if there are enough armies in
	 * the source country.
	 */
	@Override
	public void execute() {
		// Retrieves the country for airlift
		Country l_targetCountry = GetCountryForAirlift(d_player, d_targetCountryId);
		Country l_sourceCountry = GetCountryForAirlift(d_player, d_sourceCountryId);

		// Check if both target and source countries are valid
		if (l_targetCountry != null && l_sourceCountry != null) {
			int l_airliftCountryFromArmy = l_sourceCountry.getArmyCount();
			if (l_airliftCountryFromArmy >= Integer.parseInt(d_armyToAirlift)) {
				int l_airliftCountryArmy = l_targetCountry.getArmyCount();
				// adjust the army count in countries
				l_sourceCountry.setArmyCount(l_airliftCountryFromArmy - Integer.parseInt(d_armyToAirlift));
				l_targetCountry.setArmyCount(Integer.parseInt(d_armyToAirlift) + l_airliftCountryArmy);
				// log a success message
				Logger.log(MessageFormat.format(Constants.AIRLIFT_SUCCEED, d_armyToAirlift,
						l_sourceCountry.getCountryName(), l_targetCountry.getCountryName()));
			} else {
				// If source country doesn't have enough armies, log a message
				Logger.log(Constants.AIRLIFT_CARD_NOT_ENOUGH_ARMIES);
			}
		}
	}

	/**
	 * The function "ValidateOrder" checks if a player can perform an airlift order
	 * by validating the source and target countries, as well as the number of
	 * armies to airlift.
	 *
	 * @param p_player
	 *            The player object representing the player who is initiating the
	 *            order.
	 * @param p_sourceCountryId
	 *            The ID of the country from which the player wants to airlift
	 *            armies.
	 * @param p_targetCountryId
	 *            The target country ID is a string that represents the ID of the
	 *            country where the player wants to airlift their armies to.
	 * @param p_armiesToAirlift
	 *            The parameter "p_armiesToAirlift" represents the number of armies
	 *            that the player wants to airlift from the source country to the
	 *            target country.
	 * @return The method is returning a boolean value.
	 */
	public static boolean ValidateOrder(Player p_player, String p_sourceCountryId, String p_targetCountryId,
			String p_armiesToAirlift) {
		// Retrieve the source and target countries and the number of armies to be
		// airlifted
		Country l_sourceCountry = GetCountryForAirlift(p_player, p_sourceCountryId);
		Country l_targetCountry = GetCountryForAirlift(p_player, p_targetCountryId);
		int l_armyToAirlift = Integer.parseInt(p_armiesToAirlift);

		// Check if the player has the source and target countries
		if (!p_player.hasCountry(Integer.parseInt(p_sourceCountryId))) {
			// Log a message if the source country does not belong to the player
			Logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_sourceCountryId,
					p_player.getName()));
		}

		if (!p_player.hasCountry(Integer.parseInt(p_targetCountryId))) {
			// Log a message if the target country does not belong to the player
			Logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_targetCountryId,
					p_player.getName()));
		}

		// Check if both source and target countries exist and if the source country has
		// enough armies to airlift
		if (l_sourceCountry != null && l_targetCountry != null && l_armyToAirlift > l_sourceCountry.getArmyCount()) {
			// Log a message if the source country doesn't have enough armies for airlift
			Logger.log(MessageFormat.format(Constants.AIRLIFT_COUNTRY_NOT_ENOUGH_ARMY, l_sourceCountry.getCountryName(),
					l_sourceCountry.getArmyCount()));
		}

		// Return a boolean indicating if the order is valid based on multiple
		// conditions
		return l_sourceCountry != null && l_targetCountry != null && l_armyToAirlift > 0
				&& l_armyToAirlift <= l_sourceCountry.getArmyCount();
	}

	/**
	 * Retrieves the country intended for an airlift based on the player and country
	 * ID. If the country ID is null or an error occurs during processing, it logs
	 * an appropriate message.
	 *
	 * @param p_player
	 *            The player for whom the country is being fetched.
	 * @param p_countryId
	 *            The ID of the country to be retrieved.
	 * @return The Country intended for airlift; returns null if the country ID is
	 *         null or not found.
	 */
	public static Country GetCountryForAirlift(Player p_player, String p_countryId) {
		if (p_countryId == null) {
			// Log a message if the country ID is null
			Logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId));
			return null;
		}

		Country l_countryToAirlift = null;

		try {
			int l_countryId = Integer.parseInt(p_countryId);
			// Get the list of countries owned by the player
			List<Country> l_countries = p_player.getCountriesOwned();

			// Filter the list of countries using the countryId
			l_countryToAirlift = l_countries.stream().filter(country -> country.getCountryId() == l_countryId)
					.findFirst().orElse(null);

			if (l_countryToAirlift == null) {
				// Log a message if the requested country is not a valid neighbor
				Logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId));
			}
		} catch (Exception e) {
			// Log a message if an exception occurs while processing
			Logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId));
		}
		return l_countryToAirlift;
	}

	/**
	 * This function is used to check the input format for airlift command.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public static boolean CheckValidAirliftInput(String[] p_inputArray) {
		// Step 1: Check the length of the input
		if (p_inputArray.length != 4) {
			Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "airlift",
					"four"));
			return false;
		}
		// Step 2: Check whether the country id is positive integer
		String l_sourceCountryId = p_inputArray[1];
		String l_targetCountryId = p_inputArray[2];
		String l_num = p_inputArray[3];
		for (int i = 0; i < l_sourceCountryId.length(); i++) {
			if (!Character.isDigit(l_sourceCountryId.charAt(i))) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER);
				return false;
			}
		}
		for (int i = 0; i < l_targetCountryId.length(); i++) {
			if (!Character.isDigit(l_targetCountryId.charAt(i))) {
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
		return true;
	}

	/**
	 * The function returns the target country ID as an integer.
	 *
	 * @return The method is returning an integer value.
	 */
	public int getTargetCountryId() {
		return Integer.parseInt(this.d_targetCountryId);
	}

	/**
	 * The function returns the number of armies to be airlifted as an integer.
	 *
	 * @return The method is returning an integer value.
	 */
	public int getArmyToAirlift() {
		return Integer.parseInt(this.d_armyToAirlift);
	}

	/**
	 * The function returns the value of the variable d_sourceCountryId.
	 *
	 * @return The method is returning a String value.
	 */
	public int getSourceCountryId() {
		return Integer.parseInt(d_sourceCountryId);
	}

	/**
	 * The function issueAirliftOrder checks if the player has an airlift card and
	 * validates the order before creating a new airlift order and adding it to the
	 * list of orders.
	 *
	 * @param p_player
	 *            the player who issue the order
	 * @param p_inputArray
	 *            An array of strings that represents the input command. The first
	 *            element is the command itself, and the following elements are the
	 *            parameters for the command.
	 * @return The method is returning a boolean value.
	 */
	public static boolean ValidateIssueAirliftOrder(Player p_player, String[] p_inputArray) {
		// Extract information from the player's input array
		String l_countryIdToAirliftFrom = p_inputArray[1];
		String l_countryIdToAirlift = p_inputArray[2];
		String l_airliftArmies = p_inputArray[3];

		// Check if the player has an airlift card
		if (p_player.hasCard(CardType.AIRLIFT)) {
			// Validate the airlift order
			if (ValidateOrder(p_player, l_countryIdToAirliftFrom, l_countryIdToAirlift, l_airliftArmies)) {
				// If the airlift order is valid, create an Airlift order and add it to the list
				// of orders
				Order order = new Airlift(p_player, l_countryIdToAirliftFrom, l_countryIdToAirlift, l_airliftArmies);
				p_player.addOrder(order);

				// Remove the airlift card from the player's hand
				p_player.removeCard(CardType.AIRLIFT);

				// Log a success message for the airlift order execution
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true; // Return true for a successful order execution
			} else {
				// Log if the airlift order was incorrect or invalid
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT));
				return false; // Return false for an unsuccessful order execution
			}
		} else {
			return false; // Return false if the player does not have an airlift card
		}
	}

}
