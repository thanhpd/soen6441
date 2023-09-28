package com.w10.risk_game.utils;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam This class is the map editor service class. It adds
 *         removes countries continents and neighbour countries based on the
 *         user inputs While doing that it validates if the user inputs are
 *         valid or not If valid then perform the command if not valid then
 *         returns an error message
 */
public class MapEditor {
	private GameMap l_gameMap;

	// The `MapEditor` class has a constructor that takes a `GameMap` object as a
	// parameter. The purpose of
	// this constructor is to initialize the `l_gameMap` instance variable of the
	// `MapEditor` class with
	// the provided `originalMap` object. This allows the `MapEditor` class to
	// access and modify the game
	// map data.

	public MapEditor(GameMap originalMap) {
		this.l_gameMap = originalMap;
	}

	/**
	 * The function adds a country to a game map if it meets certain conditions,
	 * such as having a unique name and ID, and belonging to an existing continent.
	 *
	 * @param p_countryId
	 *            The unique identifier for the country.
	 * @param p_countryName
	 *            The name of the country that you want to add.
	 * @param p_continentName
	 *            The parameter p_continentName is a String that represents the name
	 *            of the continent to which the country belongs.
	 * @return The method returns a string message. The message can be one of the
	 *         following: - "Country Name is empty!" if the p_countryName parameter
	 *         is blank. - "Continent Name is empty!" if the p_continentName
	 *         parameter is blank. - "Country name already exists!" if the l_gameMap
	 *         already contains a country with the same name as p_countryName. -
	 *         "Country ID already
	 */
	public String addCountry(int p_countryId, String p_countryName, String p_continentName) {

		if (StringUtils.isBlank(p_countryName)) {
			return "Country Name is empty!";
		}
		if (StringUtils.isBlank(p_continentName)) {
			return "Continent Name is emplty!";
		}
		if (l_gameMap.containsCountry(p_countryName)) {
			return "Country name already exists!";
		}
		if (l_gameMap.containsCountry(p_countryId)) {
			return "Country ID already exists!";
		}
		if (l_gameMap.containsContinent(p_continentName) == false) {
			return "Continent doesnot exists!";
		}
		Continent l_continent = l_gameMap.getContinentByName(p_continentName);
		Country l_country = new Country(p_countryId, p_countryName, l_continent.getContinentId(), 0);
		l_gameMap.getCountries().put(p_countryId, l_country);
		return p_countryName + " is Added! and to the continent " + p_continentName;
	}

	/**
	 * The function adds a new continent to the game map if the continent name and
	 * id do not already exist.
	 *
	 * @param p_continentId
	 *            The continent ID is an integer value that uniquely identifies a
	 *            continent.
	 * @param p_continentName
	 *            The name of the continent that you want to add.
	 * @return The method is returning a string.
	 */
	public String addContinent(int p_continentId, String p_continentName) {
		if (l_gameMap.containsContinent(p_continentName)) {
			return "Continent name already exists!";
		}
		if (l_gameMap.containsContinent(p_continentId)) {
			return "Continent id already exists!";
		}
		Continent l_continent = new Continent(p_continentId, p_continentName, 0);
		l_gameMap.getContinents().put(l_gameMap.getContinents().size() + 1, l_continent);

		return p_continentName + " is added!";
	}

	/**
	 * The function adds a neighbor country to a given country in a game map.
	 *
	 * @param p_countryId
	 *            The ID of the country to which you want to add a neighbor.
	 * @param p_neighborCountryId
	 *            The ID of the country that you want to add as a neighbor to the
	 *            country with ID p_countryId.
	 * @return The method is returning a string that indicates the result of adding
	 *         a neighbor country to a given country.
	 */
	public String addNeighbor(int p_countryId, int p_neighborCountryId) {
		if (l_gameMap.containsCountry(p_countryId) == false) {
			return "Country does not exist! please add first";
		}
		if (l_gameMap.containsCountry(p_neighborCountryId) == false) {
			return "Neighbor country does not exist!please add first";
		}
		Country l_countryToAdd = l_gameMap.findCountry(p_countryId);
		if (l_countryToAdd.hasNeighbor(p_neighborCountryId)) {
			return "Connection already exists!";
		}
		Country l_negibor = l_gameMap.findCountry(p_neighborCountryId);
		Country l_country = l_gameMap.findCountry(p_countryId);
		l_country.addNeighbor(l_negibor);
		l_negibor.addNeighbor(l_country);

		return p_countryId + " added with " + p_neighborCountryId;
	}

	/**
	 * The function removes a country from a game map and updates the neighboring
	 * countries accordingly.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer representing the ID of the
	 *            country that needs to be removed.
	 * @return The method is returning a string.
	 */
	public String removeCountry(int p_countryId) {

		if (l_gameMap.containsCountry(p_countryId) == false) {
			return "Country id doesnot exists";
		}
		Country l_Country = l_gameMap.findCountry(p_countryId);
		for (Country l_neighbor : l_Country.getNeighbors().values()) {
			//
			l_neighbor.getNeighbors().remove(p_countryId);
		}

		// delete self from gameMap
		l_gameMap.getCountries().remove(l_Country.getCountryId());
		return p_countryId + "Country removed!";
	}

	/**
	 * The function removes a continent and all its associated countries from a game
	 * map.
	 *
	 * @param p_continentId
	 *            The parameter p_continentId is an integer that represents the ID
	 *            of the continent that needs to be removed.
	 * @return The method is returning a string.
	 */

	public String removeContinent(int p_continentId) {

		if (l_gameMap.containsContinent(p_continentId) == false) {
			return "Continent doesnot exists";
		}
		Continent l_toRemove = l_gameMap.getContinentById(p_continentId);
		String l_countriesRemoved = "";
		ArrayList<Country> l_countriesToRemove = l_gameMap.getCountriesOfContinent(l_toRemove.getContinentId());
		for (Country l_country : l_countriesToRemove) {
			removeCountry(l_country.getCountryId());
			l_countriesRemoved = l_country.getCountryName() + ", ";
		}
		// Self remove
		l_gameMap.getContinents().remove(l_toRemove.getContinentId());
		return p_continentId + " removed!" + l_countriesRemoved + "these countries also removed!";
	}

	/**
	 * The function removes a neighbor connection between two countries in a game
	 * map.
	 *
	 * @param p_countryId
	 *            The ID of the country from which you want to remove a neighbor.
	 * @param p_neighborCountryId
	 *            The ID of the neighbor country that you want to remove the
	 *            connection with.
	 * @return The method is returning a string.
	 */
	public String removeNeighbor(int p_countryId, int p_neighborCountryId) {
		if (l_gameMap.containsCountry(p_countryId) == false) {
			return "Country does not exist! please add first";
		}
		if (l_gameMap.containsCountry(p_neighborCountryId) == false) {
			return "Neighbor country does not exist!please add first";
		}
		Country l_countryToRemove = l_gameMap.findCountry(p_countryId);
		if (l_countryToRemove.hasNeighbor(p_neighborCountryId) == false) {
			return "Connection doesnot exists!";
		}
		l_gameMap.findCountry(p_countryId).getNeighbors().remove(p_neighborCountryId);
		l_gameMap.findCountry(p_neighborCountryId).getNeighbors().remove(p_countryId);

		return p_countryId + " removed from " + p_neighborCountryId;
	}
}
