package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The PlayerTest class is a JUnit test class that tests the functionality of
 * the Player class in a game.
 *
 * @author Darlene-Naz, Yajing Liu
 */
public class PlayerTest {
	private Player d_player;
	private Player d_player1;
	List<Country> d_countries;

	/**
	 * The function sets up a Player object with a specific name, no territories,
	 * and a starting army count of 10 for testing purposes.
	 */
	@BeforeEach
	public void beforeAllPlayerTests() {
		d_player = new Player("TestPlayerName", new ArrayList<Country>(), List.of(), 10);

		d_countries = new ArrayList<>();
		d_countries.add(new Country(1, "England", 1, 0));
		d_countries.add(new Country(2, "Scotland", 1, 0));
		d_player1 = new Player("Player1", d_countries, new ArrayList<>(), 10);
	}

	/**
	 * The testAddArmies function tests the addArmies method of the player object by
	 * adding 2 armies and checking if the leftover armies is equal to 12.
	 */
	@Test
	public void testAddArmies() {
		d_player.addArmies(2);
		assertEquals(12, d_player.getLeftoverArmies());
	}

	/**
	 * The testDeployArmies function tests the deployArmies method of the player
	 * object by asserting that the leftoverArmies value is 7 after deploying 3
	 * armies.
	 */
	@Test
	public void testDeployArmies() {
		d_player.deployArmies(3);
		assertEquals(7, d_player.getLeftoverArmies());
	}
}
