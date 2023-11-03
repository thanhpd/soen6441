package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * The Bomb class represents a player's order to bomb a specific country,
 * reducing its army count by half.
 */
public class Bomb extends Order {
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();
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
		Country l_countryToBomb = getCountryToBomb(d_player, d_countryIdToBomb);

		if (l_countryToBomb != null) {
			int l_initArmyCount = l_countryToBomb.getArmyCount();
			int l_newArmyCount = l_initArmyCount / 2;
			l_countryToBomb.setArmyCount(l_newArmyCount);
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
	public static boolean validateOrder(Player p_player, String p_countryId) {
		Country l_countryToBomb = getCountryToBomb(p_player, p_countryId);

		return l_countryToBomb != null;
	}

	/**
	 * The function "getCountryToBomb" takes a player and a country ID as input and
	 * returns the country object that the player can bomb, based on the given
	 * country ID.
	 *
	 * @param p_player
	 *            The player object for which we want to find a country to bomb.
	 * @param p_countryId
	 *            The parameter "p_countryId" is a String representing the ID of the
	 *            country to be bombed.
	 * @return The method is returning a Country object.
	 */
	public static Country getCountryToBomb(Player p_player, String p_countryId) {
		Formatter l_formatter = new Formatter();
		l_formatter.format(Constants.BOMB_CARD_NO_VALID_COUNTRY, p_countryId);

		// Check country ID validity
		if (p_countryId == null) {
			d_logger.log(l_formatter.toString());
			l_formatter.close();
			return null;
		}

		// Check if there exists a foreign neighbor country with the given ID
		Country l_countryToBomb = null;

		try {
			int l_countryId = Integer.parseInt(p_countryId);
			List<Country> l_neighbors = getForeignNeighbors(p_player);

			l_countryToBomb = l_neighbors.stream().filter(neighbor -> neighbor.getCountryId() == l_countryId)
					.findFirst().orElse(null);

			if (l_countryToBomb == null) {
				d_logger.log(l_formatter.toString());
			}
		} catch (Exception e) {
			d_logger.log(l_formatter.toString());
		} finally {
			l_formatter.close();
		}

		return l_countryToBomb;
	}

	/**
	 * The function "getForeignNeighbors" returns a list of countries that are owned
	 * by other players and are neighbors of the countries owned by the given
	 * player.
	 *
	 * @param p_player
	 *            The parameter "p_player" is of type Player and represents the
	 *            player for whom we want to find the foreign neighbors.
	 * @return The method is returning a list of countries that are owned by other
	 *         players and are neighbors of the countries owned by the given player.
	 */
	private static List<Country> getForeignNeighbors(Player p_player) {
		List<Country> l_countries = p_player.getCountriesOwned();
		List<Country> l_neighbors = new ArrayList<>();

		for (Country l_country : l_countries) {
			for (Country l_neighbor : l_country.getNeighbors().values()) {
				if (l_neighbor.getOwner() != p_player) {
					l_neighbors.add(l_neighbor);
				}
			}
		}

		return l_neighbors;
	}
}
