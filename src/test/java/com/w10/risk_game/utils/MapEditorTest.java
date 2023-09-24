package com.w10.risk_game.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam
 * This is test class on the MapEditor
 * There are some test cases added to fulfile some validation on different methods of the class
 * For example: add country, add continent, add neighbor
 * remove country , continent and neighbor
 */

public class MapEditorTest {
    private GameMap l_gameMap;
    private MapEditor l_mapEditor;
    private MapDisplay l_mapDisplay = new MapDisplay();
    @Before
    public void setUp() {
        MapReader l_mapReader = new MapReader();
        l_gameMap = l_mapReader.loadMapFile("test.map");
        l_mapEditor = new MapEditor(l_gameMap);

    }
    /*
   * Test cases for each validation senarios to add a continent to the map 
   */
    @Test
    public void testAddContinent() {
        int p_continentId = 1;
        String p_continentName = "North_Europe1";
        String error = l_mapEditor.addContinent(p_continentId, p_continentName);
        assertTrue("Continent id already exists!".equals(error));
        assertEquals("Continent id already exists!", error);
        p_continentId = 2;
        p_continentName = "North_Europe";
        error = l_mapEditor.addContinent(p_continentId, p_continentName);
        assertTrue("Continent name already exists!".equals(error));
        assertEquals("Continent name already exists!", error);
    }
    /*
    * Test cases for each validation senarios to add a country to the map that was handled in the method
    */
    @Test
    public void testAddCounrty() {
        int p_countryId = 25;
        String p_countryName = "England";
        String p_continentName = "North_Europe";
        String error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Country name already exists!".equals(error));
        p_countryId = 1;
        p_countryName = "England1";
        p_continentName = "North_Europe";
        error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Country ID already exists!".equals(error));
        p_countryId = 25;
        p_countryName = "England1";
        p_continentName = "";
        error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Continent Name is emplty!".equals(error));
        p_countryId = 25;
        p_countryName = "England1";
        p_continentName = "North_Europe1";
        error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Continent doesnot exists!".equals(error));

        /*Failed Case */
        p_countryId = 25;
        p_countryName = "England1";
        p_continentName = "North_Europe1";
        error = l_mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Continent Name is emplty!".equals(error));
    }
@After
    l_mapDisplay.formatMap(l_gameMap);
    // @Test
    // public void testAddNeighbor() {

    // }
    // @Test
    // public void testRemoveContinent() {

    // }

    // @Test
    // public void testRemoveCountry() {

    // }

    // @Test
    // public void testRemoveNeighbour() {

    // }

    // @Test
    // public void testValidateMap() {

    // }
}
