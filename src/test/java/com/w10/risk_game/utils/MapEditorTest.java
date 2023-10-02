package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam This is test class on the MapEditor There are some test
 *         cases added to fulfile some validation on different methods of the
 *         class For example: add country, add continent, add neighbor remove
 *         country , continent and neighbor
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapEditorTest {
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

	@BeforeAll
	public void setUp() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test.map";
		MapReader l_mapReader = new MapReader();
		d_gameMap = l_mapReader.loadMapFile(l_mapFilePath);
		d_mapEditor = new MapEditor(d_gameMap);

	}

	/**
	 * Invalid input to add a continent
	 */
	@Test
	public void testAddContinentErrorHandle() {
		d_continentName = "North_Europe";
		d_bonus = 3;
		d_error = d_mapEditor.addContinent(d_continentName, d_bonus);
		assertTrue("Continent name already exists!".equals(d_error));
	}

	/**
	 * Valid input to add a continent to the map
	 */
	@Test
	public void testAddContinent() {
		d_continentName = "North_Europe1";
		d_bonus = 3;
		d_error = d_mapEditor.addContinent(d_continentName, d_bonus);
		assertTrue((d_continentName + " is added!").equals(d_error));
		System.out.println("###############After Continent added" + d_continentName);
		d_mapDisplay.formatMap(d_gameMap, false);
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
		d_continentName = "North_Europe";
		d_error = d_mapEditor.addCountry(d_countryId, d_countryName, d_continentName);
		assertTrue("Country name already exists!".equals(d_error));

		d_countryId = 27;
		d_countryName = "England12";
		d_continentName = "North_Europe12";
		d_error = d_mapEditor.addCountry(d_countryId, d_countryName, d_continentName);
		assertTrue("Continent does not exists!".equals(d_error));
	}

	/**
	 * Valid input to add a country
	 */
	@Test
	@Disabled
	public void testAddCountry() {
		d_countryId = 26;
		d_countryName = "England1";
		d_continentName = "North_Europe";
		d_error = d_mapEditor.addCountry(d_countryId, d_countryName, d_continentName);
		assertTrue((d_countryName + " is Added! and to the continent with id: " + d_continentName).equals(d_error));
		System.out.println("###############After Country added " + d_countryName);
		d_mapDisplay.formatMap(d_gameMap, false);

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
		d_error = d_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue("Country does not exist! please add first".equals(d_error));

		d_countryId = 13;
		d_neighborCountryId = 14;
		d_error = d_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue(("Connection already exists!").equals(d_error));
	}

	/**
	 * Valid inputs to add a neighbor
	 */

	@Test
	public void testAddNeighbor() {
		d_countryId = 1;
		d_neighborCountryId = 12;
		d_error = d_mapEditor.addNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((d_countryId + " added with " + d_neighborCountryId).equals(d_error));
		System.out.println("###############After Neighbor added");
		d_mapDisplay.formatMap(d_gameMap, false);
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
		d_continentName = "North_Europe21";
		d_error = d_mapEditor.removeContinent(d_continentName);
		assertTrue("Continent does not exists".equals(d_error));
	}

	/**
	 * Valid inputs to remove a continent
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
		assertTrue(
				(d_continentName + " removed!" + d_countriesRemoved + "these countries also removed!").equals(d_error));
		System.out.println("###############After Removing Continent " + d_continentName);
		d_mapDisplay.formatMap(d_gameMap, false);
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
		String d_error = d_mapEditor.removeCountry(d_countryId);
		assertTrue("Country id does not exists".equals(d_error));
	}

	/**
	 * Valid inputs to remove a country
	 */
	@Test
	public void testRemoveCountry() {
		d_countryId = 1;
		d_error = d_mapEditor.removeCountry(d_countryId);
		assertTrue((d_countryId + "Country removed!").equals(d_error));
		System.out.println("###############After Removing country");
		d_mapDisplay.formatMap(d_gameMap, false);
	}

	@Test
	public void testRemoveNeighborWithErrorHandle() {
		int d_countryId = 27;
		int d_neighborCountryId = 7;
		String d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue(("Country does not exist! please add first").equals(d_error));

		d_countryId = 11;
		d_neighborCountryId = 1;
		d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue(("Connection does not exists!").equals(d_error));

		d_countryId = 11;
		d_neighborCountryId = 27;
		d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue(("Neighbor country does not exist!please add first").equals(d_error));
	}

	/**
	 * Valid input to remove a neighbor
	 */
	@Test
	public void testRemoveNeighbor() {
		d_countryId = 9;
		d_neighborCountryId = 15;
		d_error = d_mapEditor.removeNeighbor(d_countryId, d_neighborCountryId);
		assertTrue((d_countryId + " removed from " + d_neighborCountryId).equals(d_error));
		System.out.println("###############After Removing neighbor");
		d_mapDisplay.formatMap(d_gameMap, false);
	}

}
