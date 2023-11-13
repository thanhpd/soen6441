package com.w10.risk_game.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapValidator;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GameMap class represents a game map that contains countries, continents,
 * and players, and provides methods for accessing and manipulating these
 * objects.
 *
 * @author Omnia Alam, Darlene-Naz
 */
public class GameMap {
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;
	private Map<Integer, Player> d_player;

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * This constructor initializes the `d_countries`, `d_continents`, and
	 * `d_player` variables as empty HashMaps. These HashMaps will be used to store
	 * the countries, continents, and players in the game map.
	 */
	public GameMap() {
		this.d_countries = new HashMap<>();
		this.d_continents = new HashMap<>();
		this.d_player = new HashMap<>();
	}

	/**
	 * The function checks if a map has been created by verifying if there are
	 * continents and countries present.
	 *
	 * @return The method is returning a boolean value.
	 */
	public boolean isMapCreated() {
		return !this.d_continents.isEmpty() && !this.d_countries.isEmpty();
	}

	/**
	 * The function getCountries() returns a map of countries.
	 *
	 * @return A map of integers to Country objects is being returned.
	 */
	public Map<Integer, Country> getCountries() {
		return d_countries;
	}

	/**
	 * The function "findCountry" returns a Country object based on the given
	 * country ID.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer representing the unique
	 *            identifier of a country.
	 * @return The method is returning an object of type Country.
	 */
	public Country findCountry(int p_countryId) {
		return d_countries.get(p_countryId);
	}

	/**
	 * The function returns a player object based on the country ID.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer that represents the
	 *            country ID of the player.
	 * @return The method is returning a Player object.
	 */
	public Player getPlayerByCountry(int p_countryId) {
		return d_player.get(p_countryId);
	}

	/**
	 * The function returns a Country object by searching for a matching country
	 * name in a collection of countries.
	 *
	 * @param p_countryName
	 *            The parameter "p_countryName" is a String representing the name of
	 *            the country that you want to retrieve.
	 * @return The method is returning an object of type "Country".
	 */
	public Country getCountryByName(String p_countryName) {
		return d_countries.values().stream().filter(country -> country.getCountryName().equals(p_countryName))
				.findFirst().get();
	}

	/**
	 * The function returns a Continent object by searching for a matching continent
	 * name in a collection of continents.
	 *
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String that represents the
	 *            name of the continent that player would want to retrieve.
	 * @return The method is returning a Continent object.
	 */
	public Continent getContinentByName(String p_continentName) {
		return d_continents.values().stream().filter(counrty -> counrty.getContinentName().equals(p_continentName))
				.findFirst().get();
	}

	/**
	 * The function returns a map of continents, where the key is an integer and the
	 * value is a Continent object.
	 *
	 * @return A map of integers to continents is being returned.
	 */
	public Map<Integer, Continent> getContinents() {
		return d_continents;
	}

	/**
	 * The function checks if a given country ID exists in a map of countries.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer representing the ID of a
	 *            country.
	 * @return A boolean value is being returned.
	 */
	public boolean containsCountry(int p_countryId) {
		return d_countries.containsKey(p_countryId);
	}

	/**
	 * The function checks if a given country name exists in a collection of
	 * countries.
	 *
	 * @param p_countryName
	 *            The parameter `p_countryName` is a String representing the name of
	 *            a country.
	 * @return The method is returning a boolean value.
	 */
	public boolean containsCountry(String p_countryName) {
		return d_countries.values().stream().anyMatch(l_country -> l_country.getCountryName().equals(p_countryName));
	}

	/**
	 * The function checks if a given continent ID exists in a map of continents.
	 *
	 * @param p_id
	 *            The parameter "p_id" is an integer representing the ID of a
	 *            continent.
	 * @return The method is returning a boolean value.
	 */
	public boolean containsContinent(int p_id) {
		return d_continents.containsKey(p_id);
	}

	/**
	 * The function checks if a given continent name exists in a collection of
	 * continents.
	 *
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String representing the name
	 *            of a continent.
	 * @return The method is returning a boolean value.
	 */
	public boolean containsContinent(String p_continentName) {
		return d_continents.values().stream()
				.anyMatch(l_continent -> l_continent.getContinentName().equals(p_continentName));
	}

	/**
	 * The function returns a continent object based on the given continent ID.
	 *
	 * @param p_continentId
	 *            The parameter p_continentId is an integer representing the unique
	 *            identifier of a continent.
	 * @return The method is returning a Continent object.
	 */
	public Continent getContinentById(int p_continentId) {
		return d_continents.get(p_continentId);
	}

	/**
	 * The function getCountriesOfContinent returns a list of countries that belong
	 * to a specific continent.
	 *
	 * @param p_continentId
	 *            The parameter "p_continentId" represents the ID of the continent
	 *            for which you want to retrieve the countries.
	 * @return The method is returning a list of countries that belong to a specific
	 *         continent.
	 */
	public List<Country> getCountriesOfContinent(int p_continentId) {
		return d_countries.values().stream().filter(country -> country.getContinentId() == p_continentId)
				.collect(Collectors.toList());
	}

	/**
	 * The function adds a collection of countries to an existing map of countries.
	 *
	 * @param p_countries
	 *            The parameter "p_countries" is a Map object that maps Integer keys
	 *            to Country values.
	 */
	public void addCountries(Map<Integer, Country> p_countries) {
		this.d_countries.putAll(p_countries);
	}

	/**
	 * The function adds a collection of continents to a map of continents.
	 *
	 * @param p_continents
	 *            A map where the keys are integers and the values are objects of
	 *            type Continent.
	 */
	public void addContinents(Map<Integer, Continent> p_continents) {
		this.d_continents.putAll(p_continents);
	}

	/**
	 * The saveMap function saves the game map to a file in a specific format.
	 *
	 * @param p_filePath
	 *            The file path where the map will be saved.
	 */
	public void saveMap(String p_filePath) {
		if (MapValidator.IsMapCorrect(this))
			try (FileWriter l_fileWriter = new FileWriter(p_filePath)) {
				// Initialize PrintWriter object
				PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
				l_printWriter.println(Constants.MAP_READER_MAP + Constants.NEW_LINE + Constants.MAP_READER_CONTINENTS);
				// Writes continents details to new map file
				for (Continent continent : this.d_continents.values()) {
					l_printWriter.format("%s %d%n", continent.getContinentName(), continent.getBonus());
				}
				l_printWriter.println(Constants.NEW_LINE + Constants.MAP_READER_COUNTRIES);
				// Assigns new continent id
				int l_continentNumber = 1;
				for (Continent l_continent : this.d_continents.values()) {
					// Writes country details to new map file
					for (Country country : l_continent.getCountries()) {
						l_printWriter.format("%d %s %d%n", country.getCountryId(), country.getCountryName(),
								l_continentNumber);
					}
					l_continentNumber++;
				}
				// Writes border details to new map file
				l_printWriter.println(Constants.NEW_LINE + Constants.MAP_READER_BORDERS);
				for (Country country : this.d_countries.values()) {
					l_printWriter.format("%d %s%n", country.getCountryId(), country.getNeighbors().keySet().stream()
							.map(Object::toString).collect(Collectors.joining(Constants.SPACE)));
				}
				Logger.log(Constants.MAP_SAVE_SUCCESS);
				l_printWriter.close();
			} catch (IOException e) {
				Logger.log(Constants.MAP_SAVE_ERROR);
			}
	}

}
