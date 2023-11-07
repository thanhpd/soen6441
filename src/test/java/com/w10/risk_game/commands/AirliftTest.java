
package com.w10.risk_game.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AirliftTest {
	private Order d_order1;
	private Country country1;
	private Country country2;
	private Player player1;

	private int country1InitialArmyCount = 20;
	private int country2InitialArmyCount = 12;

	@BeforeEach
	public void setUp() {
		country1 = new Country(1, "England", 1, country1InitialArmyCount);
		country2 = new Country(2, "France", 1, country2InitialArmyCount);
		country1.addNeighbor(country2);
		country2.addNeighbor(country1);

		player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 10);

		ArrayList<Country> countries1 = new ArrayList<>() {
			{
				add(country1);
			}
		};
		ArrayList<Country> countries2 = new ArrayList<>() {
			{
				add(country2);
			}
		};
		// set country-1 and country-2 owner as player 1
		country1.setOwner(player1);
		player1.setCountriesOwned(countries1);

		country2.setOwner(player1);
		player1.setCountriesOwned(countries2);
	}

	@Test
	public void testAirliftExecution() {
		d_order1 = new Airlift(player1, "1", "2", "5");
		d_order1.execute();

		assertEquals(15, country1.getArmyCount());

	}
}
