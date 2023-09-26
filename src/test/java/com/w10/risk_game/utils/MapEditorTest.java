package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam This is test class on the MapEditor There are some test
 *         cases added to fulfile some validation on different methods of the
 *         class For example: add country, add continent, add neighbor remove
 *         country , continent and neighbor
 */

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
		assertTrue("Continent id already exists!".equals(error));
		assertEquals("Continent id already exists!", error);
		p_continentId = 2;
		p_continentName = "North_Europe1";
		l_mapDisplay.formatMap(l_gameMap,false);
	}

	/*
	 * Test cases for each validation senarios to add a country to the map that was
	 * handled in the method
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
		l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
		l_mapDisplay.formatMap(l_gameMap,false);

	}

	@Test
	public void testAddNeighbor() {
		l_mapEditor.addNeighbor(1, 7);
		l_mapDisplay.formatMap(l_gameMap,false);
	}

	@Test
	public void testRemoveContinent() {
		l_mapEditor.removeContinent("North_Europe");
		l_mapDisplay.formatMap(l_gameMap,false);
	}

	@Test
	public void testRemoveCountry() {
		l_mapEditor.removeCountry(1);
		l_mapDisplay.formatMap(l_gameMap,false);
	}

	@Test
	public void testRemoveNeighbour() {
		l_mapEditor.removeNeighbour(1, 21);
		l_mapDisplay.formatMap(l_gameMap,false);
	}

}
