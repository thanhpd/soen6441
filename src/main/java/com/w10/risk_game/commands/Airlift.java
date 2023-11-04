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
	private final String d_countryIdToAirFrom;
	private final String d_countryIdToAir;
	private String d_ArmyToAirlift;

	/**
	 * Constructor for Airlift class.
	 *
	 * @param p_player
	 *            The player who is issuing the order.
	 * @param d_countryIdToAirFrom
	 *            The country id of the country to intiate the airlift from.
	 * @param d_countryIdToAir
	 *            The country id of the country to intiate the airlift to.
	 * @param d_ArmyToAirlift
	 *            The number to armies to airlift.
	 */
	public Airlift(Player p_player, String d_countryIdToAirFrom, String d_countryIdToAir, String d_ArmyToAirlift) {
		this.d_player = p_player;
		this.d_countryIdToAirFrom = d_countryIdToAirFrom;
		this.d_countryIdToAir = d_countryIdToAir;
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
		l_formatter.format(Constants.AIRLIFT_CARD_NOT_ENOUGH_ARMIES);

		Country l_countryToAirlift = getCountryForAirlift(d_player, d_countryIdToAir);
		Country l_countryToAirliftFrom = getCountryForAirlift(d_player, d_countryIdToAirFrom);

		if (l_countryToAirlift != null && l_countryToAirliftFrom != null) {
			int l_airliftCountryFromArmy = l_countryToAirliftFrom.getArmyCount();
			if (l_airliftCountryFromArmy > Integer.parseInt(d_ArmyToAirlift)) {
				int l_airliftCountryArmy = l_countryToAirlift.getArmyCount();
				// adjust the army count in countries
				l_countryToAirliftFrom.setArmyCount(l_airliftCountryFromArmy - Integer.parseInt(d_ArmyToAirlift));
				l_countryToAirlift.setArmyCount(Integer.parseInt(d_ArmyToAirlift) + l_airliftCountryArmy);
			} else {
				d_logger.log(l_formatter.toString());
				l_formatter.close();
			}
		}
	}

	/**
	 * The function "validateOrder" checks if a player can perform an airlift order
	 * on a specific country.
	 *
	 * @param p_player
	 *            The player object that represents the player who is trying to
	 *            validate the order.
	 * @param p_countryId
	 *            The p_countryId parameter is a String that represents the ID of a
	 *            country.
	 * @return The method is returning a boolean value.
	 */
	public static boolean validateOrder(Player p_player, String p_countryId) {
		Country l_countryToAirlift = getCountryForAirlift(p_player, p_countryId);

		return l_countryToAirlift != null;
	}

	/**
	 * The function "getCountryForAirlift" takes a player and a country ID as input
	 * and returns the corresponding country object if it is a valid neighbor of the
	 * player's countries.
	 *
	 * @param p_player
	 *            The player for whom we are finding the country for airlift.
	 * @param p_countryId
	 *            The parameter `p_countryId` is a String representing the ID of the
	 *            country for which the airlift is being requested.
	 * @return The method is returning a Country object.
	 */
	public static Country getCountryForAirlift(Player p_player, String p_countryId) {
		Formatter l_formatter = new Formatter();
		l_formatter.format(Constants.AIRLIFT_CARD_NO_VALID_COUNTRY, p_countryId);

		if (p_countryId == null) {
			// Log a message if the country ID is null
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
