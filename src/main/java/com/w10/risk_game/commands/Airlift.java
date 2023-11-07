package com.w10.risk_game.commands;

import java.text.MessageFormat;
import java.util.List;

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
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

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
		Country l_targetCountry = getCountryForAirlift(d_player, d_targetCountryId);
		Country l_sourceCountry = getCountryForAirlift(d_player, d_sourceCountryId);

		// Check if both target and source countries are valid
		if (l_targetCountry != null && l_sourceCountry != null) {
			int l_airliftCountryFromArmy = l_sourceCountry.getArmyCount();
			if (l_airliftCountryFromArmy >= Integer.parseInt(d_armyToAirlift)) {
				int l_airliftCountryArmy = l_targetCountry.getArmyCount();
				// adjust the army count in countries
				l_sourceCountry.setArmyCount(l_airliftCountryFromArmy - Integer.parseInt(d_armyToAirlift));
				l_targetCountry.setArmyCount(Integer.parseInt(d_armyToAirlift) + l_airliftCountryArmy);
			} else {
				// If source country doesn't have enough armies, log a message
				d_logger.log(Constants.AIRLIFT_CARD_NOT_ENOUGH_ARMIES);
			}
		}
	}

	/**
	 * The function checks if a player can perform an airlift order by validating
	 * the source and target countries, as well as the number of armies to airlift.
	 *
	 * @param p_targetCountryId
	 *            The ID of the country where the player wants to airlift their
	 *            armies to. The "armiesToAirlift" parameter is a String that
	 *            represents the number of layer wants to airlift from the source
	 *            country to the target country.
	 * @return The method is returning a boolean value.
	 */
	public static boolean ValidateOrder(Player p_player, String p_sourceCountryId, String p_targetCountryId,
			String p_armiesToAirlift) {
		Country l_sourceCountry = getCountryForAirlift(p_player, p_sourceCountryId);
		Country l_targetCountry = getCountryForAirlift(p_player, p_targetCountryId);
		int l_armyToAirlift = Integer.parseInt(p_armiesToAirlift);

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
	public static Country getCountryForAirlift(Player p_player, String p_countryId) {
		if (p_countryId == null) {
			// Log a message if the country ID is null
			d_logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId));
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
				d_logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId));
			}
		} catch (Exception e) {
			// Log a message if an exception occurs while processing
			d_logger.log(MessageFormat.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId));
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
			d_logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "airlift",
					"four"));
			return false;
		}
		// Step 2: Check whether the country id is positive integer
		String l_sourceCountryId = p_inputArray[1];
		String l_targetCountryId = p_inputArray[2];
		String l_num = p_inputArray[3];
		for (int i = 0; i < l_sourceCountryId.length(); i++) {
			if (!Character.isDigit(l_sourceCountryId.charAt(i))) {
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER);
				return false;
			}
		}
		for (int i = 0; i < l_targetCountryId.length(); i++) {
			if (!Character.isDigit(l_targetCountryId.charAt(i))) {
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER);
				return false;
			}
		}
		// Step 3: Check whether the number of armies is positive integer
		for (int i = 0; i < l_num.length(); i++) {
			if (!Character.isDigit(l_num.charAt(i))) {
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_ARMIES_NOT_INTEGER);
				return false;
			}
		}
		return true;
	}

	public int getTargetCountryId() {
		return Integer.parseInt(this.d_targetCountryId);
	}

	public int getArmyToAirlift() {
		return Integer.parseInt(this.d_armyToAirlift);
	}
}
