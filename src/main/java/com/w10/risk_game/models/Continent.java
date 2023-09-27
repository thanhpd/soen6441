package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omnia Alam
 */

public class Continent {
	private int d_continentId;
	private String d_continentName;
	private List<Country> d_countries;
	private List<Country> d_neighborCountries;
	private int d_bonus;

	public Continent(int p_continentId, String p_continentName, int p_bonus) {
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
		this.d_bonus = p_bonus;
		this.d_countries = new ArrayList<>();
		this.d_neighborCountries = new ArrayList<>();
	}

	// Getter
	public int getContinentId() {
		return d_continentId;
	}

	// Setter
	public void setContinentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}

	public String getContinentName() {
		return d_continentName;
	}

	public List<Country> getCountries() {
		return d_countries;
	}

	public void addCountry(Country d_country) {
		this.d_countries.add(d_country);
	}

	public List<Country> getNeighborCountries() {
		return d_neighborCountries;
	}

	public void setNeighborCountries(List<Country> d_neighborCountries) {
		this.d_neighborCountries = d_neighborCountries;
	}

	// Setter
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	// Getter
	public int getBonus() {
		return d_bonus;
	}

	// Setter
	public void setBonus(int p_bouns) {
		this.d_bonus = p_bouns;
	}

	public String toString() {
		return d_continentId + " " + d_continentName + " " + d_bonus;
	}
}
