package com.w10.risk_game.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.w10.risk_game.utils.Constants;

public class GameMap {
	private Map<Integer, Country> countries = new HashMap<Integer, Country>();
	private Map<Integer, Continent> continents = new HashMap<Integer, Continent>();

	// Getter
	public Map<Integer, Country> getCountries() {
		return countries;
	}

	public Map<Integer, Continent> getContinents() {
		return continents;
	}

	public void addCountries(Map<Integer, Country> countries) {
		this.countries.putAll(countries);
	}

	public void addContinentes(Map<Integer, Continent> continents) {
		this.continents.putAll(continents);
	}

	public void saveMap(String p_fileName) {
		try (FileWriter l_fileWriter = new FileWriter(Constants.GAME_MAP_FOLDER_PATH + p_fileName)) {
			PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
			l_printWriter.println("[continents]");
			for (Continent continent : continents.values()) {
				l_printWriter.format("%s %d%n", continent.getContinentName(), continent.getCountries().size());
			}
			l_printWriter.println("\n[countries]");
			for (Country country : countries.values()) {
				l_printWriter.format("%d %s %d%n", country.getCountryId(), country.getCountryName(),
						country.getContinentId());
			}
			l_printWriter.close();
		} catch (IOException e) {
			System.out.format("Error - Unable to save file. Please try again. %s", e.getMessage());
		}
	}
}
