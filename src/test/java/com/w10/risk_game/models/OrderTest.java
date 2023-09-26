package com.w10.risk_game.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * This is a test class for Order
 */
public class OrderTest {
	Order order1;
	/**
	 * This method is to set up the test environment
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		List<Country> countries = new ArrayList<>();
		countries.add(new Country(1, "England", 1));
		Player player1 = new Player("Player1", countries, new ArrayList<>(), 10);
		order1 = new Order(player1, "deploy", 1, 6);
	}
	/**
	 * This method is to test the method of deploy
	 */
	@Test
	public void deployTest() {
		// Test deploy
		order1.deploy();
		assertEquals(4, order1.getD_player().getLeftoverArmies());
		assertEquals(6, order1.getD_player().getCountriesOwned().get(0).getArmies());
	}
}
