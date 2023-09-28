package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.GameMap;

/**
 * The MapValidatorTest class contains multiple test cases to validate the
 * correctness of a game map.
 */
public class MapValidatorTest {
	MapReader d_mapReader = new MapReader();

	/**
	 * The testMapCorrectness function tests whether the loaded map file is correct
	 * using the MapValidator class.
	 */
	@Test
	public void testMapCorrectness() {
		GameMap l_gameMap = d_mapReader.loadMapFile("europe.map");
		assertTrue(MapValidator.isMapCorrect(l_gameMap));
	}

	/**
	 * The testMapWithIsolatedCountries function tests whether a map with isolated
	 * countries is correctly identified as incorrect.
	 */
	@Test
	public void testMapWithIsolatedCountries() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-connected.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	/**
	 * The testMapWithContinents function tests whether a map that has a continent
	 * with no countries is correctly loaded and marked as incorrect.
	 */
	@Test
	public void testMapWithContinents() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-continents-no-country.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	/**
	 * The testMapEmpty function tests if some prepared empty maps are recognized as
	 * invalid.
	 */
	@Test
	public void testMapEmpty() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-empty.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));

		GameMap l_gameMap2 = d_mapReader.loadMapFile("test-empty2.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapHasNonExistentContinent function checks if a map file with a
	 * non-existent continent will be marked as incorrect
	 */
	@Test
	public void testMapHasNonExistentContinent() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-continents-exist.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	/**
	 * The testMapHasNonExistentCountry function checks if a map file with
	 * non-existent countries is incorrect.
	 */
	@Test
	public void testMapHasNonExistentCountry() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-countries-exist.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	/**
	 * The function tests whether a map has a self-referencing neighbor and expects
	 * the result to be false.
	 */
	@Test
	public void testMapHasSelfReferencingNeighbor() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-self-referencing.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

}
