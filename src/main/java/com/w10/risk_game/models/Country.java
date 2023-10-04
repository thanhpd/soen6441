package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The `Country` class represents a country in the game, with properties such as
 * country ID, name, continent ID, army count, owner, and neighboring countries.
 *
 * @author Omnia Alam, Darlene-Naz
 */
public class Country implements Serializable {
	private int d_countryId;
	private String d_countryName;
	private int d_continentId;
	private int d_armyCount;
	private Player d_owner;
	private Map<Integer, Country> d_neighbors;

	/**
	 * This constructor is initializing a new instance of the `Country` class with
	 * the provided parameters.
	 *
	 * @param p_countryId
	 *            The Country's unique identifier
	 * @param p_countryName
	 *            The Country's name
	 * @param p_continentId
	 *            The Continent's ID in which this Country lies
	 * @param p_armyCount
	 *            The number of armies deployed on this Country
	 */
	public Country(int p_countryId, String p_countryName, int p_continentId, int p_armyCount) {
		this.d_countryId = p_countryId;
		this.d_countryName = p_countryName;
		this.d_continentId = p_continentId;
		this.d_armyCount = p_armyCount;
		this.d_neighbors = new HashMap<>();
	}

	/**
	 * The function returns the country ID.
	 *
	 * @return The method is returning an integer value, specifically the value of
	 *         the variable "d_countryId".
	 */
	public int getCountryId() {
		return d_countryId;
	}

	/**
	 * The function sets the country ID to the given value.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer that represents the
	 *            country ID.
	 */
	public void setCountryId(int p_countryId) {
		this.d_countryId = p_countryId;
	}

	/**
	 * The function returns the name of a country.
	 *
	 * @return The method is returning a String value, specifically the value of the
	 *         variable "d_countryName".
	 */
	public String getCountryName() {
		return d_countryName;
	}

	/**
	 * The function sets the country name to the provided value.
	 *
	 * @param p_countryName
	 *            The parameter p_countryName is a String that represents the name
	 *            of a country.
	 */
	public void setCountryName(String p_countryName) {
		this.d_countryName = p_countryName;
	}

	/**
	 * The function returns the continent ID.
	 *
	 * @return The method is returning an integer value, specifically the value of
	 *         the variable "d_continentId".
	 */
	public int getContinentId() {
		return d_continentId;
	}

	/**
	 * The function sets the continent ID for an object.
	 *
	 * @param p_continentId
	 *            The parameter p_continentId is an integer that represents the ID
	 *            of a continent.
	 */
	public void setContinentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}

	/**
	 * The function returns the count of armies".
	 *
	 * @return The method is returning the value of the variable "d_armyCount".
	 */
	public int getArmyCount() {
		return d_armyCount;
	}

	/**
	 * The function sets the value of the variable d_armyCount.
	 *
	 * @param d_armyCount
	 *            The parameter "d_armyCount" is an integer that represents the
	 *            number of armies in a game.
	 */
	public void setArmyCount(int d_armyCount) {
		this.d_armyCount = d_armyCount;
	}

	/**
	 * The toString() function returns a string representation of a Country object,
	 * including its country ID, name, continent ID, and the IDs of its neighboring
	 * countries.
	 *
	 * @return The method is returning a string representation of the object.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(d_countryId + " " + d_countryName + " " + d_continentId);

		for (Country country : d_neighbors.values()) {
			builder.append(" ");
			builder.append(country.d_countryId);
		}
		return builder.toString();
	}

	/**
	 * The function checks if a given country has a neighbor with a specific country
	 * ID.
	 *
	 * @param p_neighborCountryId
	 *            The parameter p_neighborCountryId is an integer that represents
	 *            the ID of a neighboring country.
	 * @return A boolean value is being returned.
	 */
	public boolean hasNeighbor(int p_neighborCountryId) {
		return d_neighbors.containsKey(p_neighborCountryId);
	}

	/**
	 * The function checks if a given country name is a neighbor of the current
	 * country.
	 *
	 * @param p_neighborCountryName
	 *            The parameter p_neighborCountryName is a String that represents
	 *            the name of a neighbor country.
	 * @return The method is returning a boolean value. It returns true if the given
	 *         neighbor country name is found in the list of neighbor countries, and
	 *         false otherwise.
	 */
	public boolean hasNeighbor(String p_neighborCountryName) {
		for (var neighborCountry : d_neighbors.values()) {
			if (neighborCountry.getCountryName().equals(p_neighborCountryName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The function checks if a given country has a neighbor.
	 *
	 * @param p_country
	 *            The parameter "p_country" is an object of the class "Country".
	 * @return The method is returning a boolean value. It returns true if the given
	 *         country has a neighbor and false otherwise.
	 */
	public boolean hasNeighbor(Country p_country) {
		return d_neighbors.containsKey(p_country.getCountryId());
	}

	/**
	 * The addNeighbor function adds a neighbor country to the current country's
	 * list of neighbors.
	 *
	 * @param p_neighborCountry
	 *            The parameter "p_neighborCountry" is of type "Country" and
	 *            represents the neighbor country that we want to add to the current
	 *            country's list of neighbors.
	 */
	public void addNeighbor(Country p_neighborCountry) {
		d_neighbors.put(p_neighborCountry.d_countryId, p_neighborCountry);
	}

	/**
	 * The function returns a map of integers to Country objects representing the
	 * neighbors of a country.
	 *
	 * @return A map of integers and Country objects is being returned.
	 */
	public Map<Integer, Country> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * The function returns the owner of the Country.
	 *
	 * @return The method is returning the value of the variable "d_owner", which is
	 *         of type "Player".
	 */
	public Player getOwner() {
		return this.d_owner;
	}

	/**
	 * The function sets a specified player of the Country as an owner
	 *
	 * @param p_owner
	 *            The parameter "p_owner" is a Player who owns this Country
	 */
	public void setOwner(Player p_owner) {
		this.d_owner = p_owner;
	}
}
