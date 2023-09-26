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
	ArrayList<Country> countries;
	ArrayList<Continent> continents;

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

	public void saveMap(String p_fileName) {
		try (FileWriter l_fileWriter = new FileWriter(p_fileName)) {
			PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
			l_printWriter.println("[continents]");
			for (Continent continent : continents) {
				l_printWriter.format("%s %d%n", continent.getContinentName(), continent.getCountries().size());
			}
			l_printWriter.println("[countries]");
			for (Country country : countries) {
				l_printWriter.format("%d %s %d%n", country.getCountryId(), country.getCountryName(), country.getContinentId());
			}
			l_printWriter.close();
		} catch (IOException e) {
			System.out.format("Error occurred. Unable to save file. Please try again. %s", e.getMessage());
		}
	}
}
