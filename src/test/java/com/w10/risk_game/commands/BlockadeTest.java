package com.w10.risk_game.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

/**
 * This is a test class for blockade card
 *
 */
public class BlockadeTest {
	private Order d_order1;
	private Country d_country1;
	private Country d_country2;
	private Player d_player1;
	private Player d_player2;

	private final int d_country1ArmyCount = 20;
	private final int d_country2ArmyCount = 8;

	/**
	 * The setUp function initializes two countries (England and France) with their
	 * respective army counts and adds each other as neighbors, and also initializes
	 * two players (Player1 and Player2).
	 */
	@BeforeEach
	public void setUp() {
		d_country1 = new Country(1, "England", 1, d_country1ArmyCount);
		d_country2 = new Country(2, "France", 1, d_country2ArmyCount);
		d_country1.addNeighbor(d_country2);
		d_country2.addNeighbor(d_country1);
		d_player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 10);
		d_player2 = new Player("Player2", new ArrayList<>(), new ArrayList<>(), 10);
	}

	/**
	 * The function tests the Blockade order by verifying that the country's army
	 * count is tripled, the country's owner is set to null, and the player no
	 * longer owns the country.
	 */
	@Test
	public void testReinforceOwnCountry() {
		this.setUpOwnership();
		d_order1 = new Blockade(d_player1, "1");

		d_order1.execute();
		assertEquals(d_country1ArmyCount * 3, d_country1.getArmyCount());
		assertNull(d_country1.getOwner());
		assertNull(d_player1.getCountriesOwned().stream()
				.filter(country -> country.getCountryId() == d_country1.getCountryId()).findFirst().orElse(null));
	}

	/**
	 * The function tests the Blockade order on a neutral country by verifying that
	 * it does not change the country's army count and ownership
	 *
	 */
	@Test
	public void testReinforceNeutralCountry() {
		d_order1 = new Blockade(d_player1, "1");

		d_order1.execute();
		assertEquals(d_country1ArmyCount, d_country1.getArmyCount());
		assertNull(d_country1.getOwner());
		assertNull(d_player1.getCountriesOwned().stream()
				.filter(country -> country.getCountryId() == d_country1.getCountryId()).findFirst().orElse(null));
	}

	/**
	 * The function tests the Blockade order on an enemy country by verifying that
	 * it does not change the country's army count and ownership
	 *
	 */
	@Test
	public void testReinforceEnemyCountry() {
		this.setUpOwnership();
		d_order1 = new Blockade(d_player1, "2");

		d_order1.execute();
		assertEquals(d_country2ArmyCount, d_country2.getArmyCount());
		assertNotNull(d_country2.getOwner());
		assertNotNull(d_player2.getCountriesOwned().stream()
				.filter(country -> country.getCountryId() == d_country2.getCountryId()).findFirst().orElse(null));
	}

	/**
	 * The function sets up ownership of countries by assigning players as owners
	 * and assigning the countries to the players' list of owned countries.
	 */
	private void setUpOwnership() {
		// Set up list of countries
		ArrayList<Country> l_countries1 = new ArrayList<>() {
			{
				add(d_country1);
			}
		};
		ArrayList<Country> l_countries2 = new ArrayList<>() {
			{
				add(d_country2);
			}
		};

		// Set up ownership
		d_country1.setOwner(d_player1);
		d_player1.setCountriesOwned(l_countries1);

		d_country2.setOwner(d_player2);
		d_player2.setCountriesOwned(l_countries2);
	}
}
