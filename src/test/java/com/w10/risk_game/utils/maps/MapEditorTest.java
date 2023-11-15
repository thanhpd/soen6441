package com.w10.risk_game.utils.maps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.Constants;

/**
 * This is test class on the MapEditor There are some test cases added to
 * fulfill some validation on different methods of the class For example: add
 * country, add continent, add neighbor remove country , continent and neighbor.
 * Declaring all the instances for the test
 *
 * @author Omnia Alam
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapEditorTest {

	/**
	 * Setting up all the local variables.
	 */
	private GameMap d_gameMap;
	private MapEditor d_mapEditor;
	private MapDisplay d_mapDisplay = new MapDisplay();
	int d_continentId;
	String d_continentName;
	String d_error;
	int d_countryId;
	String d_countryName;
	int d_neighborCountryId;
	int d_bonus;

	/**
	 * Setting up all the common variable for the test scenarios
	 */
	@BeforeAll
	public void setUp() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test.map";
		DominationMap l_mapReader = new DominationMap();
		d_gameMap = l_mapReader.loadMapFile(l_mapFilePath);
		d_mapEditor = new MapEditor(d_gameMap);

	}

	/**
	 * Test scenarios to add a continent into the Game Map.
	 * testAddContinentErrorHandle function to test all the invalid commands.
	 */
	@Test
	public void testAddContinentErrorHandle() {
		d_continentName = "North_Europe";
		d_bonus = 3;
		d_error = d_mapEditor.addContinent(d_continentName, d_bonus);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NAME_EXIST.equals(d_error));
	}

	/**
	 * Test scenarios to add a continent into the Game Map. testAddContinent
	 * function to test a valid input and add a continent
	 */
	@Test
	public void testAddContinent() {
		d_continentName = "North_Europe1";
		d_bonus = 3;
		d_error = d_mapEditor.addContinent(d_continentName, d_bonus);
		assertTrue((d_continentName + Constants.MAP_EDITOR_ADD_CONTINENT).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_CONTINENT_ADDED + d_continentName);
		d_mapDisplay.displayMap(d_gameMap, false);
	}

	/**
	 * The testAddCountry function tests the addCountry method in a MapEditor class
	 * by checking for various error conditions and asserting the expected error
	 * messages. testAddCountryErrorHandle function test all the invalid input for
	 * adding a country
	 */
	@Test
	public void testAddCountryErrorHandle() {
		d_countryId = 26;
		d_countryName = "Scotland";
		d_continentName = "North_Europe";
		d_error = d_mapEditor.addCountry(d_countryId, d_countryName, d_continentName);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_NAME_EXIST.equals(d_error));

		d_countryId = 27;
		d_countryName = "England12";
		d_continentName = "North_Europe12";
		d_error = d_mapEditor.addCountry(d_countryId, d_countryName, d_continentName);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NOT_EXIST.equals(d_error));
	}

	/**
	 * The testAddCountry function tests the addCountry method in a MapEditor class
	 * by checking for various error conditions and asserting the expected error
	 * messages. testAddCountry function test valid input to add a country into the
	 */
	@Test
	public void testAddCountry() {
		d_countryId = 26;
		d_countryName = "England1";
		d_continentName = "North_Europe";
		d_error = d_mapEditor.addCountry(d_countryId, d_countryName, d_continentName);
		assertTrue((d_countryName + Constants.MAP_EDITOR_ADD_COUNTRY + d_continentName).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_COUNTRY_ADDED + d_countryName);
		d_mapDisplay.displayMap(d_gameMap, false);

	}

	/**
	 * The testAddNeighbor function tests the addNeighbor method in the MapEditor
	 * class by checking if the correct error message is returned when trying to add
	 * a neighbor to a non-existent country and if the correct success message is
	 * returned when adding a neighbor to an existing country.
	 * testAddNeighborWithErrorHandle test all the invalid inputs to remove a
	 * neighbor
	 */
	@Test
	public void testAddNeighborWithErrorHandle() {
		d_countryId = 30;
		d_neighborCountryId = 7;
		d_error = d_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_NOT_EXIST.equals(d_error));

		d_countryId = 13;
		d_neighborCountryId = 14;
		d_error = d_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_CONNECTION_EXIST).equals(d_error));
	}

	/**
	 * The testAddNeighbor function tests the addNeighbor method in the MapEditor
	 * class by checking if the correct error message is returned when trying to add
	 * a neighbor to a non-existent country and if the correct success message is
	 * returned when adding a neighbor to an existing country. testAddNeighbor test
	 * the valid inputs to remove a neighbor to the Game Map
	 */

	@Test
	public void testAddNeighbor() {
		d_countryId = 1;
		d_neighborCountryId = 12;
		d_error = d_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((d_countryId + Constants.MAP_EDITOR_ADD_NEIGHBOR + d_neighborCountryId).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_NEIGHBOR_ADDED);
		d_mapDisplay.displayMap(d_gameMap, false);
	}

	/**
	 * testRemoveContinentWithErrorHandle test all the invalid input to remove a
	 * continent
	 */
	@Test
	public void testRemoveContinentWithErrorHandle() {
		d_continentName = "North_Europe21";
		d_error = d_mapEditor.removeContinent(d_continentName);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NOT_EXIST.equals(d_error));
	}

	/**
	 * The testRemoveContinent function tests the functionality of removing a
	 * continent from a game map. testRemoveContinent test the valid input to remove
	 * a continent
	 */
	@Test
	public void testRemoveContinent() {
		d_continentName = "South_Europe";
		String d_countriesRemoved = "";
		Continent l_toRemove = d_gameMap.getContinentByName(d_continentName);
		List<Country> d_countriesToRemove = d_gameMap.getCountriesOfContinent(l_toRemove.getContinentId());
		for (Country d_country : d_countriesToRemove) {
			d_countriesRemoved = d_country.getCountryName() + ", ";
		}
		d_error = d_mapEditor.removeContinent(d_continentName);
		assertTrue((d_continentName + Constants.MAP_EDITOR_REMOVED + d_countriesRemoved
				+ Constants.MAP_EDITOR_COUNTRIES_REMOVED).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_CONTINENT_REMOVE + d_continentName);
		d_mapDisplay.displayMap(d_gameMap, false);
	}

	/**
	 * testRemoveCountryWithErrorHandle function to test all the invalid inputs to
	 * remove a country
	 */
	@Test
	public void testRemoveCountryWithErrorHandle() {
		int d_countryId = 30;
		String d_error = d_mapEditor.removeCountry(d_countryId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_ID_NOT_EXIST.equals(d_error));
	}

	/**
	 * The testRemoveCountry function tests the removeCountry method in the
	 * MapEditor class by checking if the country ID exists and if the country is
	 * successfully removed. testRemoveCountry function to test a valid inputs to
	 * remove a country
	 */
	@Test
	public void testRemoveCountry() {
		d_countryId = 1;
		d_error = d_mapEditor.removeCountry(d_countryId);
		assertTrue((d_countryId + Constants.MAP_EDITOR_COUNTRY_REMOVED).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_COUNTRY_REMOVE);
		d_mapDisplay.displayMap(d_gameMap, false);
	}

	/**
	 * testRemoveNeighborWithErrorHandle functions to test invalid inputs to remove
	 * the neighbour for the Game Map
	 */
	@Test
	public void testRemoveNeighborWithErrorHandle() {
		int d_countryId = 27;
		int d_neighborCountryId = 7;
		String d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_COUNTRY_NOT_EXIST).equals(d_error));

		d_countryId = 11;
		d_neighborCountryId = 1;
		d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_CONNECTION_NOT_EXIST).equals(d_error));

		d_countryId = 11;
		d_neighborCountryId = 27;
		d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((Constants.MAP_EDITOR_NEIGHBOR_COUNTRY_NOT_EXIST).equals(d_error));
	}

	/**
	 * testRemoveNeighbor functions to test valid inputs to remove the neighbour for
	 * the Game Map
	 */
	@Test
	public void testRemoveNeighbor() {
		d_countryId = 9;
		d_neighborCountryId = 15;
		d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((d_countryId + Constants.MAP_EDITOR_NEIGHBOR_REMOVED + d_neighborCountryId).equals(d_error));
		System.out.println(Constants.MAP_EDITOR_AFTER_NEIGHBOR_REMOVE);
		d_mapDisplay.displayMap(d_gameMap, false);
	}

}
