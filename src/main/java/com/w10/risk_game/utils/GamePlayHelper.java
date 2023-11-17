package com.w10.risk_game.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

/**
 * The GamePlayHelper class provides a static method to get a list of countries
 * owned by other players that are neighbors of the countries owned by a given
 * player.
 */
public class GamePlayHelper {

	/**
	 * The constructor is private because this is a utility class.
	 */
	private GamePlayHelper() {
		throw new IllegalStateException("Utility class");
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
	public static List<Country> GetForeignNeighbors(Player p_player) {
		return p_player.getCountriesOwned().stream().flatMap(l_country -> l_country.getNeighbors().values().stream())
				.filter(l_neighbor -> p_player != l_neighbor.getOwner()).collect(Collectors.toList());
	}

}
