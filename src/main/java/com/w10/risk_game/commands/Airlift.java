package com.w10.risk_game.commands;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The Airlift class represents an order to move armies from one country to
 * another in a game.
 */
public class Airlift extends Order {
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	private final Player d_player;
	private final String d_sourceCountryId;
	private final String d_targetCountryId;
	private String d_ArmyToAirlift;

	/**
	 * Constructor for Airlift class.
	 *
	 * @param p_player
	 *                             The player who is issuing the order.
	 * @param d_countryIdToAirFrom
	 *                             The country id of the country to intiate th
	 *                             airlift from.
	 * @param d_countryIdToAir
	 *                             The country id of the country to intiate th
	 *                             airlift to.
	 * @param d_ArmyToAirlift
	 *                             The number to armies to airlift.
	 */
	public Airlift(Player p_player, String d_countryIdToAirFrom, String d_countryIdToAir, String d_ArmyToAirlift) {
		this.d_player = p_player;
		this.d_sourceCountryId = d_countryIdToAirFrom;
		this.d_targetCountryId = d_countryIdToAir;
		this.d_ArmyToAirlift = d_ArmyToAirlift;
	}

	/**
	 * The function executes an airlift card action by transferring a specified
	 * number of armies from one country to another, if there are enough armies in
	 * the source country.
	 */
	@Override
	public void execute() {
		Formatter l_formatter = new Formatter();

		Country l_targetCountry = getCountryForAirlift(d_player, d_targetCountryId);
		Country l_sourceCountry = getCountryForAirlift(d_player, d_sourceCountryId);

		if (l_targetCountry != null && l_sourceCountry != null) {
			int l_airliftCountryFromArmy = l_sourceCountry.getArmyCount();
			if (l_airliftCountryFromArmy >= Integer.parseInt(d_ArmyToAirlift)) {
				int l_airliftCountryArmy = l_targetCountry.getArmyCount();
				// adjust the army count in countries
				l_sourceCountry.setArmyCount(l_airliftCountryFromArmy - Integer.parseInt(d_ArmyToAirlift));
				l_targetCountry.setArmyCount(Integer.parseInt(d_ArmyToAirlift) + l_airliftCountryArmy);
			} else {
				l_formatter.format(Constants.AIRLIFT_CARD_NOT_ENOUGH_ARMIES);
				d_logger.log(l_formatter.toString());
				l_formatter.close();
			}
		}
	}


/**
 * The function "ValidateOrder" checks if a player can perform an airlift order by validating the
 * source and target countries, as well as the number of armies to airlift.
 * 
 *                     yer The player object representing the player who is init
	 *                    ating the order.
 *                     rceCountryId The ID of the country from which the player wants to airlift armies.
 * @param p_targetCountryId The ID of the country where the player wants to airlift their armies to.
 *                     sToAirlift The "armiesToAirlift" parameter is a String t
	 *                    at represents the number of
 *                     layer wants to airlift from the source country to the target country.
 * @return The method is returning a boolean value.
 */
	public static boolean ValidateOrder(Player p_player, String p_sourceCountryId, String p_targetCountryId,
			String armiesToAirlift) {
		Country l_sourceCountry = getCountryForAirlift(p_player, p_sourceCountryId);
		Country l_targetCountry = getCountryForAirlift(p_player, p_targetCountryId);
		int l_armyToAirlift = Integer.parseInt(armiesToAirlift);

		return l_sourceCountry != null && l_targetCountry != null && l_armyToAirlift > 0
				&& l_armyToAirlift <= l_sourceCountry.getArmyCount();
	}

	/**
	 * The function "getCountryForAirlift" takes a player and a country ID as input
	 * and returns the corresponding country object if it is a valid neighbor of the
	 * player's countries.
	 *
	 * @param p_player
	 *                    The player for whom we are finding the country fo airlift.
	 * @param p_countryId
	 *                    The parameter `p_countryId` is a String representing th ID
	 *                    of the
	 *                    country for which the airlift is being requested.
	 * @return The method is returning a Country object.
	 */
	public static Country getCountryForAirlift(Player p_player, String p_countryId) {
		Formatter l_formatter = new Formatter();

		if (p_countryId == null) {
			// Log a message if the country ID is null
			l_formatter.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId);
			d_logger.log(l_formatter.toString());
			l_formatter.close();
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
				d_logger.log(l_formatter.toString());
			}
		} catch (Exception e) {
			// Log a message if an exception occurs while processing
			d_logger.log(l_formatter.toString());
		} finally {
			l_formatter.close();
		}
		return l_countryToAirlift;
	}
}
