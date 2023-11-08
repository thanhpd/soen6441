package com.w10.risk_game.utils;

import java.util.List;

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
	 *                        The unique identifier for the country.
	 * @param p_countryName
	 *                        The name of the country you want to add.
	 * @param p_continentName
	 *                        The parameter p_continentName represents the ID of the
	 *                        continent
	 *                        to which the country will be added.
	 * @return The method returns a string message indicating the result of adding a
	 *         country.
	 */
	public String addCountry(int p_countryId, String p_countryName, String p_continentName) {

		// Check if the provided country name is blank
		if (StringUtils.isBlank(p_countryName)) {
			return Constants.MAP_EDITOR_EMPTY_COUNTRY_NAME;
		}

		// Check if the country name already exists in the map
		if (l_gameMap.containsCountry(p_countryName)) {
			return Constants.MAP_EDITOR_COUNTRY_NAME_EXIST;
		}

		// Check if the country ID already exists in the map
		if (l_gameMap.containsCountry(p_countryId)) {
			return Constants.MAP_EDITOR_COUNTRY_ID_EXIST;
		}

		// Check if the provided continent name does not exist in the map
		if (!l_gameMap.containsContinent(p_continentName)) {
			return Constants.MAP_EDITOR_CONTINENT_NOT_EXIST;
		}

		// Fetch the continent object based on the provided continent name
		Continent l_continent = l_gameMap.getContinentByName(p_continentName);

		// Create a new country object and add it to the map and the continent
		Country l_country = new Country(p_countryId, p_countryName, l_continent.getContinentId(), 0);
		l_gameMap.getCountries().put(p_countryId, l_country);
		l_continent.addCountry(l_country);

		// Return a success message indicating the addition of the country to the
		// specified continent
		return p_countryName + Constants.MAP_EDITOR_ADD_COUNTRY + p_continentName;
	}

	/**
	 * The function adds a new continent to the game map if the continent name and
	 * id do not already exist.
	 *
	 * @param p_continentName
	 *                        The name of the continent that you want to add.
	 * @param p_bonus
	 *                        The bonus of the new continent
	 * @return The method returns a string message indicating the result of adding a
	 *         continent.
	 */
	public String addContinent(String p_continentName, int p_bonus) {

		// Check if the provided continent name already exists in the map
		if (l_gameMap.containsContinent(p_continentName)) {
			return Constants.MAP_EDITOR_CONTINENT_NAME_EXIST;
		}

		// Calculate a new ID for the continent
		int l_newContinentId = l_gameMap.getContinents().size() + 1;

		// Create a new Continent object with the provided details and add it to the map
		Continent l_continent = new Continent(l_newContinentId, p_continentName, p_bonus);
		l_gameMap.getContinents().put(l_newContinentId, l_continent);

		// Return a success message indicating the addition of the new continent
		return p_continentName + Constants.MAP_EDITOR_ADD_CONTINENT;
	}

	/**
	 * The function adds a neighbor country to a given country in a game map.
	 *
	 * @param p_countryId
	 *                            The ID of the country to which you want to add a
	 *                            neighbor.
	 * @param p_neighborCountryId
	 *                            An integer that represents the ID of the neighbor
	 *                            country that we
	 *                            want to add to the country with ID p_countryId.
	 * @return The method is returning a string that indicates the result of adding
	 *         a neighbor country to a given country.
	 */
	public String addNeighbor(int p_countryId, int p_neighborCountryId) {
		// Check if the first country exists in the game map.
		if (l_gameMap.containsCountry(p_countryId) == false) {
			return Constants.MAP_EDITOR_COUNTRY_NOT_EXIST;
		}
		// Check if the neighbor country exists in the game map.
		if (l_gameMap.containsCountry(p_neighborCountryId) == false) {
			return Constants.MAP_EDITOR_NEIGHBOR_COUNTRY_NOT_EXIST;
		}
		Country l_countryToAdd = l_gameMap.findCountry(p_countryId);
		// Check if the neighbor relationship already exists.
		if (l_countryToAdd.hasNeighbor(p_neighborCountryId)) {
			return Constants.MAP_EDITOR_CONNECTION_EXIST;
		}
		Country l_neighbor = l_gameMap.findCountry(p_neighborCountryId);
		Country l_country = l_gameMap.findCountry(p_countryId);
		// Add the neighbor relationship between both countries.
		l_country.addNeighbor(l_neighbor);
		l_neighbor.addNeighbor(l_country);

		return p_countryId + Constants.MAP_EDITOR_ADD_NEIGHBOR + p_neighborCountryId;
	}

	/**
	 * The function removes a country from a game map and updates the neighboring
	 * countries accordingly.
	 *
	 * @param p_countryId
	 *                    The parameter p_countryId is an integer representing the
	 *                    ID of the
	 *                    country that needs to be removed.
	 * @return The method is returning a string that indicates the result of
	 *         removing the country.
	 */
	public String removeCountry(int p_countryId) {

		if (l_gameMap.containsCountry(p_countryId) == false) {
			return Constants.MAP_EDITOR_COUNTRY_ID_NOT_EXIST;
		}
		// Find the country to be removed.
		Country l_country = l_gameMap.findCountry(p_countryId);
		for (Country l_neighbor : l_country.getNeighbors().values()) {
			l_neighbor.getNeighbors().remove(p_countryId);
		}
		// removed country from continent's country list
		l_gameMap.getContinentById(l_country.getContinentId()).getCountries().remove(l_country);

		// Remove the country from the game map.
		l_gameMap.getCountries().remove(l_country.getCountryId());
		return p_countryId + Constants.MAP_EDITOR_COUNTRY_REMOVED;
	}

	/**
	 * The function removes a continent and all its associated countries from a game
	 * map.
	 *
	 * @param p_continentName
	 *                        The parameter p_continentName is the name of the
	 *                        continent that
	 *                        needs to be removed from the game map.
	 * @return The method is returning a string that indicates the result of
	 *         removing the continent.
	 */
	public String removeContinent(String p_continentName) {
		// Check if the continent exists in the game map.
		if (l_gameMap.containsContinent(p_continentName) == false) {
			return Constants.MAP_EDITOR_CONTINENT_NOT_EXIST;
		}
		// Get the continent object to be removed.
		Continent l_toRemove = l_gameMap.getContinentByName(p_continentName);
		String l_countriesRemoved = "";
		List<Country> l_countriesToRemove = l_gameMap.getCountriesOfContinent(l_toRemove.getContinentId());
		for (Country l_country : l_countriesToRemove) {
			removeCountry(l_country.getCountryId());
			l_countriesRemoved = l_country.getCountryName() + ", ";
		}
		// Remove the continent itself.
		l_gameMap.getContinents().remove(l_toRemove.getContinentId());
		return p_continentName + Constants.MAP_EDITOR_REMOVED + l_countriesRemoved
				+ Constants.MAP_EDITOR_COUNTRIES_REMOVED;
	}

	/**
	 * The function removes a neighbor connection between two countries in a game
	 * map.
	 *
	 * @param p_countryId
	 *                            The ID of the country from which you want to
	 *                            remove a neighbor.
	 * @param p_neighborCountryId
	 *                            The ID of the country that you want to remove as a
	 *                            neighbor from
	 *                            the country with ID p_countryId.
	 * @return The method is returning a string indicating the result of the
	 *         operation.
	 */
	public String removeNeighbor(int p_countryId, int p_neighborCountryId) {
		if (l_gameMap.containsCountry(p_countryId) == false) {
			return Constants.MAP_EDITOR_COUNTRY_NOT_EXIST;
		}
		if (l_gameMap.containsCountry(p_neighborCountryId) == false) {
			return Constants.MAP_EDITOR_NEIGHBOR_COUNTRY_NOT_EXIST;
		}
		// Find the first country to remove the neighbor from.
		Country l_countryToRemove = l_gameMap.findCountry(p_countryId);
		if (l_countryToRemove.hasNeighbor(p_neighborCountryId) == false) {
			return Constants.MAP_EDITOR_CONNECTION_NOT_EXIST;
		}
		// Remove the neighbor relationship from both countries.
		l_gameMap.findCountry(p_countryId).getNeighbors().remove(p_neighborCountryId);
		l_gameMap.findCountry(p_neighborCountryId).getNeighbors().remove(p_countryId);

		return p_countryId + Constants.MAP_EDITOR_NEIGHBOR_REMOVED + p_neighborCountryId;
	}
}
