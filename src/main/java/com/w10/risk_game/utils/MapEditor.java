package com.w10.risk_game.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.w10.risk_game.exceptions.ApplicationException;
import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

public class MapEditor {
	private GameMap originalMap;
	private ArrayList<Country> countries;
	private ArrayList<Continent> continents;

	public MapEditor(GameMap originalMap) {
		this.originalMap = originalMap;
		countries = new ArrayList<>(originalMap.getCountries().values());
		continents = new ArrayList<>(originalMap.getContinents().values());
	}

	public void addCounrty(int countryId, String countryName, int continentId) {
		Country country = new Country(countryId, countryName, continentId);
		countries.add(country);
	}

	public void addContinent(int continentId, String continentName) {
		Continent continent = new Continent(continentId, continentName, 0);
		continents.add(continent);
	}

	public void addNeighbor(int countryId, int neighbourCountryId) {

	}

	public void removeCountry(int countryId) {
		countries.removeIf(country -> countryId == country.getCountryId());
	}

	public void removeContinent(int continentId) {
		continents.removeIf(continent -> continentId == continent.getContinentId());
	}

	public void removeNeighbour(int countryId, int neighbourCountryId) {

	}

	public void validateMap() {

	}

}
