package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.w10.risk_game.utils.Constants;
import java.util.ArrayList;
import java.util.List;

import com.w10.risk_game.utils.MapReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameEngineTest {
	private GameEngine d_gameEngine;
	Player d_player1;
	Player d_player2;

	Player d_player3;
	Player d_player4;
	List<Country> d_countries;
	List<Country> d_countries2;
	List<Country> d_countries3;
	MapReader d_mapReader = new MapReader();

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

		d_countries = new ArrayList<>();
		d_countries.add(new Country(1, "England", 1, 0));
		d_countries.add(new Country(2, "Scotland", 1, 0));
		d_countries.add(new Country(3, "N_Ireland", 1, 0));
		d_countries.add(new Country(4, "Rep_Ireland", 1, 0));
		d_countries.add(new Country(5, "Wales", 1, 0));
		d_countries.add(new Country(6, "Belgum", 1, 0));
		d_countries.add(new Country(7, "Netherlands", 1, 0));
		String l_playerName = "Player";
		List<Order> l_orders = new ArrayList<>();
		int l_armies = 10;
		d_player3 = new Player(l_playerName, d_countries, l_orders, l_armies);

		List<Country> l_countries1 = new ArrayList<>();
		l_countries1.add(new Country(1, "England", 1, 0));
		l_countries1.add(new Country(2, "Scotland", 1, 0));
		l_countries1.add(new Country(3, "N_Ireland", 1, 0));
		l_countries1.add(new Country(4, "Rep_Ireland", 1, 0));
		l_countries1.add(new Country(5, "Wales", 1, 0));
		l_countries1.add(new Country(6, "Belgum", 1, 0));
		String l_playerName1 = "Player1";
		List<Order> l_orders1 = new ArrayList<>();
		int l_armies1 = 10;
		d_player4 = new Player(l_playerName1, l_countries1, l_orders1, l_armies1);

		d_countries2 = new ArrayList<>();
		d_countries2.add(new Country(1, "England", 1, 0));
		d_countries2.add(new Country(2, "Scotland", 1, 0));
		d_countries2.add(new Country(3, "N_Ireland", 1, 0));
		d_countries2.add(new Country(4, "Rep_Ireland", 1, 0));
		d_countries2.add(new Country(5, "Wales", 1, 0));
		d_countries2.add(new Country(6, "Belgum", 1, 0));
		d_countries2.add(new Country(7, "Netherlands", 1, 0));
		d_countries2.add(new Country(8, "Denmark", 2, 0));
		d_countries2.add(new Country(9, "Germany", 2, 0));

		d_countries3 = new ArrayList<>();
		d_countries3.add(new Country(2, "Scotland", 1, 0));
		d_countries3.add(new Country(1, "England", 1, 0));
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

	@Test
	void testShowAllPlayers() {

	}

	@Test
	void testShowMap() {

	}

	/**
	 * The testgetNoOfPlayers() function tests the getNoOfPlayers() method in the
	 * d_gameEngine class to ensure that it returns the correct number of players.
	 *
	 * @author Sherwyn Dsouza
	 */
	@Test
	void testgetNoOfPlayers() {
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
	/**
	 * This method is to test reinforcementPhase method It tests the following
	 * cases: 1. Test bonus reinforcement 2. Test basic reinforcement
	 */
	@Test
	public void testReinforcementPhase() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		GameMap d_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		// Test bonus
		d_gameEngine.reinforcementPhase(d_player3, d_gameMap);
		assertEquals(17, d_player3.getLeftoverArmies());
		// Test basic
		d_gameEngine.reinforcementPhase(d_player4, d_gameMap);
		assertEquals(12, d_player4.getLeftoverArmies());
	}

	/**
	 * This method is to test groupCountries method It tests the following cases: 1.
	 * Test all countries belong to one continent 2. Test not all countries belong
	 * to one continent 3. Test order
	 */
	@Test
	public void testGroupCountry() {
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		GameMap d_gameMap = d_mapReader.loadMapFile(l_mapFilePath);
		// Test all countries belong to one continent
		List<String> l_groupCountries = d_gameEngine.groupCountries(d_countries, d_gameMap);
		assertEquals("1 2 3 4 5 6 7", l_groupCountries.get(0));
		// Test not all countries belong to one continent
		List<String> l_groupCountries1 = d_gameEngine.groupCountries(d_countries2, d_gameMap);
		assertEquals("8 9", l_groupCountries1.get(1));
		// Test order
		List<String> l_groupCountries2 = d_gameEngine.groupCountries(d_countries3, d_gameMap);
		assertEquals("1 2", l_groupCountries2.get(0));
	}
}
