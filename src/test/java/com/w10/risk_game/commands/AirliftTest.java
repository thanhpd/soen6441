package com.w10.risk_game.commands;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Airlift command.
 */
public class AirliftTest {
	private Order d_order1;
	private Country d_country1;
	private Country d_country2;
	private Player d_player1;

	private int d_country1InitialArmyCount = 20;
	private int d_country2InitialArmyCount = 12;

	/**
	 * Set up initial test conditions.
	 */
	@BeforeEach
	public void setUp() {
		// Create two countries
		d_country1 = new Country(1, "England", 1, d_country1InitialArmyCount);
		d_country2 = new Country(2, "France", 1, d_country2InitialArmyCount);

		// Create a player
		d_player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 10);

		// Create a list of countries and assign them to the player
		ArrayList<Country> countries1 = new ArrayList<>();
		countries1.add(d_country1);
		countries1.add(d_country2);

		// Set country-1 and country-2 owner as player 1
		d_country1.setOwner(d_player1);
		d_player1.setCountriesOwned(countries1);
	}

	/**
	 * The testAirliftExecution function creates an Airlift order, executes it, and
	 * then checks if the army counts of two countries have been updated correctly.
	 */
	@Test
	public void testAirliftExecution() {
		// Create an Airlift order
		d_order1 = new Airlift(d_player1, "1", "2", "5");
		// Execute the Airlift order
		d_order1.execute();

		assertEquals(15, d_country1.getArmyCount());
		assertEquals(17, d_country2.getArmyCount());

	}
}
