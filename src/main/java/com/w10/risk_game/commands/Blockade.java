package com.w10.risk_game.commands;

import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

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
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();
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
		Country l_countryToBlock = getCountryToBlock(d_player, d_countryIdToBlock);

		if (l_countryToBlock != null) {
			int l_initArmyCount = l_countryToBlock.getArmyCount();
			int l_newArmyCount = l_initArmyCount * 3;
			l_countryToBlock.setArmyCount(l_newArmyCount);
			l_countryToBlock.setOwner(null);

			List<Country> l_newCountriesOwned = d_player.getCountriesOwned().stream()
					.filter(country -> country.getCountryId() != l_countryToBlock.getCountryId())
					.collect(Collectors.toList());
			d_player.setCountriesOwned(l_newCountriesOwned);
		}
	}

	/**
	 * The function "validateOrder" checks if a player can block a specific country
	 * in a game.
	 *
	 * @param p_player
	 *            The player object that represents the player making the order.
	 * @param p_countryId
	 *            The p_countryId parameter is a String that represents the ID of a
	 *            country.
	 * @return The method is returning a boolean value.
	 */
	public static boolean validateOrder(Player p_player, String p_countryId) {
		Country l_countryToBlock = getCountryToBlock(p_player, p_countryId);

		return l_countryToBlock != null;
	}

	/**
	 * The function "getCountryToBlock" checks if a provided country ID is valid and
	 * belongs to a player, and returns the corresponding Country object if it
	 * exists.
	 *
	 * @param p_player
	 *            The player object that represents the player who wants to block a
	 *            country.
	 * @param p_countryId
	 *            The country ID that needs to be checked and blocked.
	 * @return The method is returning a Country object.
	 */
	public static Country getCountryToBlock(Player p_player, String p_countryId) {
		// Check country ID validity
		if (p_countryId == null) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INCORRECT);
			return null;
		}

		// Check if the provided country ID is valid and belongs to the player
		Country l_countryToBlock = null;
		Formatter l_formatter = new Formatter();

		try {
			int l_countryId = Integer.parseInt(p_countryId);

			l_countryToBlock = p_player.getCountriesOwned().stream().filter(c -> c.getCountryId() == l_countryId)
					.findFirst().orElse(null);

			if (l_countryToBlock == null) {
				l_formatter.format(Constants.BOMB_CARD_NO_VALID_COUNTRY, p_countryId);
				d_logger.log(l_formatter.toString());
			}
		} catch (Exception e) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INCORRECT);
		} finally {
			l_formatter.close();
		}

		return l_countryToBlock;
	}
}