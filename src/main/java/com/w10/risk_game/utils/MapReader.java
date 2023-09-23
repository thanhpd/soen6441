/*
 * @author Omnia Alam
 * in the MapReader class,
 * we have a function called showMap that prints the map in text.
 * to use the show map, we need to call the function called readMapFile(String mapFilename).
 * We need to link the Commandline "showmap filename" with readMapFile(String mapFilename) and pass the map file name
 *
 */

package com.w10.risk_game.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

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
	 *
	 *
	 * @return Returns a list of countries with their id as key
	 */

	public Map<Integer, Country> readCountries(Scanner scanner) {

		String line;
		HashMap<Integer, Country> countries = new HashMap<Integer, Country>();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.equals("[continents]") || line.equals("[borders]") || line.isEmpty()) {
				break;
			}

			Country country = mapCountry(line);
			countries.put(country.getCountryId(), country);

		}
		return countries;
	}

	/**
	 *
	 * creates list of neighbouring countries with their parent country id
	 */

	public void parseBorders(Map<Integer, Country> countries, Scanner scanner) {
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.equals("[continents]") || line.equals("[countries]") || line.isEmpty()) {
				break;
			}
			String[] splitted = line.split(" ");
			Country country = countries.get(Integer.parseInt(splitted[0]));
			for (int i = 1; i < splitted.length; i++) {
				Country neighbor = countries.get(Integer.parseInt(splitted[i]));
				country.addNeighbor(neighbor);
			}

		}

	}

	/**
	 *
	 * @return Returns a list of contnents with their id as key
	 */
	public Map<Integer, Continent> readCointinents(Scanner scanner) {

		String line;
		HashMap<Integer, Continent> continents = new HashMap<Integer, Continent>();
		int continentId = 1;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

			if (line.equals("[countries]") || line.equals("[borders]") || line.isEmpty()) {
				break;
			}

			Continent continent = mapContinent(line, continentId);
			continents.put(continent.getContinentId(), continent);
			continentId++;
		}
		return continents;
	}

	/**
	 *
	 *
	 * @return returns Country objects for each line
	 */

	public Country mapCountry(String line) {
		Country country = new Country();
		String[] splitted = line.split(" ");
		country.setCountryId(Integer.parseInt(splitted[0]));
		country.setCountryName(splitted[1]);
		country.setContinentId(Integer.parseInt(splitted[2]));
		return country;
	}

	/**
	 *
	 *
	 * @return returns Continent objecct for each line
	 */

	public Continent mapContinent(String line, int continentId) {
		Continent continent = new Continent();
		String[] splitted = line.split(" ");
		continent.setContinentId(continentId);
		continent.setContinentName(splitted[0]);
		return continent;
	}

	// read a map and show it in the commandline
	public void showMap(Map<Integer, Country> countries, Map<Integer, Continent> continents) {
		System.out.println("###############List of Continents:################");
		for (Integer key : continents.keySet()) {
			System.out.println(key + " = " + continents.get(key));
		}
		System.out.println("#########List of Countries:############");
		for (Integer key : countries.keySet()) {
			System.out.println(key + " = " + countries.get(key));
		}

	}

	/**
	 * Reading the map file Example: europe.map
	 *
	 * @param mapFilename
	 *            Returns GameMap object
	 *
	 */
	public GameMap loadMapFile(String mapFilename) {
		Map<Integer, Country> countries = new HashMap<Integer, Country>();
		Map<Integer, Continent> continents = new HashMap<Integer, Continent>();
		GameMap gamemap = new GameMap();

		String path = getMapFolerPath() + "" + mapFilename;
		try {
			FileReader reader = new FileReader(path);

			Scanner scanner = new Scanner(reader);
			String line;

			// read unitl continents
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (line.equals("[continents]")) {
					continents = readCointinents(scanner);
					break;
				}
			}

			// read until countries
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (line.equals("[countries]")) {
					countries = readCountries(scanner);
					break;
				}
			}

			// read until border
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (line.equals("[borders]")) {
					parseBorders(countries, scanner);
					break;
				}
			}

			gamemap.addCountries(countries);
			gamemap.addContinentes(continents);
			showMap(countries, continents);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return gamemap;
	}
}
