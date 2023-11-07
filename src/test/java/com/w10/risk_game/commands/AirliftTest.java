package com.w10.risk_game.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Airlift command.
 */
public class AirliftTest {
	private Order d_order1;
	private Country country1;
	private Country country2;
	private Player player1;

	private int country1InitialArmyCount = 20;
	private int country2InitialArmyCount = 12;

	/**
	 * Set up initial test conditions.
	 */
	@BeforeEach
	public void setUp() {
		// Create two countries
		country1 = new Country(1, "England", 1, country1InitialArmyCount);
		country2 = new Country(2, "France", 1, country2InitialArmyCount);

		// Create a player
		player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 10);

		// Create a list of countries and assign them to the player
		ArrayList<Country> countries1 = new ArrayList<>() {
			{
				add(country1);
				add(country2);
			}
		};

		// Set country-1 and country-2 owner as player 1
		country1.setOwner(player1);
		player1.setCountriesOwned(countries1);
	}

	/**
	 * The testAirliftExecution function creates an Airlift order, executes it, and
	 * checks if the
	 * number of armies in country1 has been updated as expected.
	 */
	@Test
	public void testAirliftExecution() {
		// Create an Airlift order
		d_order1 = new Airlift(player1, "1", "2", "5");
		// Execute the Airlift order
		d_order1.execute();

		// Check if the number of armies in country1 has been updated as expected
		assertEquals(15, country1.getArmyCount());
	}
}
