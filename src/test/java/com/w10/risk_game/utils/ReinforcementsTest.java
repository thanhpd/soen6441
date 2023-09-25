package com.w10.risk_game.utils;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Order;
import com.w10.risk_game.models.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This is a test class for Reinforcements It will test the following cases: 1.
 * Test bonus reinforcement 2. Test basic reinforcement 3. Test group countries
 * by continent (all belong to one continent, not all belong to one continent,
 * order
 *
 * @author Yajing LIU
 */
public class ReinforcementsTest {
	Player player;
	Player player1;
	List<Country> countries;
	List<Country> countries2;
	List<Country> countries3;
	/**
	 * This method is to set up the test environment
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		countries = new ArrayList<>();
		countries.add(new Country(1, "England", 1));
		countries.add(new Country(2, "Scotland", 1));
		countries.add(new Country(3, "N_Ireland", 1));
		countries.add(new Country(4, "Rep_Ireland", 1));
		countries.add(new Country(5, "Wales", 1));
		countries.add(new Country(6, "Belgum", 1));
		countries.add(new Country(7, "Netherlands", 1));
		String playerName = "Player";
		List<Order> orders = new ArrayList<>();
		int armies = 10;
		player = new Player(playerName, countries, orders, armies);

		List<Country> countries1 = new ArrayList<>();
		countries1.add(new Country(1, "England", 1));
		countries1.add(new Country(2, "Scotland", 1));
		countries1.add(new Country(3, "N_Ireland", 1));
		countries1.add(new Country(4, "Rep_Ireland", 1));
		countries1.add(new Country(5, "Wales", 1));
		countries1.add(new Country(6, "Belgum", 1));
		String playerName1 = "Player1";
		List<Order> orders1 = new ArrayList<>();
		int armies1 = 10;
		player1 = new Player(playerName1, countries1, orders1, armies1);

		countries2 = new ArrayList<>();
		countries2.add(new Country(1, "England", 1));
		countries2.add(new Country(2, "Scotland", 1));
		countries2.add(new Country(3, "N_Ireland", 1));
		countries2.add(new Country(4, "Rep_Ireland", 1));
		countries2.add(new Country(5, "Wales", 1));
		countries2.add(new Country(6, "Belgum", 1));
		countries2.add(new Country(7, "Netherlands", 1));
		countries2.add(new Country(8, "Denmark", 2));
		countries2.add(new Country(9, "Germany", 2));

		countries3 = new ArrayList<>();
		countries3.add(new Country(2, "Scotland", 1));
		countries3.add(new Country(1, "England", 1));
	}
	/**
	 * This method is to test reinforcementPhase method
	 */
	@Test
	public void testReinforcementPhase() {
		// Test bonus
		// Reinforcements reinforcement = new Reinforcements();
		// reinforcement.reinforcementPhase(player);
		// assertEquals(17, player.getLeftoverArmies());
		// Test basic
		Reinforcements reinforcement1 = new Reinforcements();
		reinforcement1.reinforcementPhase(player1);
		assertEquals(12, player1.getLeftoverArmies());
	}
	/**
	 * This method is to test groupCountries method
	 */
	@Test
	public void testGroupCountry() {
		// Test all countries belong to one continent
		Reinforcements reinforcement = new Reinforcements();
		List<String> groupCountries = reinforcement.groupCountries(countries);
		assertEquals("1 2 3 4 5 6 7", groupCountries.get(0));
		// Test not all countries belong to one continent
		Reinforcements reinforcement1 = new Reinforcements();
		List<String> groupCountries1 = reinforcement1.groupCountries(countries2);
		assertEquals("8 9", groupCountries1.get(1));
		// Test order
		Reinforcements reinforcement2 = new Reinforcements();
		List<String> groupCountries2 = reinforcement2.groupCountries(countries3);
		assertEquals("1 2", groupCountries2.get(0));
	}
}
