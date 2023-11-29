package com.w10.risk_game.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.w10.risk_game.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The GameEngineTest class contains unit test for various methods in the
 * GameEngine class.
 *
 * @author Omnia Alam
 */
class MapEditorControllerTest {
	private GamePlayController d_gamePlayController;
	private MapEditorController d_mapEditorController;
	private ByteArrayOutputStream d_outputStream;

	/**
	 * The function "beforeAllGameEngineTests" initializes a new instance of the
	 * GameEngine class and creates two Player objects with specific names and
	 * attributes.
	 *
	 */
	@BeforeEach
	public void beforeAllGameEngineTests() {
		d_mapEditorController = new MapEditorController();
		d_gamePlayController = new GamePlayController(d_mapEditorController);
		d_outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(d_outputStream));
	}

	/**
	 * The testLoadMap function tests whether the d_RiskGame successfully loads the
	 * "europe.map" file.
	 */
	@Test
	void testLoadMap() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		assertNotNull(d_mapEditorController.getGameMap());
	}

	/**
	 * The testShowMap function tests if the game engine can display the map
	 */
	@Test
	void testShowMap() {
		d_mapEditorController.showMap(false);
		assertTrue(d_gamePlayController.checkIfGameCanBegin() == d_mapEditorController.checkIfMapIsValid());
	}

	/**
	 * The testGameMap function tests if the game engine successfully loads a map
	 * and returns a non-null game map object.
	 *
	 */
	@Test
	void testGameMap() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		assertNotNull(d_mapEditorController.getGameMap());
	}

}
