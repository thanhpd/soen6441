package com.w10.risk_game.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/*
* This class reads a map file and initialize into the Country and Continents.
* This is used to populate the Gamemap.

 * @author Omnia Alam
 */
public class MapReader {
	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * The function reads country data from a scanner and maps it to a collection of
	 * countries, associating each country with its corresponding continent.
	 *
	 * @param p_scanner
	 *            The scanner object used to read the input file line by line.
	 * @param p_continents
	 *            p_continents is a map that contains the continents of the world.
	 *            The key is an Integer representing the continent ID, and the value
	 *            is a Continent object representing the continent.
	 * @return The method is returning a object containing continentID as key and
	 *         Continent object as value.
	 */
	public Map<Integer, Country> readCountries(Scanner p_scanner, Map<Integer, Continent> p_continents) {

		String l_line;
		HashMap<Integer, Country> l_countries = new HashMap<>();
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();
			// If the line indicates the start of continents, borders, or is empty, stop
			// reading.
			if (l_line.equals(Constants.MAP_READER_CONTINENTS) || l_line.equals(Constants.MAP_READER_BORDERS)
					|| l_line.isEmpty()) {
				break;
			}
			// Parse the line to create a Country object and add it to the map of countries.
			Country l_country = mapCountry(l_line);
			l_countries.put(l_country.getCountryId(), l_country);

			// Associate the country with its continent if applicable.
			Continent p_continent = p_continents.get(l_country.getContinentId());
			if (p_continent != null) {
				p_continent.addCountry(l_country);
			}
		}
		return l_countries;
	}

	/**
	 * The function parses a file containing country borders and adds the
	 * neighboring countries to each country object in a map.
	 *
	 * @param p_countries
	 *            p_countries is a map object which stores the countries in the
	 *            game. The keys are integers representing the country IDs, and the
	 *            values are Country objects representing the countries themselves.
	 * @param p_scanner
	 *            A Scanner object used to read input from a file or other source.
	 */
	public void parseBorders(Map<Integer, Country> p_countries, Scanner p_scanner) {
		String l_line;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();
			if (l_line.equals(Constants.MAP_READER_CONTINENTS) || l_line.equals(Constants.MAP_READER_COUNTRIES)
					|| l_line.isEmpty()) {
				break;
			}

			// Split the line into individual elements based on spaces.
			String[] l_splitted = l_line.split(Constants.SPACE);
			Country d_country = p_countries.get(Integer.parseInt(l_splitted[0]));
			// Iterate through the elements in the line, which represent neighbor country
			// IDs.
			for (int i = 1; i < l_splitted.length; i++) {
				Country l_neighbor = p_countries.get(Integer.parseInt(l_splitted[i]));
				// If both the current country and neighbor exist, establish a neighbor
				// relationship.
				if (d_country != null && l_neighbor != null) {
					d_country.addNeighbor(l_neighbor);
				}
			}
		}
	}

	/**
	 * The function reads continent data from a scanner and returns a map of
	 * continent objects.
	 *
	 * @param p_scanner
	 *            The parameter `p_scanner` is of type `Scanner`. It is used to read
	 *            input from a source, such as a file or user input. In this method,
	 *            it is used to read lines of text from the input source.
	 * @return The method is returning a object containing continentID as key and
	 *         Continent object as value.
	 */
	public Map<Integer, Continent> readContinents(Scanner p_scanner) {

		String l_line;
		HashMap<Integer, Continent> l_continents = new HashMap<>();
		int l_continentId = 1;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();

			if (l_line.equals(Constants.MAP_READER_COUNTRIES) || l_line.equals(Constants.MAP_READER_BORDERS)
					|| l_line.isEmpty()) {
				break;
			}
			// Parse the line to create a Continent object with the current continent ID.
			Continent l_continent = mapContinent(l_line, l_continentId);
			l_continents.put(l_continent.getContinentId(), l_continent);
			l_continentId++;
		}
		return l_continents;
	}

	/**
	 * The function takes a string as input, splits it into an array of substrings,
	 * and uses the substrings to create and return a new Country object.
	 *
	 * @param p_line
	 *            The parameter `p_line` is a string that represents a line of data
	 *            containing information about a country.
	 * @return The method is returning a Country object.
	 */
	public Country mapCountry(String p_line) {
		String[] l_splitted = p_line.split(Constants.SPACE);
		return new Country(Integer.parseInt(l_splitted[0]), l_splitted[1], Integer.parseInt(l_splitted[2]), 0);
	}

	/**
	 * The function takes a string and an integer as input, splits the string by
	 * space, and returns a new Continent object with the continent ID, name, and
	 * population extracted from the string.
	 *
	 * @param p_line
	 *            The p_line parameter is a string that represents a line of data
	 *            containing information about a continent.
	 * @param p_continentId
	 *            The p_continentId parameter is an integer that represents the
	 *            unique identifier for the continent.
	 * @return The method is returning a Continent object.
	 */
	public Continent mapContinent(String p_line, int p_continentId) {
		String[] l_splitted = p_line.split(Constants.SPACE);
		return new Continent(p_continentId, l_splitted[0], Integer.parseInt(l_splitted[1]));
	}

	/**
	 * The function `loadMapFile` reads a map file, extracts information about
	 * continents, countries, and borders, and returns a `GameMap` object containing
	 * this information.
	 *
	 * @param p_mapFilePath
	 *            A String that represents the path of the map file that needs to be
	 *            loaded.
	 * @return The method is returning a GameMap object.
	 */
	public GameMap loadMapFile(String p_mapFilePath) {
		Map<Integer, Country> l_countries = new HashMap<>();
		Map<Integer, Continent> l_continents = new HashMap<>();
		GameMap l_gameMap = new GameMap();

		try {
			FileReader l_reader = new FileReader(p_mapFilePath);

			Scanner l_scanner = new Scanner(l_reader);
			String l_line;

			// read until continents
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals(Constants.MAP_READER_CONTINENTS)) {
					l_continents = readContinents(l_scanner);
					break;
				}
			}

			// read until countries
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals(Constants.MAP_READER_COUNTRIES)) {
					l_countries = readCountries(l_scanner, l_continents);
					break;
				}
			}

			// read until border
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals(Constants.MAP_READER_BORDERS)) {
					parseBorders(l_countries, l_scanner);
					break;
				}
			}

			// Add the loaded countries and continents to the GameMap.
			l_gameMap.addCountries(l_countries);
			l_gameMap.addContinents(l_continents);

		} catch (FileNotFoundException e) {
			d_logger.log(Constants.MAP_READER_FILE_NOT_FOUND);
		}
		return l_gameMap;
	}
}
