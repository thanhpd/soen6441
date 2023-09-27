package com.w10.risk_game.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.w10.risk_game.utils.Constants;

/**
 * @author Omnia Alam
 */

public class GameMap {
	private Map<Integer, Country> l_countries;
	private Map<Integer, Continent> l_continents;
	private Map<Integer, Player> l_player;

	public GameMap() {
		this.l_countries = new HashMap<>();
		this.l_continents = new HashMap<>();
		this.l_player = new HashMap<>();
	}

	public boolean isMapCreated() {
		return this.l_continents.size() != 0 || this.l_countries.size() != 0;
	}

	// Getter
	public Map<Integer, Country> getCountries() {
		return l_countries;
	}

	public Country findCountry(int p_countryId) {
		return l_countries.get(p_countryId);
	}

	public Player getPlayerbyCountry(int p_countryId) {
		return l_player.get(p_countryId);
	}

	public Country getCountryByName(String p_countryNmae) {
		return l_countries.values().stream().filter(counrty -> counrty.getCountryName().equals(p_countryNmae))
				.findFirst().get();
	}

	public Continent getContinentByName(String p_continentName) {
		return l_continents.values().stream().filter(counrty -> counrty.getContinentName().equals(p_continentName))
				.findFirst().get();
	}

	public Map<Integer, Continent> getContinents() {
		return l_continents;
	}

	public boolean containsCountry(int p_countryId) {
		return l_countries.containsKey(p_countryId);
	}

	/*
	 * Method overloading- polymorphism
	 */
	public boolean containsCountry(String p_countryName) {
		return l_countries.values().stream().filter(counrty -> counrty.getCountryName().equals(p_countryName))
				.findFirst().isPresent();
	}

	public boolean containsContinent(int p_id) {
		return l_continents.containsKey(p_id);
	}

	/*
	 * Method overloading- polymorphism
	 *
	 */
	public boolean containsContinent(String p_continentName) {
		return l_continents.values().stream().filter(continent -> continent.getContinentName().equals(p_continentName))
				.findFirst().isPresent();
	}

	public Continent getContinentById(int p_continentId) {
		return l_continents.get(p_continentId);
	}

	public ArrayList<Country> getCountriesOfContinent(int p_continentId) {
		return l_countries.values().stream().filter(country -> country.getContinentId() == p_continentId)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public void addCountries(Map<Integer, Country> p_countries) {
		this.l_countries.putAll(p_countries);
	}

	public void addContinentes(Map<Integer, Continent> p_continents) {
		this.l_continents.putAll(p_continents);
	}

	public void saveMap(String p_fileName) {
		try (FileWriter l_fileWriter = new FileWriter(Constants.GAME_MAP_FOLDER_PATH + p_fileName)) {
			PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
			l_printWriter.println("[continents]");
			for (Continent continent : l_continents.values()) {
				l_printWriter.format("%s %d%n", continent.getContinentName(), continent.getCountries().size());
			}
			l_printWriter.println("\n[countries]");
			for (Country country : l_countries.values()) {
				l_printWriter.format("%d %s %d%n", country.getCountryId(), country.getCountryName(),
						country.getContinentId());
			}
			l_printWriter.println("\n[borders]");
			for (Country country : l_countries.values()) {
				l_printWriter.format("%d %s%n", country.getCountryId(), country.getNeighbors().keySet());
			}
			l_printWriter.close();
		} catch (IOException e) {
			System.out.format("Error - Unable to save file. Please try again. %s", e.getMessage());
		}
	}
}
