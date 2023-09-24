package com.w10.risk_game.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.w10.risk_game.models.GameMap;

public class MapEditorTest {
    private GameMap gameMap;
    private MapEditor mapEditor;

    @Before
    public void setUp() {
        MapReader mapReader = new MapReader();
        gameMap = mapReader.loadMapFile("test.map");
        mapEditor = new MapEditor(gameMap);

    }

    @Test
    public void testAddContinent() {
        int p_continentId = 1;
        String p_continentName = "North_Europe1";
        String error = mapEditor.addContinent(p_continentId, p_continentName);
        assertTrue("Continent id already exists!".equals(error));
        assertEquals("Continent id already exists!", error);
        p_continentId = 2;
        p_continentName = "North_Europe";
        error = mapEditor.addContinent(p_continentId, p_continentName);
        assertTrue("Continent name already exists!".equals(error));
        assertEquals("Continent name already exists!", error);
    }

    @Test
    public void testAddCounrty() {
        int p_countryId = 25;
        String p_countryName = "England";
        String p_continentName = "North_Europe";
        String error = mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Country name already exists!".equals(error));
        p_countryId = 1;
        p_countryName = "England1";
        p_continentName = "North_Europe";
        error = mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Country ID already exists!".equals(error));
        p_countryId = 25;
        p_countryName = "England1";
        p_continentName = "";
        error = mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Continent Name is emplty!".equals(error));
        p_countryId = 25;
        p_countryName = "England1";
        p_continentName = "North_Europe1";
        error = mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Continent doesnot exists!".equals(error));

        /*Failed Case */
        p_countryId = 25;
        p_countryName = "England1";
        p_continentName = "North_Europe1";
        error = mapEditor.addCounrty(p_countryId, p_countryName, p_continentName);
        assertTrue("Continent Name is emplty!".equals(error));
    }

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
