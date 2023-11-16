package com.w10.risk_game.utils.maps;

import com.w10.risk_game.models.ConquestGameMap;
import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The ConquestMapDriver class is responsible for loading and saving Conquest
 * game maps in the Conquest file format.
 */
public class ConquestMapDriver {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * The function `loadMapFile` reads a Conquest game map file, extracts the
	 * continents and countries information, and returns a ConquestGameMap object
	 * containing the loaded data.
	 *
	 * @param p_mapFilePath
	 *            The file path of the map file that needs to be loaded.
	 * @return The method is returning a ConquestGameMap object.
	 */
	public ConquestGameMap loadMapFile(String p_mapFilePath) {
		LinkedHashMap<String, Country> l_countries = new LinkedHashMap<>();
		LinkedHashMap<String, Continent> l_continents = new LinkedHashMap<>();
		ConquestGameMap l_gameMap = new ConquestGameMap();

		try {
			FileReader l_reader = new FileReader(p_mapFilePath);

			Scanner l_scanner = new Scanner(l_reader);
			String l_line;

			// read until continents
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals(Constants.CONQUEST_MAP_READER_CONTINENTS)) {
					l_continents = readContinents(l_scanner);
					break;
				}
			}

			// read until countries
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals(Constants.CONQUEST_MAP_READER_TERRITORIES)) {
					l_countries = readTerritories(l_scanner, l_continents);
					break;
				}
			}

			// Add the loaded countries and continents to the GameMap.
			l_gameMap.addCountries(l_countries);
			l_gameMap.addContinents(l_continents);

		} catch (FileNotFoundException e) {
			Logger.log(Constants.MAP_READER_FILE_NOT_FOUND);
		}
		return l_gameMap;
	}

	/**
	 * The function `saveMap` saves a ConquestGameMap object to a file in a specific
	 * format.
	 *
	 * @param p_filePath
	 *            The file path where the map will be saved.
	 * @param p_gameMap
	 *            The parameter `p_gameMap` is an instance of the `ConquestGameMap`
	 *            class, which represents the game map for the Conquest game. It
	 *            contains information about continents, countries, and their
	 *            connections.
	 */
	public void saveMap(String p_filePath, ConquestGameMap p_gameMap) {
		try (FileWriter l_fileWriter = new FileWriter(p_filePath)) {
			Logger.log(Constants.MAP_START_SAVE_CONQUEST);
			// Initialize PrintWriter object
			PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
			l_printWriter.println(
					Constants.CONQUEST_MAP_READER_MAP + Constants.NEW_LINE + Constants.CONQUEST_MAP_READER_CONTINENTS);
			// Writes continents details to new map file
			for (Continent continent : p_gameMap.getContinents().values()) {
				l_printWriter.format("%s=%d%n", continent.getContinentName(), continent.getBonus());
			}
			l_printWriter.println(Constants.NEW_LINE + Constants.CONQUEST_MAP_READER_TERRITORIES);

			for (Continent l_continent : p_gameMap.getContinents().values()) {
				// Writes country details to new map file
				for (Country country : l_continent.getCountries()) {
					/**
					 * For each country of a continent, do the following: - Get the country name -
					 * Get 0 and 0 as the coordinates - Get the continent name - Get the names of
					 * the neighbors of the country - Merge all the above details into a single
					 * string, separated by comma
					 */
					l_printWriter.format("%s,0,0,%s,%s%n", country.getCountryName(), l_continent.getContinentName(),
							country.getNeighbors().values().stream().map(item -> item.getCountryName())
									.collect(Collectors.joining(Constants.COMMA)));
				}

				l_printWriter.println();
			}

			Logger.log(Constants.MAP_SAVE_SUCCESS);
			l_printWriter.close();
		} catch (IOException e) {
			Logger.log(Constants.MAP_SAVE_ERROR);
		}
	}

	/**
	 * The function reads continent data from a scanner and returns a LinkedHashMap
	 * of continents.
	 *
	 * @param p_scanner
	 *            A Scanner object used to read input from a file or other source.
	 * @return The method is returning a LinkedHashMap<String, Continent>.
	 */
	protected LinkedHashMap<String, Continent> readContinents(Scanner p_scanner) {
		String l_line;
		LinkedHashMap<String, Continent> l_continents = new LinkedHashMap<>();
		int l_continentOrder = 1;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();

			// If the line indicates the start of territories or is empty, stop reading.
			if (l_line.equals(Constants.CONQUEST_MAP_READER_TERRITORIES) || l_line.isEmpty()) {
				break;
			}
			// Parse the line to create a Continent object with the current continent ID.
			Continent l_continent = mapContinent(l_line, l_continentOrder);
			l_continents.put(l_continent.getContinentName(), l_continent);
			l_continentOrder++;
		}
		return l_continents;
	}

	/**
	 * The function reads territory information from a scanner and creates a map of
	 * countries with their neighbors.
	 *
	 * @param p_scanner
	 *            A Scanner object used to read input from a file or other source.
	 * @param p_continents
	 *            A LinkedHashMap containing the continents in the game, where the
	 *            key is the continent name and the value is the Continent object.
	 * @return The method is returning a LinkedHashMap<String, Country> object.
	 */
	protected LinkedHashMap<String, Country> readTerritories(Scanner p_scanner,
			LinkedHashMap<String, Continent> p_continents) {
		String l_line;
		LinkedHashMap<String, Country> l_countries = new LinkedHashMap<>();
		LinkedHashMap<String, String[]> l_countryNameToNeighborNames = new LinkedHashMap<>();
		int l_lineNumber = 1;

		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();
			// If the line indicates the start of continents stop
			// reading.
			if (l_line.equals(Constants.CONQUEST_MAP_READER_CONTINENTS)) {
				break;
			}

			// If the line is empty, skip it.
			if (l_line.isEmpty()) {
				continue;
			}

			// Parse the line to create a Country object and add it to the map of countries.
			Country l_country = mapTerritory(l_line, l_lineNumber, p_continents, l_countryNameToNeighborNames);
			l_countries.put(l_country.getCountryName(), l_country);

			// Associate the country with its continent if applicable.
			p_continents.values().stream()
					.filter(l_continent -> l_continent.getContinentId() == l_country.getContinentId()).findFirst()
					.ifPresent(p_continent -> p_continent.addCountry(l_country));
			l_lineNumber++;
		}

		// Associate each country with its neighbors.
		for (String l_countryName : l_countryNameToNeighborNames.keySet()) {
			Country l_country = l_countries.get(l_countryName);
			for (String l_neighborName : l_countryNameToNeighborNames.get(l_countryName)) {
				l_country.addNeighbor(l_countries.get(l_neighborName));
			}
		}
		return l_countries;
	}

	/**
	 * The function takes a line of text and a line number, splits the line into
	 * values using the "=" delimiter, and creates a new Continent object with the
	 * line number, the first value as the name, and the second value as the
	 * population.
	 *
	 * @param p_line
	 *            The parameter `p_line` is a string that represents a line of input
	 *            data. It is expected to be in the format
	 *            "continent_name=continent_population", where `continent_name` is a
	 *            string representing the name of the continent and
	 *            `continent_population` is an integer representing the population
	 *            of the continent
	 * @param p_lineNumber
	 *            The line number of the input line being processed.
	 * @return The method is returning a Continent object.
	 */
	private Continent mapContinent(String p_line, int p_lineNumber) {
		String[] l_values = p_line.split(Constants.EQUAL);
		return new Continent(p_lineNumber, l_values[0], Integer.parseInt(l_values[1]));
	}

	/**
	 * The function maps a territory by creating a Country object and adding it to
	 * the list of countries in a continent, while also storing the neighboring
	 * countries.
	 *
	 * @param p_line
	 *            The parameter `p_line` is a string that represents a line of data
	 *            containing information about a country.
	 * @param p_lineNumber
	 *            The line number of the current line being processed.
	 * @param p_continents
	 *            A LinkedHashMap that maps continent names to Continent objects.
	 *            Each Continent object represents a continent and contains
	 *            information about the continent.
	 * @param p_countryNameToNeighborNames
	 *            A LinkedHashMap that maps a country name to an array of its
	 *            neighbor names.
	 * @return The method is returning an instance of the Country class.
	 */
	private Country mapTerritory(String p_line, int p_lineNumber, LinkedHashMap<String, Continent> p_continents,
			LinkedHashMap<String, String[]> p_countryNameToNeighborNames) {
		String[] l_values = p_line.split(Constants.COMMA);
		Country l_country = new Country(p_lineNumber, l_values[0], p_continents.get(l_values[3]).getContinentId(), 0);
		p_countryNameToNeighborNames.put(l_country.getCountryName(), Arrays.copyOfRange(l_values, 4, l_values.length));

		return l_country;
	}
}
