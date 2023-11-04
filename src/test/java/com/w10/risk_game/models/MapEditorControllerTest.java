package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.controllers.RiskGameController;
import com.w10.risk_game.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The GameEngineTest class contains unit test for various methods in the
 * GameEngine class.
 */
public class MapEditorControllerTest {
	private RiskGameController d_RiskGameController;
	private MapEditorController d_EditorController;
	private ByteArrayOutputStream d_outputStream;
	// Player d_player1;
	// Player d_player2;

	/**
	 * The function "beforeAllGameEngineTests" initializes a new instance of the
	 * GameEngine class and creates two Player objects with specific names and
	 * attributes.
	 *
	 */
	@BeforeEach
	public void beforeAllGameEngineTests() {

		d_EditorController = new MapEditorController();

		d_outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(d_outputStream));
	}

	/**
	 * The testLoadMap function tests whether the d_RiskGame successfully loads the
	 * "europe.map" file.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testLoadMap() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_EditorController.loadMap(l_mapFilePath);
		assertNotNull(d_EditorController.getGameMap());
	}

	/**
	 * The testShowMap function tests if the game engine can display the map
	 */
	@Test
	void testShowMap() {
		d_EditorController.showMap();
		assertTrue(d_RiskGameController.checkIfGameCanBegin() == d_EditorController.checkIfMapIsValid());
	}

	/**
	 * The testGameMap function tests if the game engine successfully loads a map
	 * and returns a non-null game map object.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testGameMap() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_EditorController.loadMap(l_mapFilePath);
		assertNotNull(d_RiskGameController.getGameMap());
	}

}
