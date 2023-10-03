package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Omnia Alam The Country class represents a country in a game, with
 *         properties such as country ID, name, continent ID, army count, owner,
 *         and a list of neighboring countries.
 */
public class Country implements Serializable {
	private int d_countryId;
	private String d_countryName;
	private int d_continentId;
	private int d_armyCount;
	private Player d_owner;

	private Map<Integer, Country> d_neighbors;

	/**
	 * This is a constructor of the country class
	 *
	 * @param p_countryId
	 * @param p_countryName
	 * @param p_continentId
	 * @param p_armyCount
	 */
	public Country(int p_countryId, String p_countryName, int p_continentId, int p_armyCount) {
		this.d_countryId = p_countryId;
		this.d_countryName = p_countryName;
		this.d_continentId = p_continentId;
		this.d_armyCount = p_armyCount;
		this.d_neighbors = new HashMap<>();
	}

	/**
	 * @return d_countryId
	 */
	public int getCountryId() {
		return d_countryId;
	}

	/**
	 * @param p_countryId
	 */
	public void setCountryId(int p_countryId) {
		this.d_countryId = p_countryId;
	}

	/**
	 * @return d_countryName
	 */
	public String getCountryName() {
		return d_countryName;
	}

	/**
	 * @param p_countryName
	 */
	public void setCountryName(String p_countryName) {
		this.d_countryName = p_countryName;
	}

	/**
	 * @return d_continentId
	 */
	public int getContinentId() {
		return d_continentId;
	}

	/**
	 * @param p_continentId
	 */
	public void setContinentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}

	/**
	 * @return d_armyCount
	 */
	public int getArmyCount() {
		return d_armyCount;
	}

	/**
	 * @param d_armyCount
	 */
	public void setArmyCount(int d_armyCount) {
		this.d_armyCount = d_armyCount;
	}
	/**
	 * Overriding the to string methods to custom print
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
	 * @param p_neighborCountryId
	 * @return boolean
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
	 * API: Checks for different parameters if the neighbor exists
	 */

	public boolean hasNeighbor(Country p_country) {
		return d_neighbors.containsKey(p_country.getCountryId());
	}
	/**
	 * addNeighbor function adds neighbor to the country
	 */
	public void addNeighbor(Country p_neighborCountry) {
		d_neighbors.put(p_neighborCountry.d_countryId, p_neighborCountry);
	}

	/**
	 * @return d_neighbors
	 */
	public Map<Integer, Country> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * @return Player
	 */
	public Player getOwner() {
		return this.d_owner;
	}

	/**
	 * @param p_owner
	 */
	public void setOwner(Player p_owner) {
		this.d_owner = p_owner;
	}
}
