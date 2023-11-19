package com.w10.risk_game.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

/**
 * The AdvanceTest class is a JUnit test class that tests the functionality of
 * the Advance class, which represents an advance order in a game.
 *
 * @author Darlene-Naz
 */
class AdvanceTest {
	private Country d_country1;
	private Country d_country2;
	private Player d_player1;
	private Player d_player2;
	private Order d_order1;

	/**
	 * The setUp() function initializes objects for testing in a Java program.
	 */
	@BeforeEach
	public void setUp() {
		d_country1 = new Country(1, "England", 1, 10);
		d_country2 = new Country(2, "France", 1, 2);
		d_player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 3);
		d_player2 = new Player("Player2", new ArrayList<>(), new ArrayList<>(), 3);
		d_country1.setOwner(d_player1);
		d_country2.setOwner(d_player2);
		d_player1.addCountry(d_country1);
		d_player2.addCountry(d_country2);
		d_order1 = new Advance(d_country1, d_country2, 3);
	}

	/**
	 * The testSameOwnerTransfer function tests if the execute method of d_order1
	 * correctly transfers armies from d_country1 to d_country2 when they have the
	 * same owner.
	 */
	@Test
	void testSameOwnerTransfer() {
		d_country2.setOwner(d_player1);
		d_order1.execute();
		assertEquals(5, d_country2.getArmyCount());
		assertEquals(7, d_country1.getArmyCount());
	}

	/**
	 * The testBattleWon function tests various assertions after a battle is won.
	 */
	@Test
	void testBattleWon() {
		d_order1.execute();
		assertEquals(7, d_country1.getArmyCount());
		assertEquals(1, d_country2.getArmyCount());
		assertEquals("Player1", d_country2.getOwner().getName());
		assertEquals(0, d_player2.getCountriesOwned().size());
		assertEquals(2, d_player1.getCountriesOwned().size());
		assertTrue(d_player1.hasConqueredNewCountry());
	}

	/**
	 * The testBattleLost function tests if the attacking country loses the battle
	 * and the appropriate changes are made to the army counts.
	 */
	@Test
	void testBattleLost() {
		d_order1 = new Advance(d_country2, d_country1, 2);
		d_order1.execute();
		assertEquals(0, d_country2.getArmyCount());
		assertEquals(8, d_country1.getArmyCount());
		assertEquals("Player1", d_country1.getOwner().getName());
	}

	/**
	 * The testDeployAfterBattleWon function tests various assertions and deploy's
	 * armies to newly acquired country after a battle is won.
	 */
	@Test
	void testDeployAfterBattleWon() {
		d_order1.execute();
		assertEquals(7, d_country1.getArmyCount());
		assertEquals(1, d_country2.getArmyCount());
		assertEquals("Player1", d_country2.getOwner().getName());
		assertEquals(0, d_player2.getCountriesOwned().size());
		assertEquals(2, d_player1.getCountriesOwned().size());
		assertTrue(d_player1.hasConqueredNewCountry());

		Order deploy = new Deploy(d_player1, d_country2.getCountryId(), 3);
		deploy.execute();
		assertEquals(4, d_country2.getArmyCount());
	}
}
