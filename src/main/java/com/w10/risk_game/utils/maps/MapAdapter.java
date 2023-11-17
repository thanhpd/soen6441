package com.w10.risk_game.utils.maps;

import com.w10.risk_game.models.ConquestGameMap;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import java.util.HashMap;
import java.util.Map;

/**
 * The MapAdapter class is a Java class that acts as an adapter between the
 * DominationMapDriver and ConquestMapDriver classes, allowing for the
 * translation of game maps between the two formats.
 */
public class MapAdapter extends DominationMapDriver {
	private final ConquestMapDriver d_conquestMapReader;

	/**
	 * The constructor for the MapAdapter class.
	 *
	 * @param p_conquestMapReader
	 *            The parameter p_conquestMapReader is a ConquestMapDriver object.
	 */
	public MapAdapter(ConquestMapDriver p_conquestMapReader) {
		d_conquestMapReader = p_conquestMapReader;
	}

	/**
	 * The function loads a map file and translates it into a Domination game map.
	 *
	 * @param p_mapFilePath
	 *            The parameter "p_mapFilePath" is a string that represents the file
	 *            path of the map file that needs to be loaded.
	 * @return The method is returning a GameMap object.
	 */
	public GameMap loadMapFile(String p_mapFilePath) {
		return translateToDominationGameMap(d_conquestMapReader.loadMapFile(p_mapFilePath));
	}

	@Override
	public void saveMap(String p_mapFilePath, GameMap p_gameMap) {
		d_conquestMapReader.saveMap(p_mapFilePath, translateToConquestGameMap(p_gameMap));
	}

	/**
	 * The function translates a ConquestGameMap object into a GameMap object by
	 * mapping continents and countries.
	 *
	 * @param p_conquestGameMap
	 *            The parameter p_conquestGameMap is an object of type
	 *            ConquestGameMap, which represents a game map in the Conquest
	 *            format.
	 * @return The method is returning a GameMap object.
	 */
	private GameMap translateToDominationGameMap(ConquestGameMap p_conquestGameMap) {
		GameMap l_gameMap = new GameMap();
		Map<Integer, Country> l_countries = new HashMap<>();
		Map<Integer, Continent> l_continents = new HashMap<>();

		// Translate continents and countries from ConquestGameMap to GameMap
		for (Continent continent : p_conquestGameMap.getContinents().values()) {
			l_continents.put(continent.getContinentId(), continent);
		}

		for (Country country : p_conquestGameMap.getCountries().values()) {
			l_countries.put(country.getCountryId(), country);
		}

		// Add countries and continents to GameMap
		l_gameMap.addCountries(l_countries);
		l_gameMap.addContinents(l_continents);

		return l_gameMap;
	}

	/**
	 * The function translates a GameMap object to a ConquestGameMap object by
	 * creating maps of continents and countries and adding them to the
	 * ConquestGameMap.
	 *
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is of type `GameMap`, which represents a
	 *            game map in a certain format.
	 * @return The method is returning a ConquestGameMap object.
	 */
	private ConquestGameMap translateToConquestGameMap(GameMap p_gameMap) {
		ConquestGameMap l_gameMap = new ConquestGameMap();
		Map<String, Country> l_countries = new HashMap<>();
		Map<String, Continent> l_continents = new HashMap<>();

		// Translate continents and countries from GameMap to ConquestGameMap
		for (Continent continent : p_gameMap.getContinents().values()) {
			l_continents.put(continent.getContinentName(), continent);
		}

		for (Country country : p_gameMap.getCountries().values()) {
			l_countries.put(country.getCountryName(), country);
		}

		// Add countries and continents to ConquestGameMap
		l_gameMap.addCountries(l_countries);
		l_gameMap.addContinents(l_continents);

		return l_gameMap;
	}
}
