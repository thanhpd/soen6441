package com.w10.risk_game.utils;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.GameMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is to reinforce armies for each player. It uses list of countries
 * owned by the player to calculate basic reinforcement armies. It also
 * calculates the bonus armies based on the number of continents owned.
 *
 * @author Yajing LIU
 */
public class Reinforcements {

	private Reinforcements() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * This method is to calculate the reinforcement armies for each player. To
	 * calculate basic reinforcement armies, the occupied countries are divided by 3
	 * and rounded down. To calculate bonus armies, it needs two steps: 1. Group the
	 * countries owned by the player and all countries by continent. This step is
	 * finished by groupCountries method. 2. Compare the two groups and calculate
	 * the bonus armies.
	 *
	 * @param p_player
	 *            this parameter represents a player object. It is used to access
	 *            the information of owned countries and leftover armies.
	 * @param p_gameMap
	 *            this parameter represents a game map object. It is used to access
	 *            the information of all countries.
	 */
	public static void AssignPlayerReinforcements(Player p_player, GameMap p_gameMap) {
		// Step 1: Initialize the variables
		int l_armies = p_player.getLeftoverArmies();
		List<Country> l_playerCountries = p_player.getCountriesOwned();
		GameMap l_gameMap = p_gameMap;
		List<Country> allCountries = new ArrayList<>(l_gameMap.getCountries().values());
		// Step 2: Calculate bonus armies
		List<String> l_groupPlayerCountries = GroupCountries(l_playerCountries, p_gameMap);
		List<String> l_groupAllCountries = GroupCountries(allCountries, p_gameMap);
		int l_bonus = 0;
		for (int i = 0; i < l_groupPlayerCountries.size(); i++) {
			if (l_groupPlayerCountries.get(i).equals(l_groupAllCountries.get(i))) {
				l_bonus += p_gameMap.getContinents().get(i + 1).getBonus();
			}
		}
		// Step 3: Calculate the total number of reinforcement armies
		int l_countrySize = l_playerCountries.size();
		int l_reinforceArmies = (int) (Math.floor(l_countrySize / 3) + l_bonus);

		p_player.setLeftoverArmies((l_armies + l_reinforceArmies) < Constants.REINFORCEMENTS_MIN_NUMBER_OF_ARMIES
				? Constants.REINFORCEMENTS_MIN_NUMBER_OF_ARMIES
				: (l_armies + l_reinforceArmies));
	}

	/**
	 * This method is to group the countries by continent It creates a String list
	 * to hold the countries in each continent. The continent id - 1 is the index of
	 * the list. The continent id of each country is got. Based on these continent
	 * id, the country ids are extended to corresponding String. To prevent the
	 * country ids from being out of order, the country ids are sorted. After
	 * finishing grouping for both Player and GameMap, the Strings in these two
	 * lists can be compared directly to calculate the bonus armies.
	 *
	 * @param p_countries
	 *            This parameter represents a list of countries.
	 * @param p_gameMap
	 *            This parameter represents a game map object.
	 * @return A String list is returned. Each String represents the country ids in
	 *         a continent.
	 */
	public static List<String> GroupCountries(List<Country> p_countries, GameMap p_gameMap) {
		// Step 1: Initialize the variables
		GameMap l_gameMap = p_gameMap;
		int l_continentNum = l_gameMap.getContinents().size();
		List<String> l_groupCountries = new ArrayList<>();
		for (int i = 0; i < l_continentNum; i++) {
			l_groupCountries.add("");
		}
		// Step 2: Add the country ids to the corresponding continent string in the list
		for (Country country : p_countries) {
			int l_continentId = country.getContinentId();
			int l_countryId = country.getCountryId();
			if (l_groupCountries.get(l_continentId - 1) == "") {
				l_groupCountries.set(l_continentId - 1, l_groupCountries.get(l_continentId - 1) + l_countryId + "");
			} else {
				l_groupCountries.set(l_continentId - 1, l_groupCountries.get(l_continentId - 1) + " " + l_countryId);
			}
		}
		// Step 3: Sort the country ids in each continent string
		for (int i = 0; i < l_groupCountries.size(); i++) {
			List<String> countryIds = new ArrayList<>();
			for (String countryId : l_groupCountries.get(i).split(" ")) {
				countryIds.add(countryId);
			}
			Collections.sort(countryIds);
			l_groupCountries.set(i, String.join(" ", countryIds));
		}
		// Step 4: Return the list
		return l_groupCountries;
	}
}
