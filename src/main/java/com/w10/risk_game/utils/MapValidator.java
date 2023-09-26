package com.w10.risk_game.utils;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

import java.util.*;

// TODO: comment all classes and methods and unit tests

/**
 * Needs to cover these cases: - Empty map - Countries: Non-existent continent -
 * Borders: Non-existent country - Borders: Self-referenced country -
 * Continents: 1 continent must have at least one country - Continents,
 * countries, borders: Check duplicated ids/names - File: countries must have at
 * least one neighbor - Countries: 1 country must have at least one neighbor
 * (fully connected graph check)
 */

public class MapValidator {
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

	protected static boolean isMapEmpty(GameMap p_gameMap) {
		return p_gameMap.getCountries().isEmpty() || p_gameMap.getContinents().isEmpty();
	}

	protected static boolean hasNonExistentContinent(GameMap p_gameMap) {
		var l_countries = p_gameMap.getCountries().values();

		return l_countries.stream()
				.anyMatch(country -> !p_gameMap.getContinents().containsKey(country.getContinentId()));
	}

	protected static boolean hasNonExistentNeighbor(GameMap p_gameMap) {
		var l_countries = p_gameMap.getCountries().values();

		return l_countries.stream().anyMatch(country -> country.getNeighbors().keySet().stream()
				.anyMatch(neighborId -> !p_gameMap.getCountries().containsKey(neighborId)));
	}

	protected static boolean hasSelfReferencingNeighbor(GameMap p_gameMap) {
		var l_countries = p_gameMap.getCountries().values();

		return l_countries.stream().anyMatch(country -> country.getNeighbors().values().stream()
				.anyMatch(neighbor -> neighbor.getCountryId() == country.getCountryId()));
	}

	protected static boolean areCountriesConnected(Map<Integer, Country> p_countryMap) {
		// Initialize the visited and to be visited list
		var l_visitedCountryIds = new HashSet<Integer>();
		var l_queuedCountryIds = new LinkedList<Integer>();

		// Set up the first element for visiting
		if (!p_countryMap.isEmpty()) {
			var l_firstNode = p_countryMap.values().iterator().next();

			if (l_firstNode != null) {
				l_queuedCountryIds.add(l_firstNode.getCountryId());
			}
		}

		// For each node visited from `l_queuedCountryIds`:
		// 1. Add the node to `l_visitedCountryIds`
		// 2. Add the neighbors of the node to `l_queuedCountryIds`
		// 3. Remove the node from `l_queuedCountryIds`
		while (!l_queuedCountryIds.isEmpty()) {
			Integer l_nextCountryId = l_queuedCountryIds.remove();
			Country l_visitingNode = p_countryMap.get(l_nextCountryId);

			if (l_visitingNode != null) {
				l_visitedCountryIds.add(l_visitingNode.getCountryId());

				for (Integer l_countryId : l_visitingNode.getNeighbors().keySet()) {
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

	protected static boolean areContinentsConnected(GameMap p_gameMap) {
		var continentIdToCountryIdsMap = getContinentIdToCountryIdsMap(p_gameMap);

		return continentIdToCountryIdsMap.entrySet().stream()
				.allMatch(entry -> areCountriesConnected(entry.getValue()));
	}

	protected static Map<Integer, Map<Integer, Country>> getContinentIdToCountryIdsMap(GameMap p_gameMap) {
		var l_continentIdToCountryIdsMap = new HashMap<Integer, Map<Integer, Country>>();

		for (Map.Entry<Integer, Country> l_entry : p_gameMap.getCountries().entrySet()) {
			var l_country = l_entry.getValue();
			var l_countryMap = l_continentIdToCountryIdsMap.get(l_country.getContinentId());
			if (l_countryMap != null) {
				l_countryMap.put(l_entry.getKey(), l_entry.getValue());
			} else {
				var l_newCountryMap = new HashMap<Integer, Country>();
				l_newCountryMap.put(l_entry.getKey(), l_entry.getValue());
				l_continentIdToCountryIdsMap.put(l_country.getContinentId(), l_newCountryMap);
			}
		}

		return l_continentIdToCountryIdsMap;
	}
}
