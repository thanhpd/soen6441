package com.w10.risk_game.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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

	public Map<Integer, Country> getCountries() {
		return d_countries;
	}

	public Country findCountry(int p_countryId) {
		return d_countries.get(p_countryId);
	}

	public Player getPlayerByCountry(int p_countryId) {
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
		return d_countries.values().stream().anyMatch(l_country -> l_country.getCountryName().equals(p_countryName));
	}

	public boolean containsContinent(int p_id) {
		return d_continents.containsKey(p_id);
	}

	/*
	 * Method overloading- polymorphism
	 *
	 */
	public boolean containsContinent(String p_continentName) {
		return d_continents.values().stream()
				.anyMatch(l_continent -> l_continent.getContinentName().equals(p_continentName));
	}

	public Continent getContinentById(int p_continentId) {
		return d_continents.get(p_continentId);
	}

	public List<Country> getCountriesOfContinent(int p_continentId) {
		return d_countries.values().stream().filter(country -> country.getContinentId() == p_continentId)
				.collect(Collectors.toList());
	}

	public void addCountries(Map<Integer, Country> p_countries) {
		this.d_countries.putAll(p_countries);
	}

	public void addContinents(Map<Integer, Continent> p_continents) {
		this.d_continents.putAll(p_continents);
	}

	/**
	 * The saveMap function saves the game map to a file in a specific format.
	 *
	 * @param p_filePath
	 *            The file path where the map will be saved.
	 */
	public void saveMap(String p_filePath) {
		if (MapValidator.IsMapCorrect(this))
			try (FileWriter l_fileWriter = new FileWriter(p_filePath)) {
				PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
				l_printWriter.println(Constants.MAP_READER_MAP + Constants.NEW_LINE + Constants.MAP_READER_CONTINENTS);
				for (Continent continent : this.d_continents.values()) {
					l_printWriter.format("%s %d%n", continent.getContinentName(), continent.getCountries().size());
				}
				l_printWriter.println(Constants.NEW_LINE + Constants.MAP_READER_COUNTRIES);
				for (Country country : this.d_countries.values()) {
					l_printWriter.format("%d %s %d%n", country.getCountryId(), country.getCountryName(),
							country.getContinentId());
				}
				l_printWriter.println(Constants.NEW_LINE + Constants.MAP_READER_BORDERS);
				for (Country country : this.d_countries.values()) {
					l_printWriter.format("%d %s%n", country.getCountryId(), country.getNeighbors().keySet().stream()
							.map(Object::toString).collect(Collectors.joining(Constants.SPACE)));
				}
				l_printWriter.close();
			} catch (IOException e) {
				System.out.format(Constants.MAP_SAVE_ERROR);
			}
	}
}
