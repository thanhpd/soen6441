package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam This is test class on the MapEditor There are some test
 *         cases added to fulfile some validation on different methods of the
 *         class For example: add country, add continent, add neighbor remove
 *         country , continent and neighbor
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapEditorTest {
	private GameMap l_gameMap;
	private MapEditor l_mapEditor;
	private MapDisplay l_mapDisplay = new MapDisplay();

	@BeforeAll
	public void setUp() {
		MapReader l_mapReader = new MapReader();
		l_gameMap = l_mapReader.loadMapFile("test.map");
		l_mapEditor = new MapEditor(l_gameMap);

	}

	/**
	 ** Test cases for each validation senarios to add a continent to the map
	 */
	@Test
	public void testAddContinent() {
		int p_continentId = 1;
		String p_continentName = "North_Europe1";
		String error = l_mapEditor.addContinent(p_continentId, p_continentName);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_ID_EXIST.equals(error));
		assertEquals(Constants.MAP_EDITOR_CONTINENT_ID_EXIST, error);
		p_continentId = 2;
		p_continentName = "North_Europe1";
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	/*
	 * Test cases for each validation scenarios to add a country to the map that was
	 * handled in the method
	 */
	@Test
	@Disabled
	public void testAddCountry() {
		int p_countryId = 25;
		String p_countryName = "England";
		int p_continentId = 1;
		String l_error = l_mapEditor.addCountry(p_countryId, p_countryName, p_continentId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_NAME_EXIST.equals(l_error));

		p_countryId = 1;
		p_countryName = "England1";
		p_continentId = 1;
		l_error = l_mapEditor.addCountry(p_countryId, p_countryName, p_continentId);
		assertTrue(Constants.MAP_EDITOR_COUNTRY_ID_EXIST.equals(l_error));

		p_countryId = 25;
		p_countryName = "England1";
		p_continentId = 1;
		l_error = l_mapEditor.addCountry(p_countryId, p_countryName, p_continentId);
		assertTrue("Continent Name is empty!".equals(l_error));

		p_countryId = 25;
		p_countryName = "England1";
		p_continentId = 1;
		l_error = l_mapEditor.addCountry(p_countryId, p_countryName, p_continentId);
		assertTrue(Constants.MAP_EDITOR_CONTINENT_NOT_EXIST.equals(l_error));

		p_countryId = 25;
		p_countryName = "England1";
		p_continentId = 1;
		l_mapEditor.addCountry(p_countryId, p_countryName, p_continentId);
		l_mapDisplay.formatMap(l_gameMap, false);

	}

	@Test
	public void testAddNeighbor() {
		l_mapEditor.addNeighbor(1, 7);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	@Test
	public void testRemoveContinent() {
		l_mapEditor.removeContinent(1);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	@Test
	public void testRemoveCountry() {
		l_mapEditor.removeCountry(1);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

	@Test
	public void testRemoveNeighbour() {
		l_mapEditor.removeNeighbour(1, 21);
		l_mapDisplay.formatMap(l_gameMap, false);
	}

}
