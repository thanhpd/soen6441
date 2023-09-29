package com.w10.risk_game.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapValidator;

/**
 * @author Omnia Alam
 */

public class GameMap {
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;
	private Map<Integer, Player> d_player;

	public GameMap() {
		this.d_countries = new HashMap<>();
		this.d_continents = new HashMap<>();
		this.d_player = new HashMap<>();
	}

	public boolean isMapCreated() {
		return this.d_continents.size() != 0 && this.d_countries.size() != 0;
	}

	// Getter
	public Map<Integer, Country> getCountries() {
		return d_countries;
	}

	public Country findCountry(int p_countryId) {
		return d_countries.get(p_countryId);
	}

	public Player getPlayerbyCountry(int p_countryId) {
		return d_player.get(p_countryId);
	}

	public Country getCountryByName(String p_countryNmae) {
		return d_countries.values().stream().filter(counrty -> counrty.getCountryName().equals(p_countryNmae))
				.findFirst().get();
	}

	public Continent getContinentByName(String p_continentName) {
		return d_continents.values().stream().filter(counrty -> counrty.getContinentName().equals(p_continentName))
				.findFirst().get();
	}

	public Map<Integer, Continent> getContinents() {
		return d_continents;
	}

	public boolean containsCountry(int p_countryId) {
		return d_countries.containsKey(p_countryId);
	}

	/*
	 * Method overloading- polymorphism
	 */
	public boolean containsCountry(String p_countryName) {
		return d_countries.values().stream().filter(counrty -> counrty.getCountryName().equals(p_countryName))
				.findFirst().isPresent();
	}

	public boolean containsContinent(int p_id) {
		return d_continents.containsKey(p_id);
	}

	/*
	 * Method overloading- polymorphism
	 *
	 */
	public boolean containsContinent(String p_continentName) {
		return d_continents.values().stream().filter(continent -> continent.getContinentName().equals(p_continentName))
				.findFirst().isPresent();
	}

	public Continent getContinentById(int p_continentId) {
		return d_continents.get(p_continentId);
	}

	public ArrayList<Country> getCountriesOfContinent(int p_continentId) {
		return d_countries.values().stream().filter(country -> country.getContinentId() == p_continentId)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public void addCountries(Map<Integer, Country> p_countries) {
		this.d_countries.putAll(p_countries);
	}

	public void addContinents(Map<Integer, Continent> p_continents) {
		this.d_continents.putAll(p_continents);
	}

	public void saveMap(String p_fileName) {
		if (MapValidator.isMapCorrect(this))
			try (FileWriter l_fileWriter = new FileWriter(Constants.GAME_MAP_FOLDER_PATH + p_fileName)) {
				PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
				l_printWriter.println("[continents]");
				for (Continent continent : this.d_continents.values()) {
					l_printWriter.format("%s %d%n", continent.getContinentName(), continent.getCountries().size());
				}
				l_printWriter.println("\n[countries]");
				for (Country country : this.d_countries.values()) {
					l_printWriter.format("%d %s %d%n", country.getCountryId(), country.getCountryName(),
							country.getContinentId());
				}
				l_printWriter.println("\n[borders]");
				for (Country country : this.d_countries.values()) {
					l_printWriter.format("%d %s%n", country.getCountryId(), country.getNeighbors().keySet());
				}
				l_printWriter.close();
			} catch (IOException e) {
				System.out.format("Error - Unable to save file. Please try again.%n%s", e.getMessage());
			}
	}
}
