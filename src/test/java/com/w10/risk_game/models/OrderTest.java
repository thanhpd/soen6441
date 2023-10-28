package com.w10.risk_game.models;

import com.w10.risk_game.command.Deploy;
import com.w10.risk_game.command.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * This is a test class for Order
 *
 * @author Yajing Liu
 */
public class OrderTest {
	Order d_order1;
	Player d_player1;
	/**
	 * This method is to set up the test environment
	 *
	 */
	@BeforeEach
	public void setUp() {
		List<Country> l_countries = new ArrayList<>();
		l_countries.add(new Country(1, "England", 1, 0));
		d_player1 = new Player("Player1", l_countries, new ArrayList<>(), 10);
		d_order1 = new Deploy(d_player1, 1, 6);
	}
	/**
	 * This method is to test the method of deploy
	 */
	@Test
	public void executeTest() {
		// Test deploy
		d_order1.execute();
		assertEquals(6, d_player1.getCountriesOwned().get(0).getArmyCount());
	}
}
