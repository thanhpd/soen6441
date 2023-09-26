package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.List;

public class Continent {
	private int d_continentId;
	private String d_continentName;
	private int d_armyCount;
	private List<Country> d_countries;
	private List<Country> d_neighborCountries;

	public Continent(int p_continentId, String p_continentName, int p_armyCount) {
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
		this.d_armyCount = p_armyCount;
		this.d_countries = new ArrayList<>();
		this.d_neighborCountries = new ArrayList<>();
	}

	public int getContinentId() {
		return d_continentId;
	}

	public void setContinentId(int d_continentId) {
		this.d_continentId = d_continentId;
	}

	public String getContinentName() {
		return d_continentName;
	}

	public void setContinentName(String d_continentName) {
		this.d_continentName = d_continentName;
	}

	public int getArmyCount() {
		return d_armyCount;
	}

	public void setArmyCount(int d_armyCount) {
		this.d_armyCount = d_armyCount;
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

	public String toString() {
		return d_continentId + " " + d_continentName;
	}
}
