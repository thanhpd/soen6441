package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.Constants;

/**
 * @author Omnia Alam This is test class on the MapEditor There are some test
 *         cases added to fulfil some validation on different methods of the
 *         class For example: add country, add continent, add neighbor remove
 *         country , continent and neighbor
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
 * MapEditorTest` is a test class for the `MapEditor` class. It contains test
 * cases to validate the functionality of various methods in the `MapEditor`
 * class, such as adding continents, adding countries, adding neighbors,
 * removing continents, removing countries, and removing neighbors. The test
 * cases check for different error conditions and assert the expected error
 * messages
 */

public class MapEditorTest {
	private GameMap l_gameMap;
	private MapEditor l_mapEditor;
	private MapDisplay l_mapDisplay = new MapDisplay();
	int d_continentId;
	String d_continentName;
	String d_error;
	int d_countryId;
	String d_countryName;
	int d_neighborCountryId;
	int d_bonus;

	@BeforeAll
	/*
	 * The `setUp()` method is a setup method that is executed before running any
	 * test cases in the `MapEditorTest` class. It is used to initialize the
	 * necessary objects and variables required for the test cases.
	 */
	public void setUp() {
		MapReader l_mapReader = new MapReader();
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test.map";
		l_gameMap = l_mapReader.loadMapFile(l_mapFilePath);
		l_mapEditor = new MapEditor(l_gameMap);

	}
	/*
	 * The testAddContinent function tests the addContinent method in a MapEditor
	 * class by checking for various error conditions and asserting the expected
	 * error messages.
	 */

	/**
	 * Invalid input to add a continent
	 */
	@Test
	public void testAddContinentErrorHandle() {
		d_continentName = "North_Europe";
		d_bonus = 3;
		d_error = l_mapEditor.addContinent(d_continentName, d_bonus);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NAME_EXIST.equals(d_error));
	}
	/**
	 * Valid input to add a continent to the map
	 */
	@Test
	public void testAddContinent() {
		d_continentName = "North_Europe1";
		d_bonus = 3;
		d_error = l_mapEditor.addContinent(d_continentName, d_bonus);
		assertTrue((d_continentName + Constants.MAP_EDITOR_ADD_CONTINENT).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_CONTINENT_ADDED + d_continentName);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testAddCountry function tests the addCountry method in a MapEditor class
	 * by checking for various error conditions and asserting the expected error
	 * messages.
	 */

	/**
	 * Invalid input to add a country
	 */
	@Test
	public void testAddCountryErrorHandle() {
		d_countryId = 26;
		d_countryName = "Scotland";
		d_continentId = 1;
		d_error = l_mapEditor.addCountry(d_countryId, d_countryName, d_continentId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_NAME_EXIST.equals(d_error));

		d_countryId = 27;
		d_countryName = "England12";
		d_continentId = 15;
		d_error = l_mapEditor.addCountry(d_countryId, d_countryName, d_continentId);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NOT_EXIST.equals(d_error));
	}

	/**
	 * Valid input to add a country
	 */
	@Test
	public void testAddCountry() {
		d_countryId = 26;
		d_countryName = "England1";
		d_continentId = 1;
		d_error = l_mapEditor.addCountry(d_countryId, d_countryName, d_continentId);
		assertTrue((d_countryName + Constants.MAP_EDITOR_ADD_COUNTRY + d_continentId).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_COUNTRY_ADDED + d_countryName);
		l_mapDisplay.formatMap(l_gameMap, false);

	}

	/**
	 * The testAddNeighbor function tests the addNeighbor method in the MapEditor
	 * class by checking if the correct error message is returned when trying to add
	 * a neighbor to a non-existent country and if the correct success message is
	 * returned when adding a neighbor to an existing country.
	 */

	/**
	 * Invalid inputs to remove a neighbor
	 */
	@Test
	public void testAddNeighborWithErrorHandle() {
		d_countryId = 30;
		d_neighborCountryId = 7;
		d_error = l_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_NOT_EXIST.equals(d_error));

		d_countryId = 13;
		d_neighborCountryId = 14;
		d_error = l_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_CONNECTION_EXIST).equals(d_error));
	}
	/**
	 * Valid inputs to add a neighbor
	 */

	@Test
	public void testAddNeighbor() {
		d_countryId = 1;
		d_neighborCountryId = 12;
		d_error = l_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((d_countryId + Constants.MAP_EDITOR_ADD_NEIGHBOR + d_neighborCountryId).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_NEIGHBOR_ADDED);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveContinent function tests the functionality of removing a
	 * continent from a game map.
	 */

	/**
	 * Invalid input to remove a continent
	 */
	@Test
	public void testRemoveContinentWithErrorHandle() {
		d_continentId = 5;
		d_error = l_mapEditor.removeContinent(d_continentId);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NOT_EXIST.equals(d_error));
	}
	/**
	 * Valid inputs to remove a continent
	 */
	@Test
	public void testRemoveContinent() {
		d_continentId = 2;
		String d_countriesRemoved = "";
		ArrayList<Country> d_countriesToRemove = l_gameMap.getCountriesOfContinent(d_continentId);
		for (Country d_country : d_countriesToRemove) {
			d_countriesRemoved = d_country.getCountryName() + ", ";
		}
		d_error = l_mapEditor.removeContinent(d_continentId);
		assertTrue((d_continentId + Constants.MAP_EDITOR_REMOVED + d_countriesRemoved
				+ Constants.MAP_EDITOR_COUNTRIES_REMOVED).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_CONTINENT_REMOVE + d_continentId);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveCountry function tests the removeCountry method in the
	 * MapEditor class by checking if the country ID exists and if the country is
	 * successfully removed.
	 */

	/**
	 * Invalid inputs to remove a country
	 */
	@Test
	public void testRemoveCountryWithErrorHandle() {
		int d_countryId = 30;
		String d_error = l_mapEditor.removeCountry(d_countryId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_ID_NOT_EXIST.equals(d_error));
	}
	/**
	 * Valid inputs to remove a country
	 */
	@Test
	public void testRemoveCountry() {
		d_countryId = 1;
		d_error = l_mapEditor.removeCountry(d_countryId);
		assertTrue((d_countryId + Constants.MAP_EDITOR_COUNTRY_REMOVED).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_COUNTRY_REMOVE);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveNeighbor function tests the functionality of the removeNeighbor
	 * method in the MapEditor class.
	 */
	/**
	 * Invalid inputs test to remove a neighbor
	 */
	@Test
	public void testRemoveNeighborWithErrorHandle() {
		int d_countryId = 27;
		int d_neighborCountryId = 7;
		String d_error = l_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_COUNTRY_NOT_EXIST).equals(d_error));

		d_countryId = 11;
		d_neighborCountryId = 1;
		d_error = l_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_CONNECTION_NOT_EXIST).equals(d_error));

		d_countryId = 11;
		d_neighborCountryId = 27;
		d_error = l_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_NEIGHBOR_COUNTRY_NOT_EXIST).equals(d_error));
	}
	/**
	 * Valid input to remove a neighbor
	 */
	@Test
	public void testRemoveNeighbor() {
		d_countryId = 1;
		d_neighborCountryId = 7;
		d_error = l_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((d_countryId + Constants.MAP_EDITOR_NEIGHBOR_REMOVED + d_neighborCountryId).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_NEIGHBOR_REMOVE);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

}
