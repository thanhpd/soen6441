package com.w10.risk_game.models;

import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConquestGameMap {
	private final LinkedHashMap<String, Country> d_countriesByCountryId;
	private final LinkedHashMap<String, Continent> d_continentsByContinentId;

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	public ConquestGameMap() {
		this.d_countriesByCountryId = new LinkedHashMap<>();
		this.d_continentsByContinentId = new LinkedHashMap<>();
	}

	public Map<String, Country> getCountries() {
		return d_countriesByCountryId;
	}

	/**
	 * The function returns a map of continents, where the key is an integer and the
	 * value is a Continent object.
	 *
	 * @return A map of integers to continents is being returned.
	 */
	public LinkedHashMap<String, Continent> getContinents() {
		return d_continentsByContinentId;
	}

	/**
	 * The function adds a collection of countries to an existing map of countries.
	 *
	 * @param p_countries
	 *            The parameter "p_countries" is a Map object that maps Integer keys
	 *            to Country values.
	 */
	public void addCountries(Map<String, Country> p_countries) {
		this.d_countriesByCountryId.putAll(p_countries);
	}

	/**
	 * The function adds a collection of continents to a map of continents.
	 *
	 * @param p_continents
	 *            A map where the keys are integers and the values are objects of
	 *            type Continent.
	 */
	public void addContinents(Map<String, Continent> p_continents) {
		this.d_continentsByContinentId.putAll(p_continents);
	}
}
