package com.w10.risk_game.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The GameEngineControllerTest class contains unit test for various methods in
 * the GameEngineControllerTest class.
 */
class GameEngineControllerTest {
	private GameEngineController d_gameEngineController;
	private MapEditorController d_mapEditorController;
	private ByteArrayOutputStream d_outputStream;
	Player d_player1;
	Player d_player2;

	/**
	 * The function "beforeAllGameEngineTests" initializes a new instance of the
	 * GameEngine class and creates two Player objects with specific names and
	 * attributes.
	 *
	 */
	@BeforeEach
	public void beforeAllGameEngineTests() {
		d_mapEditorController = new MapEditorController();
		d_gameEngineController = new GameEngineController(d_mapEditorController);
		d_player1 = new Player("TestPlayerName1", new ArrayList<Country>(), null, 0);
		d_player2 = new Player("TestPlayerName2", new ArrayList<Country>(), null, 0);

		d_outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(d_outputStream));
	}

	/**
	 * The testAssignCountries function tests whether the assignCountries method
	 * correctly assigns countries to players in the game engine.
	 *
	 */
	@Test
	void testAssignCountries() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_mapEditorController.loadMap(l_mapFilePath);

		
		d_gameEngineController.createPlayer(d_player1.getName());
		d_gameEngineController.createPlayer(d_player2.getName());

		assertEquals(0, d_gameEngineController.getPlayerDetails(d_player1.getName()).getCountriesOwned().size());
		assertEquals(0, d_gameEngineController.getPlayerDetails(d_player2.getName()).getCountriesOwned().size());

		d_gameEngineController.assignCountries();

		assertEquals(12, d_gameEngineController.getPlayerDetails(d_player1.getName()).getCountriesOwned().size());
		assertEquals(12, d_gameEngineController.getPlayerDetails(d_player2.getName()).getCountriesOwned().size());
	}

	/**
	 * The testCreatePlayer function tests the creation of players in the game
	 * engine and checks if the number of players is updated correctly.
	 *
	 */
	@Test
	void testCreatePlayer() {
		d_gameEngineController.createPlayer(d_player1.getName());
		d_gameEngineController.createPlayer(d_player2.getName());
		assertEquals(2, d_gameEngineController.getNoOfPlayers());

		d_gameEngineController.createPlayer(d_player2.getName());
		assertEquals(2, d_gameEngineController.getNoOfPlayers());
	}

	/**
	 * The testRemovePlayer function tests the functionality of removing players
	 * from the game engine.
	 *
	 */
	@Test
	void testRemovePlayer() {
		d_gameEngineController.createPlayer(d_player1.getName());
		d_gameEngineController.createPlayer(d_player2.getName());

		d_gameEngineController.removePlayer(d_player2.getName());
		assertEquals(1, d_gameEngineController.getNoOfPlayers());

		d_gameEngineController.removePlayer(d_player1.getName());
		assertEquals(0, d_gameEngineController.getNoOfPlayers());
	}

	/**
	 * The testShowAllPlayers function tests the showAllPlayers method by capturing
	 * the output and comparing it to an expected output.
	 */
	@Test
	void testShowAllPlayers() {
		d_gameEngineController.showAllPlayers();
		// Capture the actual output
		String l_actualOutput = d_outputStream.toString().trim();
		String l_expectedOutput = "";
		assertEquals(l_expectedOutput, l_actualOutput);
	}

	/**
	 * The testGetNoOfPlayers() function tests the getNoOfPlayers() method in the
	 * d_gameEngineController class to ensure that it returns the correct number of
	 * players.
	 *
	 */
	@Test
	void testGetNoOfPlayers() {
		d_gameEngineController.createPlayer(d_player1.getName());
		d_gameEngineController.createPlayer(d_player2.getName());

		assertEquals(2, d_gameEngineController.getNoOfPlayers());
	}

	/**
	 * The getPlayerDetailsTest function tests if the getPlayerDetails method in the
	 * game engine returns the correct player name.
	 *
	 */
	@Test
	void testGetPlayerDetails() {
		d_gameEngineController.createPlayer(d_player1.getName());
		assertEquals(d_player1.getName(), d_gameEngineController.getPlayerDetails(d_player1.getName()).getName());
	}

	/**
	 * The testGameCompleted function tests whether the game is completed by
	 * simulating a game scenario and checking if the game is over.
	 */
	@Test
	void testGameCompleted() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test-win.map";
		d_mapEditorController.loadMap(l_mapFilePath);

		d_gameEngineController.createPlayer(d_player1.getName());
		d_gameEngineController.createPlayer(d_player2.getName());

		d_gameEngineController.assignCountries();

		Country l_countryOfPlayer1 = d_gameEngineController.getPlayerDetails(d_player1.getName()).getCountriesOwned()
				.get(0);
		Country l_countryOfPlayer2 = d_gameEngineController.getPlayerDetails(d_player2.getName()).getCountriesOwned()
				.get(0);

		d_gameEngineController.assignPlayersReinforcements();

		// Round 1 - Player A advances to Player B country and loses
		GameEngine.Command = MessageFormat.format("deploy {0} 3", l_countryOfPlayer1.getCountryId());
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = MessageFormat.format("deploy {0} 3", l_countryOfPlayer2.getCountryId());
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = MessageFormat.format("advance {0} {1} 3", l_countryOfPlayer1.getCountryName(),
				l_countryOfPlayer2.getCountryName());
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT;
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT;
		d_gameEngineController.issuePlayerOrder();

		d_gameEngineController.executePlayerOrders();

		assertFalse(d_gameEngineController.checkIfGameIsOver());

		// Round 2 - Player B advances to Player A country and wins
		d_gameEngineController.assignPlayersReinforcements();

		GameEngine.Command = MessageFormat.format("deploy {0} 3", l_countryOfPlayer1.getCountryId());
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = MessageFormat.format("deploy {0} 3", l_countryOfPlayer2.getCountryId());
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT;
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = MessageFormat.format("advance {0} {1} 4", l_countryOfPlayer2.getCountryName(),
				l_countryOfPlayer1.getCountryName());
		d_gameEngineController.issuePlayerOrder();

		GameEngine.Command = Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT;
		d_gameEngineController.issuePlayerOrder();

		d_gameEngineController.executePlayerOrders();

		assertTrue(d_gameEngineController.checkIfGameIsOver());
	}
}
