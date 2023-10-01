package com.w10.risk_game.utils;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/**
 * This class operates on the current Game map. It adds and removes Gamemap
 * elements
 *
 * @author Omnia Alam
 */
public class MapEditor {
	private GameMap l_gameMap;

	public MapEditor(GameMap originalMap) {
		this.l_gameMap = originalMap;
	}

	/**
	 * The function adds a new country to a game map if it meets certain conditions,
	 * and returns a message indicating whether the addition was successful or not.
	 *
	 * @param p_countryId
	 *            The unique identifier for the country.
	 * @param p_countryName
	 *            The name of the country you want to add.
	 * @param p_continentId
	 *            The parameter p_continentId represents the ID of the continent to
	 *            which the country will be added.
	 * @return The method returns a string message indicating the result of adding a
	 *         country.
	 */
	public String addCountry(int p_countryId, String p_countryName, int p_continentId) {

		if (StringUtils.isBlank(p_countryName)) {
			return "Country Name is empty!";
		}
		if (l_gameMap.containsCountry(p_countryName)) {
			return "Country name already exists!";
		}
		if (l_gameMap.containsCountry(p_countryId)) {
			return "Country ID already exists!";
		}
		if (l_gameMap.containsContinent(p_continentId) == false) {
			return "Continent does not exists!";
		}
		Continent l_continent = l_gameMap.getContinentById(p_continentId);
		Country l_country = new Country(p_countryId, p_countryName, l_continent.getContinentId(), 0);
		l_gameMap.getCountries().put(p_countryId, l_country);
		return p_countryName + " is Added! and to the continent with id: " + p_continentId;
	}

	/**
	 * The function adds a new continent to the game map if the continent name and
	 * id do not already exist.
	 *
	 * @param p_bonus
	 *            The bonus of the new continent
	 * @param p_continentName
	 *            The name of the continent that you want to add.
	 * @return The method returns a string message indicating the result of adding a
	 *         continent.
	 */
	public String addContinent(String p_continentName, int p_bonus) {
		if (l_gameMap.containsContinent(p_continentName)) {
			return "Continent name already exists!";
		}

		int l_newContinentId = l_gameMap.getContinents().size() + 1;
		Continent l_continent = new Continent(l_newContinentId, p_continentName, 0);
		l_gameMap.getContinents().put(l_newContinentId, l_continent);

		return p_continentName + " is added!";
	}

	/**
	 * The function adds a neighbor country to a given country in a game map.
	 *
	 * @param p_countryId
	 *            The ID of the country to which you want to add a neighbor.
	 * @param p_neighborCountryId
	 *            An integer that represents the ID of the neighbor country that we
	 *            want to add to the country with ID p_countryId.
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
		Country l_neighbor = l_gameMap.findCountry(p_neighborCountryId);
		Country l_country = l_gameMap.findCountry(p_countryId);
		l_country.addNeighbor(l_neighbor);
		l_neighbor.addNeighbor(l_country);

		return p_countryId + " added with " + p_neighborCountryId;
	}

	/**
	 * The function removes a country from a game map and updates the neighboring
	 * countries accordingly.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer representing the ID of the
	 *            country that needs to be removed.
	 * @return The method is returning a string that indicates the result of
	 *         removing the country.
	 */
	public String removeCountry(int p_countryId) {

		if (l_gameMap.containsCountry(p_countryId) == false) {
			return "Country id does not exists";
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
	 *            The parameter p_continentId is the ID of the continent that needs
	 *            to be removed from the game map.
	 * @return The method is returning a string that indicates the result of
	 *         removing the continent.
	 */
	public String removeContinent(int p_continentId) {

		if (l_gameMap.containsContinent(p_continentId) == false) {
			return "Continent does not exists";
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
	 *            The ID of the country that you want to remove as a neighbor from
	 *            the country with ID p_countryId.
	 * @return The method is returning a string indicating the result of the
	 *         operation.
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
			return "Connection does not exists!";
		}
		l_gameMap.findCountry(p_countryId).getNeighbors().remove(p_neighborCountryId);
		l_gameMap.findCountry(p_neighborCountryId).getNeighbors().remove(p_countryId);

		return p_countryId + " removed from " + p_neighborCountryId;
	}
}
