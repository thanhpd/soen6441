package com.w10.risk_game.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The ConquestGameMap class represents a game map consisting of countries and
 * continents, and provides methods to add countries and continents to the map.
 */
public class ConquestGameMap {
	private final LinkedHashMap<String, Country> d_countriesByCountryName;
	private final LinkedHashMap<String, Continent> d_continentsByContinentName;

	/**
	 * The constructor initializes the map of countries and the map of continents.
	 */
	public ConquestGameMap() {
		this.d_countriesByCountryName = new LinkedHashMap<>();
		this.d_continentsByContinentName = new LinkedHashMap<>();
	}

	/**
	 * The getCountries() function returns a map of countries, where the keys are
	 * country names and the values are Country objects.
	 *
	 * @return A map of countries, where the keys are strings representing the
	 *         country names and the values are objects of type Country.
	 */
	public Map<String, Country> getCountries() {
		return d_countriesByCountryName;
	}

	/**
	 * The function returns a LinkedHashMap containing continents mapped to their
	 * names.
	 *
	 * @return A LinkedHashMap with String keys and Continent values is being
	 *         returned.
	 */
	public LinkedHashMap<String, Continent> getContinents() {
		return d_continentsByContinentName;
	}

	/**
	 * The function adds a map of countries to an existing map of countries, using
	 * the country name as the key.
	 *
	 * @param p_countries
	 *            A map of country names (String) to Country objects.
	 */
	public void addCountries(Map<String, Country> p_countries) {
		this.d_countriesByCountryName.putAll(p_countries);
	}

	/**
	 * The function adds a collection of continents to a map.
	 *
	 * @param p_continents
	 *            A map containing continent names as keys and Continent objects as
	 *            values.
	 */
	public void addContinents(Map<String, Continent> p_continents) {
		this.d_continentsByContinentName.putAll(p_continents);
	}
}
