package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The GameEngineTest class contains unit tests for various methods in the
 * GameEngine class.
 */
public class GameEngineTest {
	private GameEngine d_gameEngine;
	private ByteArrayOutputStream outputStream;
	Player d_player1;
	Player d_player2;

	/**
	 * The function "beforeAllGameEngineTests" initializes a new instance of the
	 * GameEngine class and creates two Player objects with specific names and
	 * attributes.
	 *
	 * @author Sherwyn Dsouza
	 */
	@BeforeEach
	public void beforeAllGameEngineTests() {
		d_gameEngine = new GameEngine();
		d_player1 = new Player("TestPlayerName1", new ArrayList<Country>(), null, 0);
		d_player2 = new Player("TestPlayerName2", new ArrayList<Country>(), null, 0);

		outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
	}

	/**
	 * The testAssignCountries function tests whether the assignCountries method
	 * correctly assigns countries to players in the game engine.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testAssignCountries() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_gameEngine.loadMap(l_mapFilePath);

		d_gameEngine.createPlayer(d_player1.getName());
		d_gameEngine.createPlayer(d_player2.getName());

		assertEquals(0, d_gameEngine.getPlayerDetails(d_player1.getName()).getCountriesOwned().size());
		assertEquals(0, d_gameEngine.getPlayerDetails(d_player2.getName()).getCountriesOwned().size());

		d_gameEngine.assignCountries();

		assertEquals(12, d_gameEngine.getPlayerDetails(d_player1.getName()).getCountriesOwned().size());
		assertEquals(12, d_gameEngine.getPlayerDetails(d_player2.getName()).getCountriesOwned().size());
	}

	/**
	 * The testCreatePlayer function tests the creation of players in the game
	 * engine and checks if the number of players is updated correctly.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testCreatePlayer() {
		d_gameEngine.createPlayer(d_player1.getName());
		d_gameEngine.createPlayer(d_player2.getName());
		assertEquals(2, d_gameEngine.getNoOfPlayers());

		d_gameEngine.createPlayer(d_player2.getName());
		assertEquals(2, d_gameEngine.getNoOfPlayers());
	}

	/**
	 * The testLoadMap function tests whether the d_gameEngine successfully loads
	 * the "europe.map" file.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testLoadMap() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_gameEngine.loadMap(l_mapFilePath);
		assertNotNull(d_gameEngine.getGameMap());
	}

	/**
	 * The testRemovePlayer function tests the functionality of removing players
	 * from the game engine.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testRemovePlayer() {
		d_gameEngine.createPlayer(d_player1.getName());
		d_gameEngine.createPlayer(d_player2.getName());

		d_gameEngine.removePlayer(d_player2.getName());
		assertEquals(1, d_gameEngine.getNoOfPlayers());

		d_gameEngine.removePlayer(d_player1.getName());
		assertEquals(0, d_gameEngine.getNoOfPlayers());
	}

	/**
	 * The testShowAllPlayers function tests the showAllPlayers method by
	 * capturing the output and comparing it to an expected output.
	 */
	@Test
	void testShowAllPlayers() {
		d_gameEngine.showAllPlayers();
		// Capture the actual output
		String l_actualOutput = outputStream.toString().trim();
		String l_expectedOutput = "";
		assertEquals(l_expectedOutput, l_actualOutput);
	}

	/**
	 * The testShowMap function tests if the game engine can display the map
	 */
	@Test
	void testShowMap() {
		d_gameEngine.showMap();
		assertTrue(d_gameEngine.checkIfGameCanBegin() == d_gameEngine.checkIfMapIsValid());
	}

	/**
	 * The testGetNoOfPlayers() function tests the getNoOfPlayers() method in the
	 * d_gameEngine class to ensure that it returns the correct number of players.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testGetNoOfPlayers() {
		d_gameEngine.createPlayer(d_player1.getName());
		d_gameEngine.createPlayer(d_player2.getName());

		assertEquals(2, d_gameEngine.getNoOfPlayers());
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
		d_gameEngine.loadMap(l_mapFilePath);
		assertNotNull(d_gameEngine.getGameMap());
	}

	/**
	 * The getPlayerDetailsTest function tests if the getPlayerDetails method in the
	 * game engine returns the correct player name.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void getPlayerDetailsTest() {
		d_gameEngine.createPlayer(d_player1.getName());

		assertEquals(d_player1.getName(), d_gameEngine.getPlayerDetails(d_player1.getName()).getName());
	}
}
