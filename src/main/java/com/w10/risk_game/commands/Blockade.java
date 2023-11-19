package com.w10.risk_game.commands;

import java.text.MessageFormat;

import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The Blockade class represents a blockade order in a game, where a player
 * blocks a country and triples its army count while removing it from their
 * owned countries.
 */
public class Blockade extends Order {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	private final Player d_player;
	private final String d_countryIdToBlock;

	/**
	 * Constructor for Blockade class.
	 *
	 * @param p_player
	 *            The player who is issuing the order.
	 * @param p_countryId
	 *            The country id of the country to set up blockade.
	 */
	public Blockade(Player p_player, String p_countryId) {
		this.d_player = p_player;
		this.d_countryIdToBlock = p_countryId;
	}

	/**
	 * The function executes a block action on a country, tripling its army count,
	 * removing its owner, and updating the player's list of owned countries.
	 */
	@Override
	public void execute() {
		Country l_countryToBlock = GetCountryToBlock(d_player, d_countryIdToBlock);

		if (l_countryToBlock != null) {
			int l_initArmyCount = l_countryToBlock.getArmyCount();
			int l_newArmyCount = l_initArmyCount * 3;
			// Triple the army count of the country to block
			l_countryToBlock.setArmyCount(l_newArmyCount);

			// Remove the owner of the country to block
			l_countryToBlock.setOwner(null);

			// Remove the country from the player's list of owned countries
			d_player.getCountriesOwned().remove(l_countryToBlock);
			Logger.log(MessageFormat.format(Constants.BLOCKADE_SUCCEED, d_player.getName(),
					l_countryToBlock.getCountryName(), l_countryToBlock.getArmyCount()));
		}
	}

	/**
	 * The function checks if a player can block a specific country in a game.
	 *
	 * @param p_player
	 *            The player object that represents the player making the order.
	 * @param p_countryId
	 *            The p_countryId parameter is a String that represents the ID of a
	 *            country.
	 * @return The method is returning a boolean value.
	 */
	public static boolean ValidateOrder(Player p_player, String p_countryId) {
		Country l_countryToBlock = GetCountryToBlock(p_player, p_countryId);

		return l_countryToBlock != null;
	}

	/**
	 * The function checks if a provided country ID is valid and belongs to a
	 * player, and returns the corresponding Country object if it exists.
	 *
	 * @param p_player
	 *            The player object that represents the player who wants to block a
	 *            country.
	 * @param p_countryId
	 *            The country ID that needs to be checked and blocked.
	 * @return The method is returning a Country object.
	 */
	public static Country GetCountryToBlock(Player p_player, String p_countryId) {
		// Check country ID validity
		if (p_countryId == null) {
			Logger.log(MessageFormat.format(Constants.BLOCKADE_CARD_NO_VALID_COUNTRY, p_countryId));
			return null;
		}

		// Check if the provided country ID is valid and belongs to the player
		Country l_countryToBlock = null;

		try {
			int l_countryId = Integer.parseInt(p_countryId);

			// Find country with the given ID in the player's list of owned countries
			l_countryToBlock = p_player.getCountriesOwned().stream().filter(c -> c.getCountryId() == l_countryId)
					.findFirst().orElse(null);

			if (l_countryToBlock == null) {
				Logger.log(MessageFormat.format(Constants.BLOCKADE_CARD_NO_VALID_COUNTRY, p_countryId));
			}
		} catch (Exception e) {
			Logger.log(MessageFormat.format(Constants.BLOCKADE_CARD_NO_VALID_COUNTRY, p_countryId));
		}

		return l_countryToBlock;
	}

	/**
	 * This function is used to check the input format for blockade command.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public static boolean CheckValidBlockadeInput(String[] p_inputArray) {
		// Step 1: Check the length of the input
		if (p_inputArray.length != 2) {
			Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "blockade",
					"two"));
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
	 * The function try to add blockade order to the player's order list
	 *
	 * @param p_player
	 *            the player who issue the order
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public static boolean ValidateIssueBlockadeOrder(Player p_player, String[] p_inputArray) {
		String l_countryIdToBlockade = p_inputArray[1];

		// Check if the player has a blockade card
		if (p_player.hasCard(CardType.BLOCKADE)) {
			// Validate the blockade order
			if (ValidateOrder(p_player, l_countryIdToBlockade)) {
				// If the order is valid, create a Blockade order and add it to the list of
				// orders
				Order order = new Blockade(p_player, l_countryIdToBlockade);
				p_player.addOrder(order);

				// Remove the blockade card from the player's hand
				p_player.removeCard(CardType.BLOCKADE);

				// Log a success message for the blockade order execution
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true; // Return true for a successful order execution
			} else {
				// Log if the blockade order was incorrect or invalid
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE));
				return false; // Return false for an unsuccessful order execution
			}
		} else {
			return false; // Return false if the player does not have a blockade card
		}
	}
	/**
	 * The function returns the country ID to block.
	 *
	 * @return country id to block
	 */
	public String getCountryIdToBlock() {
		return d_countryIdToBlock;
	}
}
