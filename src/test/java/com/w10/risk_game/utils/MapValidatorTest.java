package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.GameMap;

/**
 * The MapValidatorTest class contains multiple test cases to validate the
 * correctness of a game map.
 */
public class MapValidatorTest {
	private final DominationMapReader d_mapReader = new DominationMapReader();

	/**
	 * The testMapCorrectness function tests whether the loaded map file is correct
	 * using the MapValidator class.
	 */
	@Test
	public void testMapCorrectness() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertTrue(MapValidator.IsMapCorrect(l_gameMap));
	}

	/**
	 * The testMapWithIsolatedCountries function tests whether a map with isolated
	 * countries is correctly identified as incorrect.
	 */
	@Test
	public void testMapWithIsolatedCountries() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-connected.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));
	}

	/**
	 * The testMapWithContinents function tests whether a map that has a continent
	 * with no countries is correctly loaded and marked as incorrect.
	 */
	@Test
	public void testMapWithContinents() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-continents-no-country.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));
	}

	/**
	 * The testMapEmpty function tests if some prepared empty maps are recognized as
	 * invalid.
	 */
	@Test
	public void testMapEmpty() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-empty.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));

		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-empty2.map";
		GameMap l_gameMap2 = d_mapReader.loadMapFile(l_mapFilePath2);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapHasNonExistentContinent function checks if a map file with a
	 * non-existent continent will be marked as incorrect
	 */
	@Test
	public void testMapHasNonExistentContinent() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-continents-exist.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));
	}

	/**
	 * The testMapHasNonExistentCountry function checks if a map file with
	 * non-existent countries is incorrect.
	 */
	@Test
	public void testMapHasNonExistentCountry() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-countries-exist.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));
	}

	/**
	 * The function tests whether a map has a self-referencing neighbor and expects
	 * the result to be false.
	 */
	@Test
	public void testMapHasSelfReferencingNeighbor() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-self-referencing.map";
		GameMap l_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));
	}

}
