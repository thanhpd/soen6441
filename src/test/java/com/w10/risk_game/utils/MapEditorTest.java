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
	int l_continentId;
	String l_continentName;
	String l_error;
	int l_countryId;
	String l_countryName;
	int l_neighborCountryId;

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
	 * Invalid input to add a continent
	 */
	@Test
	public void testAddContinentErrorHandle() {
		l_continentId = 1;
		l_continentName = "North_Europe1";
		l_error = l_mapEditor.addContinent(l_continentId, l_continentName);
		assertTrue("Continent id already exists!".equals(l_error));
		assertEquals("Continent id already exists!", l_error);
	}
	/**
	 * Valid input to add a continent to the map
	 */
	@Test
	public void testAddContinent() {
		l_continentId = 7;
		l_continentName = "North_Europe1";
		l_error = l_mapEditor.addContinent(l_continentId, l_continentName);
		assertTrue((l_continentName + " is added!").equals(l_error));
		System.out.println("###############After Continent added" + l_continentName);
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

	/**
	 * Invalid input to add a country
	 */
	@Test
	public void testAddCounrtyErrorHandle() {
		l_countryId = 25;
		l_countryName = "Scotland";
		l_continentName = "North_Europe";
		l_error = l_mapEditor.addCounrty(l_countryId, l_countryName, l_continentName);
		assertTrue("Country name already exists!".equals(l_error));

		l_countryId = 9;
		l_countryName = "England12";
		l_continentName = "North_Europe";
		l_error = l_mapEditor.addCounrty(l_countryId, l_countryName, l_continentName);
		assertTrue("Country ID already exists!".equals(l_error));

		l_countryId = 25;
		l_countryName = "England1";
		l_continentName = "";
		l_error = l_mapEditor.addCounrty(l_countryId, l_countryName, l_continentName);
		assertTrue("Continent Name is emplty!".equals(l_error));

		l_countryId = 30;
		l_countryName = "England12";
		l_continentName = "North_Europe12";
		l_error = l_mapEditor.addCounrty(l_countryId, l_countryName, l_continentName);
		assertTrue("Continent doesnot exists!".equals(l_error));
	}

	/**
	 * Valid input to add a country
	 */
	@Test
	public void testAddCounrty() {

		l_countryId = 25;
		l_countryName = "England1";
		l_continentName = "North_Europe";
		l_error = l_mapEditor.addCountry(l_countryId, l_countryName, l_continentName);
		assertTrue((l_countryName + " is Added! and to the continent " + l_continentName).equals(l_error));
		System.out.println("###############After Country added " + l_countryName);
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
		l_countryId = 30;
		l_neighborCountryId = 7;
		l_error = l_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
		assertTrue("Country does not exist! please add first".equals(l_error));

		l_countryId = 13;
		l_neighborCountryId = 14;
		l_error = l_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Connection already exists!").equals(l_error));
	}
	/**
	 * Valid inputs to add a neighbor
	 */

	@Test
	public void testAddNeighbor() {
		l_countryId = 1;
		l_neighborCountryId = 12;
		l_error = l_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
		assertTrue((l_countryId + " added with " + l_neighborCountryId).equals(l_error));
		System.out.println("###############After Neighbor added");
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
		l_continentId = 5;
		l_error = l_mapEditor.removeContinent(l_continentId);
		assertTrue("Continent doesnot exists".equals(l_error));
	}
	/**
	 * Valid inputs to remove a continent
	 */
	@Test
	public void testRemoveContinent() {
		l_continentId = 2;
		String l_countriesRemoved = "";
		ArrayList<Country> l_countriesToRemove = l_gameMap.getCountriesOfContinent(l_continentId);
		for (Country l_country : l_countriesToRemove) {
			l_countriesRemoved = l_country.getCountryName() + ", ";
		}
		l_error = l_mapEditor.removeContinent(l_continentId);
		assertTrue(
				(l_continentId + " removed!" + l_countriesRemoved + "these countries also removed!").equals(l_error));
		System.out.println("###############After Removing Conitnent " + l_continentId);
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
		int l_countryId = 26;
		String l_error = l_mapEditor.removeCountry(l_countryId);
		assertTrue("Country id doesnot exists".equals(l_error));
	}
	/**
	 * Valid inputs to remove a country
	 */
	@Test
	public void testRemoveCountry() {
		l_countryId = 1;
		l_error = l_mapEditor.removeCountry(l_countryId);
		assertTrue((l_countryId + "Country removed!").equals(l_error));
		System.out.println("###############After Removing country");
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/**
	 * The testRemoveneighbor function tests the functionality of the removeneighbor
	 * method in the MapEditor class.
	 */
	/**
	 * Invalid inputs test to remove a neighbor
	 */
	@Test
	public void testRemoveNeighborWithErrorHandle() {
		int l_countryId = 26;
		int l_neighborCountryId = 7;
		String l_error = l_mapEditor.removeNeighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Country does not exist! please add first").equals(l_error));

		l_countryId = 25;
		l_neighborCountryId = 12;
		l_error = l_mapEditor.removeNeighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Connection doesnot exists!").equals(l_error));

		l_countryId = 11;
		l_neighborCountryId = 27;
		l_error = l_mapEditor.removeNeighbor(l_countryId, l_neighborCountryId);
		assertTrue(("Neighbor country does not exist!please add first").equals(l_error));
	}
	/**
	 * Valid input to remove a neighbor
	 */
	@Test
	public void testRemoveneighbor() {
		l_countryId = 1;
		l_neighborCountryId = 7;
		l_error = l_mapEditor.removeNeighbor(l_countryId, l_neighborCountryId);
		assertTrue((l_countryId + " removed from " + l_neighborCountryId).equals(l_error));
		System.out.println("###############After Removing neighbor");
		l_mapDisplay.formatMap(l_gameMap, false);
	}

}
