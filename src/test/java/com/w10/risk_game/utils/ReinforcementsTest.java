package com.w10.risk_game.utils;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a test class for Reinforcements It will test the following cases: 1.
 * Test bonus reinforcement 2. Test basic reinforcement 3. Test group countries
 * by continent (all belong to one continent, not all belong to one continent,
 * order
 *
 * @author Yajing LIU
 */
public class ReinforcementsTest {
	Player d_player;
	Player d_player1;
	List<Country> d_countries;
	List<Country> d_countries2;
	List<Country> d_countries3;
	DominationMapReader d_mapReader = new DominationMapReader();

	/**
	 * This method is to set up the test environment
	 *
	 */
	@BeforeEach
	public void setUp() {
		// Step 1: Initialize the variables for bonus calculation
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
		d_player = new Player(l_playerName, d_countries, l_orders, l_armies);
		// Step 2: Initialize the variables for basic calculation
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
		d_player1 = new Player(l_playerName1, l_countries1, l_orders1, l_armies1);
		// Step 3: Initialize the variables for group countries
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
		// Step 4: Initialize the variables for order
		d_countries3 = new ArrayList<>();
		d_countries3.add(new Country(2, "Scotland", 1, 0));
		d_countries3.add(new Country(1, "England", 1, 0));
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
		Reinforcements.AssignPlayerReinforcements(d_player, d_gameMap);
		assertEquals(17, d_player.getLeftoverArmies());
		// Test basic
		Reinforcements.AssignPlayerReinforcements(d_player1, d_gameMap);
		assertEquals(12, d_player1.getLeftoverArmies());
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
		List<String> l_groupCountries = Reinforcements.GroupCountries(d_countries, d_gameMap);
		assertEquals("1 2 3 4 5 6 7", l_groupCountries.get(0));
		// Test not all countries belong to one continent
		List<String> l_groupCountries1 = Reinforcements.GroupCountries(d_countries2, d_gameMap);
		assertEquals("8 9", l_groupCountries1.get(1));
		// Test order
		List<String> l_groupCountries2 = Reinforcements.GroupCountries(d_countries3, d_gameMap);
		assertEquals("1 2", l_groupCountries2.get(0));
	}
}
