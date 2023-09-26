package com.w10.risk_game.models;

import java.util.List;

public class Continent {
	private int d_continentId;
	private String d_continentName;
	private int d_armyCount;
	private List<Country> d_countries;
	private List<Country> d_neighborCountries;

	public Continent() {
	}

	public Continent(int p_continentId, String p_continentName, int p_armyCount) {
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
		this.d_armyCount = p_armyCount;
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

	public void setCountries(List<Country> d_countries) {
		this.d_countries = d_countries;
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
