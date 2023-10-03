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
	 * @return d_continentName
	 */
	public String getContinentName() {
		return d_continentName;
	}

	/**
	 * @return d_countries
	 */
	public List<Country> getCountries() {
		return d_countries;
	}

	/**
	 * @param d_country
	 */
	public void addCountry(Country d_country) {
		this.d_countries.add(d_country);
	}

	/**
	 * @return d_neighborCountries
	 */
	public List<Country> getNeighborCountries() {
		return d_neighborCountries;
	}

	/**
	 * @param d_neighborCountries
	 */
	public void setNeighborCountries(List<Country> d_neighborCountries) {
		this.d_neighborCountries = d_neighborCountries;
	}

	/**
	 * @param p_continentName
	 */
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	/**
	 * @return d_bonus
	 */
	public int getBonus() {
		return d_bonus;
	}

	/**
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
