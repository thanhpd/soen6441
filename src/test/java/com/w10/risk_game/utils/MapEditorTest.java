package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam This is test class on the MapEditor There are some test
 *         cases added to fulfile some validation on different methods of the
 *         class For example: add country, add continent, add neighbor remove
 *         country , continent and neighbor
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// `MapEditorTest` is a test class for the `MapEditor` class. It contains test
// cases to
// validate the functionality of various methods in the `MapEditor` class, such
// as adding
// continents, adding countries, adding neighbors, removing continents, removing
// countries, and removing neighbors. The test cases check for different error
// conditions
// and assert the expected error messages.
public class MapEditorTest {
	private GameMap l_gameMap;
	private MapEditor l_mapEditor;
	private MapDisplay l_mapDisplay = new MapDisplay();

	@BeforeAll
	// The `setUp()` method is a setup method that is executed before running any
	// test cases in the
	// `MapEditorTest` class. It is used to initialize the necessary objects and
	// variables required for the
	// test cases.
	public void setUp() {
		MapReader l_mapReader = new MapReader();
		l_gameMap = l_mapReader.loadMapFile("test.map");
		l_mapEditor = new MapEditor(l_gameMap);

	}

	/**
	 * The testAddContinent function tests the addContinent method in the MapEditor
	 * class by checking if an error message is returned when a continent with the
	 * same id already exists.
	 */
	@Test
	public void testAddContinent() {
		int p_continentId = 1;
		String p_continentName = "North_Europe1";
		String error = l_mapEditor.addContinent(p_continentId, p_continentName);
		assertTrue("Continent id already exists!".equals(error));
		assertEquals("Continent id already exists!", error);
		p_continentId = 2;
		p_continentName = "North_Europe1";
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/*
	 * Test cases for each validation senarios to add a country to the map that was
	 * handled in the method
	 */
	/**
	 * The testAddCountry function tests the addCountry method in a MapEditor class
	 * by checking for various error conditions and asserting the expected error
	 * messages.
	 */
	@Test
	public void testAddCounrty() {
		int p_countryId = 25;
		String p_countryName = "England";
		String p_continentName = "North_Europe";
		String l_error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
		assertTrue("Country name already exists!".equals(l_error));

		p_countryId = 1;
		p_countryName = "England1";
		p_continentName = "North_Europe";
		l_error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
		assertTrue("Country ID already exists!".equals(l_error));

		p_countryId = 25;
		p_countryName = "England1";
		p_continentName = "";
		l_error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
		assertTrue("Continent Name is emplty!".equals(l_error));

		p_countryId = 25;
		p_countryName = "England1";
		p_continentName = "North_Europe1";
		l_error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
		assertTrue("Continent doesnot exists!".equals(l_error));

		p_countryId = 25;
		p_countryName = "England1";
		p_continentName = "North_Europe";
		l_error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
		assertTrue((p_countryName + " is Added! and to the continent " + p_continentName).equals(l_error));
		l_mapDisplay.formatMap(l_gameMap, false);

	}

	/**
	 * The testAddNeighbor function tests the addNeighbor method in the MapEditor
	 * class by checking if the correct error message is returned when trying to add
	 * a neighbor to a non-existent country and if the correct success message is
	 * returned when adding a neighbor to an existing country.
	 */
	@Test
	public void testAddNeighbor() {
		int l_countryId = 30;
		int l_neighborCountryId = 7;
		String l_error = l_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
		assertTrue("Country does not exist! please add first".equals(l_error));

		l_countryId = 1;
		l_neighborCountryId = 8;
		l_error = l_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Connection already exists!").equals(l_error));

		l_countryId = 1;
		l_neighborCountryId = 12;
		l_error = l_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
		assertTrue((l_countryId + " added with " + l_neighborCountryId).equals(l_error));
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveContinent function tests the functionality of removing a
	 * continent from a game map.
	 */
	@Test
	public void testRemoveContinent() {
		int l_continentId = 5;
		String l_error = l_mapEditor.removeContinent(l_continentId);
		assertTrue("Continent doesnot exists".equals(l_error));

		l_continentId = 2;
		String l_countriesRemoved = "";
		ArrayList<Country> l_countriesToRemove = l_gameMap.getCountriesOfContinent(l_continentId);
		for (Country l_country : l_countriesToRemove) {
			l_countriesRemoved = l_country.getCountryName() + ", ";
		}
		l_error = l_mapEditor.removeContinent(l_continentId);
		assertTrue(
				(l_continentId + " removed!" + l_countriesRemoved + "these countries also removed!").equals(l_error));
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveCountry function tests the removeCountry method in the
	 * MapEditor class by checking if the country ID exists and if the country is
	 * successfully removed.
	 */
	@Test
	public void testRemoveCountry() {
		int l_countryId = 26;
		String l_error = l_mapEditor.removeCountry(l_countryId);
		assertTrue("Country id doesnot exists".equals(l_error));

		l_countryId = 1;
		l_error = l_mapEditor.removeCountry(l_countryId);
		assertTrue((l_countryId + "Country removed!").equals(l_error));
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveneighbor function tests the functionality of the removeneighbor
	 * method in the MapEditor class.
	 */
	@Test
	public void testRemoveneighbor() {
		int l_countryId = 26;
		int l_neighborCountryId = 7;
		String l_error = l_mapEditor.removeneighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Country does not exist! please add first").equals(l_error));

		l_countryId = 11;
		l_neighborCountryId = 8;
		l_error = l_mapEditor.removeneighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Connection doesnot exists!").equals(l_error));

		l_countryId = 11;
		l_neighborCountryId = 27;
		l_error = l_mapEditor.removeneighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Neighbor country does not exist!please add first").equals(l_error));

		l_countryId = 1;
		l_neighborCountryId = 7;
		l_error = l_mapEditor.removeneighbor(l_countryId, l_neighborCountryId);
		assertTrue((l_countryId + " added with " + l_neighborCountryId).equals(l_error));
		l_mapDisplay.formatMap(l_gameMap, false);
	}

}
