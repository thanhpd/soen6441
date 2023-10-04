package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Continent is a model class The Continent class represents a continent in
 * a game, with properties such as continent ID, name, list of countries, list
 * of neighboring countries, and bonus points.
 *
 * @author Omnia Alam, Darlene-Naz
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
	 * The function returns the name of a continent.
	 *
	 * @return The method is returning a String value, specifically the value of the
	 *         variable "d_continentName".
	 */
	public String getContinentName() {
		return d_continentName;
	}

	/**
	 * The getCountries() function returns a list of Country's in this Continent.
	 *
	 * @return A List of Country objects is being returned.
	 */
	public List<Country> getCountries() {
		return d_countries;
	}

	/**
	 * The addCountry function adds a Country to a list of countries in this
	 * Continent
	 *
	 * @param d_country
	 *            The parameter "d_country" is an object of the class "Country".
	 */
	public void addCountry(Country d_country) {
		this.d_countries.add(d_country);
	}

	/**
	 * The function returns a list of neighboring countries.
	 *
	 * @return A List of Country objects is being returned.
	 */
	public List<Country> getNeighborCountries() {
		return d_neighborCountries;
	}

	/**
	 * The function sets the list of neighboring countries for a given country.
	 *
	 * @param d_neighborCountries
	 *            The parameter "d_neighborCountries" is a List of Country objects.
	 *            It is used to set the neighboring countries of a particular
	 *            country.
	 */
	public void setNeighborCountries(List<Country> d_neighborCountries) {
		this.d_neighborCountries = d_neighborCountries;
	}

	/**
	 * The function sets the name of a continent.
	 *
	 * @param p_continentName
	 *            The parameter p_continentName is a String that represents the name
	 *            of a continent.
	 */
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	/**
	 * The function returns the value of the variable d_bonus armies for owning all
	 * countries of this Continent.
	 *
	 * @return The method is returning the value of the variable "d_bonus".
	 */
	public int getBonus() {
		return d_bonus;
	}

	/**
	 * The function sets the value of the bonus armies for owning all countries of
	 * this Continent.
	 *
	 * @param p_bonus
	 *            The parameter "p_bonus" is an integer value that represents the
	 *            bonus amount.
	 */
	public void setBonus(int p_bonus) {
		this.d_bonus = p_bonus;
	}

	/**
	 * The toString() function returns a string representation of the continent's
	 * ID, name, and bonus.
	 *
	 * @return The toString() method is returning a string representation of the
	 *         object. It concatenates the values of the d_continentId,
	 *         d_continentName, and d_bonus variables, separated by spaces.
	 */
	public String toString() {
		return d_continentId + " " + d_continentName + " " + d_bonus;
	}
}
