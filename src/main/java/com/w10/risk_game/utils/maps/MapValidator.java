package com.w10.risk_game.utils.maps;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.util.*;

/**
 * The MapValidator class provides methods to validate a game map by checking
 * for various conditions such as empty map, non-existent continents or
 * neighbors, self-referencing neighbors, inaccessible countries, and
 * disconnected continents.
 */
public class MapValidator {

	/**
	 * Private constructor to prevent unexpected instance initialization
	 */
	private MapValidator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * The function IsMapCorrect checks if a given game map is valid by checking for
	 * various conditions such as empty map, non-existent continents or neighbors,
	 * self-referencing neighbors, inaccessible countries, and disconnected
	 * continents.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map. It contains information about the countries, continents,
	 *            and their connections in the game.
	 * @return The method IsMapCorrect is returning a boolean value indicating if
	 *         the map satisfies all conditions.
	 */
	public static boolean IsMapCorrect(GameMap p_gameMap) {
		LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
		// Check if the map is empty
		if (IsMapEmpty(p_gameMap)) {
			Logger.log(Constants.MAP_VALIDATOR_EMPTY_MAP);
			return false;
		}

		// Check if the country is referring to a non-declared continent
		if (HasNonExistentContinent(p_gameMap)) {
			Logger.log(Constants.MAP_VALIDATOR_CONTINENT_NOT_DECLARED);
			return false;
		}

		// Check if the country is referring to a non-existent country as a neighbor
		if (HasNonExistentNeighbor(p_gameMap)) {
			Logger.log(Constants.MAP_VALIDATOR_NEIGHBOR_NOT_DECLARED);
			return false;
		}

		// Check if the country is referring to itself as a neighbor
		if (HasSelfReferencingNeighbor(p_gameMap)) {
			Logger.log(Constants.MAP_VALIDATOR_COUNTRY_AS_ITS_OWN_NEIGHBOR);
			return false;
		}

		// Check if all countries are connected using DFS
		if (!AreCountriesConnected(p_gameMap.getCountries())) {
			Logger.log(Constants.MAP_VALIDATOR_COUNTRY_INACCESSIBLE);
			return false;
		}

		// Check if each continent is a connected subgraph
		if (!AreContinentsConnected(p_gameMap)) {
			Logger.log(Constants.MAP_VALIDATOR_COUNTRY_NOT_FULLY_CONNECTED);
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
	protected static boolean IsMapEmpty(GameMap p_gameMap) {
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
	protected static boolean HasNonExistentContinent(GameMap p_gameMap) {
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
	protected static boolean HasNonExistentNeighbor(GameMap p_gameMap) {
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
	protected static boolean HasSelfReferencingNeighbor(GameMap p_gameMap) {
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
	protected static boolean AreCountriesConnected(Map<Integer, Country> p_countryMap) {
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
	protected static boolean AreContinentsConnected(GameMap p_gameMap) {
		Map<Integer, Map<Integer, Country>> continentIdToCountryIdsMap = GetContinentIdToCountryIdsMap(p_gameMap);

		return continentIdToCountryIdsMap.entrySet().stream().allMatch(entry -> AreCountriesConnected(entry.getValue()))
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
	protected static Map<Integer, Map<Integer, Country>> GetContinentIdToCountryIdsMap(GameMap p_gameMap) {
		Map<Integer, Map<Integer, Country>> l_continentIdToCountriesMap = new HashMap<>();

		// Loop over the country map to create a map of integer continentId to the map
		// of country and countryId
		for (Map.Entry<Integer, Country> l_entry : p_gameMap.getCountries().entrySet()) {
			Country l_country = l_entry.getValue();
			Map<Integer, Country> l_countryMap = l_continentIdToCountriesMap.get(l_country.getContinentId());

			// Create a new map if this is the first access, else put the new country entry
			// in the map
			if (l_countryMap != null) {
				l_countryMap.put(l_entry.getKey(), l_entry.getValue());
			} else {
				Map<Integer, Country> l_newCountryMap = new HashMap<>();
				l_newCountryMap.put(l_entry.getKey(), l_entry.getValue());
				l_continentIdToCountriesMap.put(l_country.getContinentId(), l_newCountryMap);
			}
		}

		return l_continentIdToCountriesMap;
	}
}
