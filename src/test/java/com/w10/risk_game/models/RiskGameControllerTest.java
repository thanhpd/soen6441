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
public class RiskGameControllerTest {
	private RiskGameController d_RiskGameController;
	private MapEditorController d_EditorController;
	private ByteArrayOutputStream d_outputStream;
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
		d_RiskGameController = new RiskGameController();
		d_EditorController = new MapEditorController();
		d_player1 = new Player("TestPlayerName1", new ArrayList<Country>(), null, 0);
		d_player2 = new Player("TestPlayerName2", new ArrayList<Country>(), null, 0);

		d_outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(d_outputStream));
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
		d_EditorController.loadMap(l_mapFilePath);

		d_RiskGameController.createPlayer(d_player1.getName());
		d_RiskGameController.createPlayer(d_player2.getName());

		assertEquals(0, d_RiskGameController.getPlayerDetails(d_player1.getName()).getCountriesOwned().size());
		assertEquals(0, d_RiskGameController.getPlayerDetails(d_player2.getName()).getCountriesOwned().size());

		d_RiskGameController.assignCountries();

		assertEquals(0, d_RiskGameController.getPlayerDetails(d_player1.getName()).getCountriesOwned().size());
		assertEquals(0, d_RiskGameController.getPlayerDetails(d_player2.getName()).getCountriesOwned().size());
	}

	/**
	 * The testCreatePlayer function tests the creation of players in the game
	 * engine and checks if the number of players is updated correctly.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testCreatePlayer() {
		d_RiskGameController.createPlayer(d_player1.getName());
		d_RiskGameController.createPlayer(d_player2.getName());
		assertEquals(2, d_RiskGameController.getNoOfPlayers());

		d_RiskGameController.createPlayer(d_player2.getName());
		assertEquals(2, d_RiskGameController.getNoOfPlayers());
	}



	/**
	 * The testRemovePlayer function tests the functionality of removing players
	 * from the game engine.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testRemovePlayer() {
		d_RiskGameController.createPlayer(d_player1.getName());
		d_RiskGameController.createPlayer(d_player2.getName());

		d_RiskGameController.removePlayer(d_player2.getName());
		assertEquals(1, d_RiskGameController.getNoOfPlayers());

		d_RiskGameController.removePlayer(d_player1.getName());
		assertEquals(0, d_RiskGameController.getNoOfPlayers());
	}

	/**
	 * The testShowAllPlayers function tests the showAllPlayers method by capturing
	 * the output and comparing it to an expected output.
	 */
	@Test
	void testShowAllPlayers() {
		d_RiskGameController.showAllPlayers();
		// Capture the actual output
		String l_actualOutput = d_outputStream.toString().trim();
		String l_expectedOutput = "";
		assertEquals(l_expectedOutput, l_actualOutput);
	}

	

	/**
	 * The testGetNoOfPlayers() function tests the getNoOfPlayers() method in the
	 * d_RiskGameController class to ensure that it returns the correct number of
	 * players.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testGetNoOfPlayers() {
		d_RiskGameController.createPlayer(d_player1.getName());
		d_RiskGameController.createPlayer(d_player2.getName());

		assertEquals(2, d_RiskGameController.getNoOfPlayers());
	}

	

	/**
	 * The getPlayerDetailsTest function tests if the getPlayerDetails method in the
	 * game engine returns the correct player name.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void getPlayerDetailsTest() {
		d_RiskGameController.createPlayer(d_player1.getName());

		assertEquals(d_player1.getName(), d_RiskGameController.getPlayerDetails(d_player1.getName()).getName());
	}
}
