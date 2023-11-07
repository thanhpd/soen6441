package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a test class for bomb card
 *
 */
class BombTest {
	private Order d_order1;
	private Country d_country1;
	private Country d_country2;
	private Player d_player1;
	private Player d_player2;

	private final int d_country1ArmyCount = 13;
	private final int d_country2ArmyCount = 5;

	/**
	 * The setUp function initializes two countries and two players, and adds a
	 * neighbor relationship between the two countries.
	 */
	@BeforeEach
	void setUp() {
		d_country1 = new Country(1, "England", 1, d_country1ArmyCount);
		d_country2 = new Country(2, "France", 1, d_country2ArmyCount);
		d_country1.addNeighbor(d_country2);
		d_country2.addNeighbor(d_country1);
		d_player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 10);
		d_player2 = new Player("Player2", new ArrayList<>(), new ArrayList<>(), 10);
	}

	/**
	 * The testBombNeutralCountry function tests if a Bomb order reduces the army
	 * count of a neutral country to correct number.
	 */
	@Test
	void testBombNeutralCountry() {
		ArrayList<Country> l_countries = new ArrayList<>();
		l_countries.add(d_country1);

		d_country1.setOwner(d_player1);
		d_player1.setCountriesOwned(l_countries);

		d_order1 = new Bomb(d_player1, "2");

		d_order1.execute();
		assertEquals(d_country2ArmyCount / 2, d_country2.getArmyCount());
	}

	/**
	 * The testBombEnemyCountry function tests the execution of a Bomb order, which
	 * reduces the army count of a specific country owned by an enemy player.
	 */
	@Test
	void testBombEnemyCountry() {
		this.setUpOwnership();
		d_order1 = new Bomb(d_player2, "1");

		d_order1.execute();
		assertEquals(d_country1ArmyCount / 2, d_country1.getArmyCount());
	}

	/**
	 * The testCountryOwnership function tests the ownership of a country after a
	 * Bomb order is executed.
	 */
	@Test
	void testCountryOwnership() {
		this.setUpOwnership();
		d_country1.setArmyCount(1);
		d_order1 = new Bomb(d_player2, "1");

		d_order1.execute();
		assertEquals(0, d_country1.getArmyCount());
		assertEquals(d_player1.getName(), d_country1.getOwner().getName());
	}

	/**
	 * The function sets up the ownership of countries by assigning players as
	 * owners and updating the list of countries owned by each player.
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
