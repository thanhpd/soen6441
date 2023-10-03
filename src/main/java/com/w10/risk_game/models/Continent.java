package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omnia Alam The Continent is a model class The Continent class
 *         represents a continent in a game, with properties such as continent
 *         ID, name, list of countries, list of neighboring countries, and bonus
 *         points.
 */
public class Continent {
	private int d_continentId;
	private String d_continentName;
	private List<Country> d_countries;
	private List<Country> d_neighborCountries;
	private int d_bonus;
	/**
	 * This is a constructor of the Continent class
	 *
	 * @param p_continentId
	 * @param p_continentName
	 * @param p_bonus
	 */
	public Continent(int p_continentId, String p_continentName, int p_bonus) {
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
		this.d_bonus = p_bonus;
		this.d_countries = new ArrayList<>();
		this.d_neighborCountries = new ArrayList<>();
	}

	/**
	 * getContinentId function returns the id of a continent
	 *
	 * @return continent id
	 */
	public int getContinentId() {
		return d_continentId;
	}

	/**
	 * setContinentId function assigns the id of the continent
	 *
	 * @param continent
	 *            id
	 */
	public void setContinentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}

	/**
	 * Getter function to get the continent name
	 *
	 * @return continent name
	 */
	public String getContinentName() {
		return d_continentName;
	}

	/**
	 * Getter functions to get the list of countries of a continent
	 *
	 * @return countries
	 */
	public List<Country> getCountries() {
		return d_countries;
	}

	/**
	 * This function is used to add a country to a continent
	 *
	 * @param country
	 */
	public void addCountry(Country d_country) {
		this.d_countries.add(d_country);
	}

	/**
	 * Getter function for the list of countries for the continent
	 *
	 * @return list of neighbor
	 */
	public List<Country> getNeighborCountries() {
		return d_neighborCountries;
	}

	/**
	 * Setter function for the neighboring country This function sets the countries
	 * for the continent
	 *
	 * @param d_neighborCountries
	 */
	public void setNeighborCountries(List<Country> d_neighborCountries) {
		this.d_neighborCountries = d_neighborCountries;
	}

	/**
	 * Setter function for Continent name
	 *
	 * @param p_continentName
	 */
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	/**
	 * Getter function for bonus
	 *
	 * @return bonus
	 */
	public int getBonus() {
		return d_bonus;
	}

	/**
	 * Setter function for bonus
	 *
	 * @param p_bonus
	 */
	public void setBonus(int p_bonus) {
		this.d_bonus = p_bonus;
	}
	/**
	 * Overriding the to String method for custom printing
	 */
	public String toString() {
		return d_continentId + " " + d_continentName + " " + d_bonus;
	}
}
