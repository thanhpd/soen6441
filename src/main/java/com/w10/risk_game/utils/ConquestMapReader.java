package com.w10.risk_game.utils;

import com.w10.risk_game.models.ConquestGameMap;
import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
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

public class ConquestMapReader {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

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

	public void saveMap(String p_filePath, ConquestGameMap p_gameMap) {
		try (FileWriter l_fileWriter = new FileWriter(p_filePath)) {
			// Initialize PrintWriter object
			PrintWriter l_printWriter = new PrintWriter(l_fileWriter);
			l_printWriter.println(Constants.CONQUEST_MAP_READER_MAP + Constants.NEW_LINE
					+ Constants.CONQUEST_MAP_READER_CONTINENTS);
			// Writes continents details to new map file
			for (Continent continent : p_gameMap.getContinents().values()) {
				l_printWriter.format("%s=%d%n", continent.getContinentName(), continent.getBonus());
			}
			l_printWriter.println(Constants.NEW_LINE + Constants.CONQUEST_MAP_READER_TERRITORIES);

			for (Continent l_continent : p_gameMap.getContinents().values()) {
				// Writes country details to new map file
				for (Country country : l_continent.getCountries()) {
					/**
					 * For each country of a continent, do the following:
					 * - Get the country name
					 * - Get 0 and 0 as the coordinates
					 * - Get the continent name
					 * - Get the names of the neighbors of the country
					 * - Merge all the above details into a single string, separated by comma
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

	protected LinkedHashMap<String, Continent> readContinents(Scanner p_scanner) {
		String l_line;
		LinkedHashMap<String, Continent> l_continents = new LinkedHashMap<>();
		int l_continentOrder = 1;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();

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

	protected LinkedHashMap<String, Country> readTerritories(Scanner p_scanner,
			LinkedHashMap<String, Continent> p_continents) {
		String l_line;
		LinkedHashMap<String, Country> l_countries = new LinkedHashMap<>();
		LinkedHashMap<String, String[]> l_countryNameToNeighborNames = new LinkedHashMap<>();
		int l_lineNumber = 1;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();
			// If the line indicates the start of continents, borders, or is empty, stop
			// reading.
			if (l_line.equals(Constants.CONQUEST_MAP_READER_CONTINENTS)) {
				break;
			}

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

		for (String l_countryName : l_countryNameToNeighborNames.keySet()) {
			Country l_country = l_countries.get(l_countryName);
			for (String l_neighborName : l_countryNameToNeighborNames.get(l_countryName)) {
				l_country.addNeighbor(l_countries.get(l_neighborName));
			}
		}
		return l_countries;
	}

	private Continent mapContinent(String p_line, int p_lineNumber) {
		String[] l_values = p_line.split(Constants.EQUAL);
		return new Continent(p_lineNumber, l_values[0], Integer.parseInt(l_values[1]));
	}

	private Country mapTerritory(String p_line, int p_lineNumber, LinkedHashMap<String, Continent> p_continents,
			LinkedHashMap<String, String[]> p_countryNameToNeighborNames) {
		String[] l_values = p_line.split(Constants.COMMA);
		// TODO: handle exception
		Country l_country = new Country(p_lineNumber, l_values[0], p_continents.get(l_values[3]).getContinentId(), 0);
		p_countryNameToNeighborNames.put(l_country.getCountryName(), Arrays.copyOfRange(l_values, 4, l_values.length));

		return l_country;
	}
}
