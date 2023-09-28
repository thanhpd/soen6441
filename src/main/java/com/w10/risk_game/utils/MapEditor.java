package com.w10.risk_game.utils;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam This class operates on the current Game map It adds and
 *         removes Gamemap elements
 * @returns an updated gamemap after edit performs
 */

public class MapEditor {
	private GameMap l_gameMap;

	public MapEditor(GameMap originalMap) {
		this.l_gameMap = originalMap;
	}

	/**
	 *
	 * @param p_countryId
	 * @param p_countryName
	 * @param p_continentName
	 * @return string based on operation performed add operation on the current
	 *         gamemap
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
			return "Continent doesnot exists!";
		}
		Continent l_continent = l_gameMap.getContinentById(p_continentId);
		Country l_country = new Country(p_countryId, p_countryName, l_continent.getContinentId(), 0);
		l_gameMap.getCountries().put(p_countryId, l_country);
		return p_countryName + " is Added! and to the continent with id: " + p_continentId;
	}

	/**
	 *
	 * @param p_continentId
	 * @param p_continentName
	 * @return string based on operation performed add operation on the current
	 *         gamemap
	 */
	public String addContinent(String p_continentName,int p_bonus) {
		if (l_gameMap.containsContinent(p_continentName)) {
			return "Continent name already exists!";
		}
		int l_newContinetid= l_gameMap.getContinents().size()+1;
		Continent l_continent = new Continent(l_newContinetid, p_continentName, p_bonus);
		l_gameMap.getContinents().put(l_gameMap.getContinents().size() + 1, l_continent);

		return p_continentName + " is added!";
	}

	/**
	 *
	 * @param p_countryId
	 * @param p_neighbourCountryId
	 * @return string based on operation performed addes neighbor to the game map
	 */
	public String addNeighbor(int p_countryId, int p_neighbourCountryId) {
		if (l_gameMap.containsCountry(p_countryId) == false) {
			return "Country does not exist! please add first";
		}
		if (l_gameMap.containsCountry(p_neighbourCountryId) == false) {
			return "Neighbor country does not exist!please add first";
		}
		Country l_countryToAdd = l_gameMap.findCountry(p_countryId);
		if (l_countryToAdd.hasNeighbor(p_neighbourCountryId)) {
			return "Connection already exists!";
		}
		Country l_negibor = l_gameMap.findCountry(p_neighbourCountryId);
		Country l_country = l_gameMap.findCountry(p_countryId);
		l_country.addNeighbor(l_negibor);
		l_negibor.addNeighbor(l_country);

		return p_countryId + " added with " + p_neighbourCountryId;
	}

	/**
	 *
	 * @param p_countryId
	 * @return string based on operation performed first remove from the neighbors
	 *         then self removes
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
	 *            The parameter p_continentId is the ID of the continent that needs
	 *            to be removed from the game map.
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
	 *
	 * @param p_countryId
	 * @param p_neighbourCountryId
	 * @return string based on operation performed Looks for the continent elements
	 *         if they exist Remove operation on the current Game map if exists then
	 *         get all the adjacent countries and remove the countries before it
	 *         removes the continent
	 */
	public String removeNeighbour(int p_countryId, int p_neighbourCountryId) {
		if (l_gameMap.containsCountry(p_countryId) == false) {
			return "Country does not exist! please add first";
		}
		if (l_gameMap.containsCountry(p_neighbourCountryId) == false) {
			return "Neighbor country does not exist!please add first";
		}
		Country l_countryToRemove = l_gameMap.findCountry(p_countryId);
		if (l_countryToRemove.hasNeighbor(p_neighbourCountryId) == false) {
			return "Connection doesnot exists!";
		}
		l_gameMap.findCountry(p_countryId).getNeighbors().remove(p_neighbourCountryId);
		l_gameMap.findCountry(p_neighbourCountryId).getNeighbors().remove(p_countryId);

		return p_countryId + " added with " + p_neighbourCountryId;
	}
}
