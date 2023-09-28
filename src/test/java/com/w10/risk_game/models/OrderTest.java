package com.w10.risk_game.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * This is a test class for Order
 */
public class OrderTest {
	Order d_order1;
	/**
	 * This method is to set up the test environment
	 *
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		List<Country> l_countries = new ArrayList<>();
		l_countries.add(new Country(1, "England", 1, 0));
		Player l_player1 = new Player("Player1", l_countries, new ArrayList<>(), 10);
		d_order1 = new Order(l_player1, "deploy", 1, 6);
	}
	/**
	 * This method is to test the method of deploy
	 */
	@Test
	@Disabled
	public void executeTest() {
		// Test deploy
		d_order1.execute();
		assertEquals(4, d_order1.getPlayer().getLeftoverArmies());
		assertEquals(6, d_order1.getPlayer().getCountriesOwned().get(0).getArmyCount());
	}
}
