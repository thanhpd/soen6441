package com.w10.risk_game.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/*
 * @author Omnia Alam
 * This class reads a map file and initialize into the Country and Continents
 * This is use to populate the Gamemap
 */

public class MapReader {

	/**
	 *
	 * @return the map file directory
	 */
	public String getMapFolerPath() {
		return System.getProperty("user.dir") + "/src/main/resources/maps/";
	}

	/**
	 *
	 * @param takes
	 *            scanner of the file reads untill Continent, Borders or empty line
	 *            found Put it in a hashmap
	 * @return a hashmap where county id is the key and value is the country object
	 */

	public Map<Integer, Country> readCountries(Scanner p_scanner, Map<Integer, Continent> p_continents) {

		String l_line;
		HashMap<Integer, Country> l_countries = new HashMap<Integer, Country>();
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();
			if (l_line.equals("[continents]") || l_line.equals("[borders]") || l_line.isEmpty()) {
				break;
			}

			Country l_country = mapCountry(l_line);
			l_countries.put(l_country.getCountryId(), l_country);
			p_continents.get(l_country.getContinentId()).addCountry(l_country);
		}
		return l_countries;
	}

	/**
	 * @param takes
	 *            scanner and read untill continents, countries and empty line found
	 *            add the neighboring counties based on the parent id in the country
	 *            object
	 */

	public void parseBorders(Map<Integer, Country> p_countries, Scanner p_scanner) {
		String l_line;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();
			if (l_line.equals("[continents]") || l_line.equals("[countries]") || l_line.isEmpty()) {
				break;
			}
			String[] l_splitted = l_line.split(" ");
			Country d_country = p_countries.get(Integer.parseInt(l_splitted[0]));
			for (int i = 1; i < l_splitted.length; i++) {
				Country l_neighbor = p_countries.get(Integer.parseInt(l_splitted[i]));
				if (d_country != null && l_neighbor != null) {
					d_country.addNeighbor(l_neighbor);
				}
			}

		}

	}

	/**
	 *
	 * @param takes
	 *            scanner of the file reads untill Country, Borders or empty line
	 *            found Put it in a hashmap
	 * @return a hashmap where continent id is the key and value is the continent
	 *         object
	 */
	public Map<Integer, Continent> readCointinents(Scanner p_scanner) {

		String l_line;
		HashMap<Integer, Continent> l_continents = new HashMap<Integer, Continent>();
		int l_continentId = 1;
		while (p_scanner.hasNextLine()) {
			l_line = p_scanner.nextLine();

			if (l_line.equals("[countries]") || l_line.equals("[borders]") || l_line.isEmpty()) {
				break;
			}

			Continent l_continent = mapContinent(l_line, l_continentId);
			l_continents.put(l_continent.getContinentId(), l_continent);
			l_continentId++;
		}
		return l_continents;
	}

	/**
	 *
	 * @param calls
	 *            from readCountry method for each line of the country splits the
	 *            line and set it to the country object
	 * @return returns Country objects for each line
	 */
	public Country mapCountry(String p_line) {
		String[] l_splitted = p_line.split(" ");
		return new Country(Integer.parseInt(l_splitted[0]), l_splitted[1], Integer.parseInt(l_splitted[2]), 0);
	}

	/**
	 *
	 * @param calls
	 *            from readContinent method for each line of the continents splits
	 *            the line and set it to the country object
	 * @return returns Continent objects for each line
	 */

	public Continent mapContinent(String p_line, int p_continentId) {
		String[] l_splitted = p_line.split(" ");
		return new Continent(p_continentId, l_splitted[0], Integer.parseInt(l_splitted[1]));
	}

	/**
	 * Reading the map file Example: europe.map
	 *
	 * @throws file
	 *             not found expection
	 * @param mapFilename
	 *            Returns GameMap object
	 *
	 */
	public GameMap loadMapFile(String mapFilename) {
		Map<Integer, Country> l_countries = new HashMap<Integer, Country>();
		Map<Integer, Continent> l_continents = new HashMap<Integer, Continent>();
		GameMap gamemap = new GameMap();

		String l_path = getMapFolerPath() + "" + mapFilename;
		try {
			FileReader l_reader = new FileReader(l_path);

			Scanner l_scanner = new Scanner(l_reader);
			String l_line;

			// read unitl continents
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals("[continents]")) {
					l_continents = readCointinents(l_scanner);
					break;
				}
			}

			// read until countries
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals("[countries]")) {
					l_countries = readCountries(l_scanner, l_continents);
					break;
				}
			}

			// read until border
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();
				if (l_line.equals("[borders]")) {
					parseBorders(l_countries, l_scanner);
					break;
				}
			}

			gamemap.addCountries(l_countries);
			gamemap.addContinentes(l_continents);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return gamemap;
	}
}
