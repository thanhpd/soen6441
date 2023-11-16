package com.w10.risk_game.utils.maps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.w10.risk_game.controllers.MapEditorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.Constants;

/**
 * The MapValidatorTest class contains multiple test cases to validate the
 * correctness of a game map.
 */
public class MapValidatorTest {
	private MapEditorController d_mapEditorController;

	/**
	 * The setUp() function is used to initialize the MapEditorController object
	 * before each test case.
	 */
	@BeforeEach
	public void setUp() {
		d_mapEditorController = new MapEditorController();
	}

	/**
	 * The testMapCorrectness function tests whether the loaded map file is correct
	 * using the MapValidator class.
	 */
	@Test
	public void testMapCorrectness() {
		// Domination map
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertTrue(MapValidator.IsMapCorrect(l_gameMap));

		// Conquest map
		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe-conquest.map";
		d_mapEditorController.loadMap(l_mapFilePath2);
		GameMap l_gameMap2 = d_mapEditorController.getGameMap();
		assertTrue(MapValidator.IsMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapWithIsolatedCountries function tests whether a map with isolated
	 * countries is correctly identified as incorrect.
	 */
	@Test
	public void testMapWithIsolatedCountries() {
		// Domination map
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-connected.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));

		// Conquest map
		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-connected-conquest.map";
		d_mapEditorController.loadMap(l_mapFilePath2);
		GameMap l_gameMap2 = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapWithContinents function tests whether a map that has a continent
	 * with no countries is correctly loaded and marked as incorrect.
	 */
	@Test
	public void testMapWithContinents() {
		// Domination map
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-continents-no-country.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));

		// Conquest map
		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-continents-no-country-conquest.map";
		d_mapEditorController.loadMap(l_mapFilePath2);
		GameMap l_gameMap2 = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapEmpty function tests if some prepared empty maps are recognized as
	 * invalid.
	 */
	@Test
	public void testMapEmpty() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-empty.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));

		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-empty2.map";
		d_mapEditorController.loadMap(l_mapFilePath2);
		GameMap l_gameMap2 = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapHasNonExistentContinent function checks if a map file with a
	 * non-existent continent will be marked as incorrect
	 */
	@Test
	public void testMapHasNonExistentContinent() {
		// Domination map
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-continents-exist.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));

		// Conquest map
		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-continents-exist-conquest.map";
		d_mapEditorController.loadMap(l_mapFilePath2);
		GameMap l_gameMap2 = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap2));
	}

	/**
	 * The testMapHasNonExistentCountry function checks if a map file with
	 * non-existent countries is incorrect.
	 */
	@Test
	public void testMapHasNonExistentCountry() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-countries-exist.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));
	}

	/**
	 * The function tests whether a map has a self-referencing neighbor and expects
	 * the result to be false.
	 */
	@Test
	public void testMapHasSelfReferencingNeighbor() {
		// Domination map
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-self-referencing.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		GameMap l_gameMap = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap));

		// Conquest map
		String l_mapFilePath2 = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-self-referencing-conquest.map";
		d_mapEditorController.loadMap(l_mapFilePath2);
		GameMap l_gameMap2 = d_mapEditorController.getGameMap();
		assertFalse(MapValidator.IsMapCorrect(l_gameMap2));
	}

}
