package com.w10.risk_game.utils;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

import java.util.*;

/**
 * The MapValidator class provides methods to validate a game map by checking
 * for various conditions such as empty map, non-existent continents or
 * neighbors, self-referencing neighbors, inaccessible countries, and
 * disconnected continents.
 */
public class MapValidator {

	private MapValidator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * The function isMapCorrect checks if a given game map is valid by checking for
	 * various conditions such as empty map, non-existent continents or neighbors,
	 * self-referencing neighbors, inaccessible countries, and disconnected
	 * continents.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method isMapCorrect is returning a boolean value indicating if
	 *         the map satisfies all conditions.
	 */
	public static boolean isMapCorrect(GameMap p_gameMap) {
		if (isMapEmpty(p_gameMap)) {
			System.out.println("MapValidator: The map is empty!");
			return false;
		}

		if (hasNonExistentContinent(p_gameMap)) {
			System.out.println("MapValidator: The continent(s) of some countries are not declared!");
			return false;
		}

		if (hasNonExistentNeighbor(p_gameMap)) {
			System.out.println("MapValidator: The neighbor(s) of some countries are not declared!");
			return false;
		}

		if (hasSelfReferencingNeighbor(p_gameMap)) {
			System.out.println("MapValidator: Some countries are referring to itself as a neighbor");
			return false;
		}

		if (!areCountriesConnected(p_gameMap.getCountries())) {
			System.out.println("MapValidator: Some countries are inaccessible");
			return false;
		}

		if (!areContinentsConnected(p_gameMap)) {
			System.out.println("MapValidator: Some continents are not fully-connected");
			return false;
		}

		return true;
	}

	/**
	 * The function checks if a game map is empty by verifying if the list of
	 * countries or continents is empty.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method is returning a boolean value, which indicates whether the
	 *         given game map is empty or not.
	 */
	protected static boolean isMapEmpty(GameMap p_gameMap) {
		return p_gameMap.getCountries().isEmpty() || p_gameMap.getContinents().isEmpty();
	}

	/**
	 * The function checks if there are any countries in the game map that have a
	 * non-existent continent.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is an instance of the `GameMap` class.
	 *            It represents the game map that contains information about the
	 *            countries and continents in the game.
	 * @return The method is returning a boolean value.
	 */
	protected static boolean hasNonExistentContinent(GameMap p_gameMap) {
		Collection<Country> l_countries = p_gameMap.getCountries().values();

		return l_countries.stream()
				.anyMatch(country -> !p_gameMap.getContinents().containsKey(country.getContinentId()));
	}

	/**
	 * The function checks if any country in a game map has a neighbor that does not
	 * exist in the map.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method is returning a boolean value.
	 */
	protected static boolean hasNonExistentNeighbor(GameMap p_gameMap) {
		Collection<Country> l_countries = p_gameMap.getCountries().values();

		return l_countries.stream().anyMatch(country -> country.getNeighbors().keySet().stream()
				.anyMatch(neighborId -> !p_gameMap.getCountries().containsKey(neighborId)));
	}

	/**
	 * The function checks if any country in a game map has a neighbor that
	 * references itself.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method is returning a boolean value.
	 */
	protected static boolean hasSelfReferencingNeighbor(GameMap p_gameMap) {
		Collection<Country> l_countries = p_gameMap.getCountries().values();

		return l_countries.stream().anyMatch(country -> country.getNeighbors().values().stream()
				.anyMatch(neighbor -> neighbor.getCountryId() == country.getCountryId()));
	}

	/**
	 * The function checks if all countries in a given map are connected to each
	 * other.
	 *
	 * @param p_countryMap
	 *            A map that contains the countries as values, where the key is an
	 *            integer representing the country's ID.
	 * @return The method is returning a boolean value. It returns true if all the
	 *         countries in the given map are connected, and false otherwise.
	 */
	protected static boolean areCountriesConnected(Map<Integer, Country> p_countryMap) {
		// Initialize the visited and to be visited list
		Set<Integer> l_visitedCountryIds = new HashSet<>();
		LinkedList<Integer> l_queuedCountryIds = new LinkedList<>();

		// Set up the first element for visiting
		if (!p_countryMap.isEmpty()) {
			Country l_firstCountry = p_countryMap.values().iterator().next();

			if (l_firstCountry != null) {
				l_queuedCountryIds.add(l_firstCountry.getCountryId());
			}
		}

		// For each node visited from `l_queuedCountryIds`:
		// 1. Add the node to `l_visitedCountryIds`
		// 2. Add the neighbors of the node to `l_queuedCountryIds`
		// 3. Remove the node from `l_queuedCountryIds`
		while (!l_queuedCountryIds.isEmpty()) {
			Integer l_nextCountryId = l_queuedCountryIds.remove();
			Country l_visitingCountry = p_countryMap.get(l_nextCountryId);

			if (l_visitingCountry != null) {
				l_visitedCountryIds.add(l_visitingCountry.getCountryId());

				for (Integer l_countryId : l_visitingCountry.getNeighbors().keySet()) {
					if (!l_queuedCountryIds.contains(l_countryId) && !l_visitedCountryIds.contains(l_countryId)) {
						l_queuedCountryIds.add(l_countryId);
					}
				}
			}
		}

		// Countries are fully connected if the size of unique visited countries
		// equals the size of the country list
		return l_visitedCountryIds.size() == p_countryMap.size();
	}

	/**
	 * The function checks if all continents in a game map are connected and if the
	 * number of continents in the map matches the number of continents in the
	 * continentIdToCountryIdsMap.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method is returning a boolean value.
	 */
	protected static boolean areContinentsConnected(GameMap p_gameMap) {
		Map<Integer, Map<Integer, Country>> continentIdToCountryIdsMap = getContinentIdToCountryIdsMap(p_gameMap);

		return continentIdToCountryIdsMap.entrySet().stream().allMatch(entry -> areCountriesConnected(entry.getValue()))
				&& p_gameMap.getContinents().size() == continentIdToCountryIdsMap.size();
	}

	/**
	 * The function `getContinentIdToCountryIdsMap` creates a map that maps
	 * continent IDs to maps of country IDs to country objects.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method is returning a map that maps continent IDs to maps of
	 *         country IDs to countries.
	 */
	protected static Map<Integer, Map<Integer, Country>> getContinentIdToCountryIdsMap(GameMap p_gameMap) {
		Map<Integer, Map<Integer, Country>> l_continentIdToCountryIdsMap = new HashMap<>();

		for (Map.Entry<Integer, Country> l_entry : p_gameMap.getCountries().entrySet()) {
			Country l_country = l_entry.getValue();
			Map<Integer, Country> l_countryMap = l_continentIdToCountryIdsMap.get(l_country.getContinentId());
			if (l_countryMap != null) {
				l_countryMap.put(l_entry.getKey(), l_entry.getValue());
			} else {
				Map<Integer, Country> l_newCountryMap = new HashMap<>();
				l_newCountryMap.put(l_entry.getKey(), l_entry.getValue());
				l_continentIdToCountryIdsMap.put(l_country.getContinentId(), l_newCountryMap);
			}
		}

		return l_continentIdToCountryIdsMap;
	}
}
